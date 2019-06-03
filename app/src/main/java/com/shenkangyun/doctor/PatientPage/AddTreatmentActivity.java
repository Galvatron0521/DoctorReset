package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.AddTreatBean;
import com.shenkangyun.doctor.BeanFolder.ResultBean;
import com.shenkangyun.doctor.PatientPage.Adapter.ConstitutionAdapter;
import com.shenkangyun.doctor.PatientPage.Adapter.TreatmentAdapter;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTreatmentActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.questionRecycler)
    RecyclerView questionRecycler;

    private String moduleCode;
    private String moduleName;
    private String moduleUrl;
    private String patientID;

    private String id;
    private String json;
    private String displayName;

    private TreatmentAdapter treatmentAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Gson gson = new Gson();
    private List<HashMap<String, String>> filter = new ArrayList<>();
    private HashMap<String, List<HashMap<String, String>>> listHashMap = new HashMap<>();
    private List<AddTreatBean.DataBean.FieldListBean.ChildListBeanX> allChildList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treatment);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("中医治疗方式调查表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        moduleCode = intent.getStringExtra("ModuleCode");
        moduleName = intent.getStringExtra("ModuleName");
        moduleUrl = intent.getStringExtra("ModuleUrl");
        patientID = intent.getStringExtra("patientID");

        treatmentAdapter = new TreatmentAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        questionRecycler.setLayoutManager(linearLayoutManager);
        questionRecycler.setAdapter(treatmentAdapter);
        GetInfoFromNet();
    }

    private void GetInfoFromNet() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "saveFieldRecordNew")
                .addParams("data", new saveFieldRecordNew(moduleCode, moduleName, moduleUrl, patientID,
                        "2", "", "1", Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<AddTreatBean>() {
                    @Override
                    public void onSuccess(AddTreatBean response) throws JSONException {
                        //这是最外层的集合
                        for (int i = 0; i < response.getData().getFieldList().size(); i++) {
                            //这是第二层的ChildList
                            for (int j = 0; j < response.getData().getFieldList().get(i).getChildList().size(); j++) {
                                AddTreatBean.DataBean.FieldListBean.ChildListBeanX childListBeanX = new AddTreatBean.DataBean.FieldListBean.ChildListBeanX();
                                for (int k = 0; k < response.getData().getFieldList().get(i).getChildList().get(j).getChildList().size(); k++) {
                                    response.getData().getFieldList().get(i).getChildList().get(j).getChildList().get(k).setChecked(false);
                                }
                                childListBeanX.setDisplayName(response.getData().getFieldList().get(i).getChildList().get(j).getDisplayName());
                                childListBeanX.setChildList(response.getData().getFieldList().get(i).getChildList().get(j).getChildList());
                                allChildList.add(childListBeanX);
                            }
                        }
                        treatmentAdapter.setNewData(allChildList);
                        initClick();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void initClick() {
        treatmentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.option_A:
                        ((TreatmentAdapter) adapter).getItem(position).getChildList().get(0).setChecked(true);
                        ((TreatmentAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        break;
                    case R.id.option_B:
                        ((TreatmentAdapter) adapter).getItem(position).getChildList().get(1).setChecked(true);
                        ((TreatmentAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        break;
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        boolean Empty = false;
        for (int i = 0; i < questionRecycler.getLayoutManager().getItemCount(); i++) {
            displayName = "";
            HashMap filterObj = new HashMap();
            for (int j = 0; j < treatmentAdapter.getItem(i).getChildList().size(); j++) {
                if (treatmentAdapter.getItem(i).getChildList().get(j).isChecked()) {
                    id = treatmentAdapter.getItem(i).getChildList().get(j).getId();
                    displayName = treatmentAdapter.getItem(i).getChildList().get(j).getDisplayName();
                    break;
                }
            }
            filterObj.put("fieldID", id);
            if (TextUtils.isEmpty(displayName)) {
                Empty = true;
                break;
            } else {
                filterObj.put("contents", displayName);
            }
            filter.add(filterObj);
        }
        if (Empty) {
            filter.clear();
            Toast.makeText(this, "请将问题全部回答", Toast.LENGTH_SHORT).show();
        } else {
            listHashMap.put("filterObj", filter);
            json = gson.toJson(listHashMap);
            PutInfoToNet();
        }
    }

    private void PutInfoToNet() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "saveFieldRecordJson")
                .addParams("moduleCode", moduleCode)
                .addParams("moduleName", moduleName)
                .addParams("moduleUrl", moduleUrl)
                .addParams("patientID", patientID)
                .addParams("appType", "2")
                .addParams("scores", "")
                .addParams("diseasesid", "")
                .addParams("filter", json)
                .addParams("scoresStr", "")
                .addParams("data", new saveFieldRecordJson("1", Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<ResultBean>() {
                    @Override
                    public void onSuccess(ResultBean response) throws JSONException {
                        boolean message = response.isMessage();
                        if (message) {
                            Toast.makeText(AddTreatmentActivity.this, response.getData(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(1, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }


    static class saveFieldRecordNew {

        private String moduleCode;
        private String moduleName;
        private String moduleUrl;
        private String patientID;
        private String appType;
        private String diseasesid;
        private String mobileType;
        private String appKey;
        private String timeSpan;

        public saveFieldRecordNew(String moduleCode, String moduleName, String moduleUrl, String patientID,
                                  String appType, String diseasesid, String mobileType, String appKey, String timeSpan) {
            this.moduleCode = moduleCode;
            this.moduleName = moduleName;
            this.moduleUrl = moduleUrl;
            this.patientID = patientID;
            this.appType = appType;
            this.diseasesid = diseasesid;
            this.mobileType = mobileType;
            this.appKey = appKey;
            this.timeSpan = timeSpan;

        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }

    static class saveFieldRecordJson {
        private String mobileType;
        private String appKey;
        private String timeSpan;

        public saveFieldRecordJson(String mobileType, String appKey, String timeSpan) {
            this.mobileType = mobileType;
            this.appKey = appKey;
            this.timeSpan = timeSpan;

        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }

}
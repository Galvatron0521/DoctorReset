package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.shenkangyun.doctor.BeanFolder.AddConstitutionBean;
import com.shenkangyun.doctor.BeanFolder.ResultBean;
import com.shenkangyun.doctor.PatientPage.Adapter.ConstitutionAdapter;
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
import okhttp3.MediaType;

public class AddConstitutionActivity extends AppCompatActivity {

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
    private String replace1, replace2;
    private int scores;
    private int scoresSum;
    private String displayName;

    private ConstitutionAdapter constitutionAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Gson gson = new Gson();
    private List<HashMap<String, String>> filter = new ArrayList<>();
    private List<Integer> scoresSumList = new ArrayList<>();
    private HashMap<String, List<HashMap<String, String>>> listHashMap = new HashMap<>();
    private List<AddConstitutionBean.DataBean.FieldListBean.ChildListBeanX> allChildList = new ArrayList<>();

    private String[] fieldID;
    private String[] contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constitution);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("中医体质分类与判定自测表");
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

        fieldID = new String[]{"act", "patientID", "moduleCode", "moduleName", "appType", "scores", "data"};
        contents = new String[]{"saveFieldRecordJson2", patientID, "SP020133", "中医体质分类与判定自测表", "1", "0", "json"};

        constitutionAdapter = new ConstitutionAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        questionRecycler.setLayoutManager(linearLayoutManager);
        questionRecycler.setAdapter(constitutionAdapter);
        GetInfoFromNet();
    }

    private void GetInfoFromNet() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "saveFieldRecordNew")
                .addParams("data", new saveFieldRecordNew(moduleCode, moduleName, moduleUrl, patientID,
                        "1", "", "1", Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<AddConstitutionBean>() {
                    @Override
                    public void onSuccess(AddConstitutionBean response) throws JSONException {
                        //这是最外层的集合
                        for (int i = 0; i < response.getData().getFieldList().size(); i++) {
                            //这是第二层的ChildList
                            for (int j = 0; j < response.getData().getFieldList().get(i).getChildList().size(); j++) {
                                AddConstitutionBean.DataBean.FieldListBean.ChildListBeanX childListBeanX = new AddConstitutionBean.DataBean.FieldListBean.ChildListBeanX();
                                for (int k = 0; k < response.getData().getFieldList().get(i).getChildList().get(j).getChildList().size(); k++) {
                                    response.getData().getFieldList().get(i).getChildList().get(j).getChildList().get(k).setChecked(false);
                                }

                                if (j == response.getData().getFieldList().get(i).getChildList().size() - 1) {
                                    childListBeanX.setLastOne(true);
                                } else {
                                    childListBeanX.setLastOne(false);
                                }
                                childListBeanX.setFieldName(response.getData().getFieldList().get(i).getChildList().get(j).getFieldName());
                                childListBeanX.setChildList(response.getData().getFieldList().get(i).getChildList().get(j).getChildList());
                                allChildList.add(childListBeanX);
                            }
                        }
                        constitutionAdapter.setNewData(allChildList);
                        initClick();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void initClick() {
        constitutionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.option_A:
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(0).setChecked(true);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_B:
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(1).setChecked(true);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_C:
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(2).setChecked(true);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_D:
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(3).setChecked(true);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_E:
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(4).setChecked(true);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((ConstitutionAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        break;
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

    @OnClick(R.id.tv_save)
    public void onViewClicked() {
        scoresSum = 0;
        scoresSumList.clear();
        boolean Empty = false;
        for (int i = 0; i < fieldID.length; i++) {
            HashMap filterObj = new HashMap();
            filterObj.put("fieldID", fieldID[i]);
            filterObj.put("contents", contents[i]);
            filter.add(filterObj);
        }
        for (int i = 0; i < questionRecycler.getLayoutManager().getItemCount(); i++) {
            displayName = "";
            HashMap filterObj = new HashMap();
            for (int j = 0; j < constitutionAdapter.getItem(i).getChildList().size(); j++) {
                if (constitutionAdapter.getItem(i).getChildList().get(j).isChecked()) {
                    id = constitutionAdapter.getItem(i).getChildList().get(j).getId();
                    displayName = constitutionAdapter.getItem(i).getChildList().get(j).getDisplayName();
                    scores = Integer.valueOf(constitutionAdapter.getItem(i).getChildList().get(j).getScores());

                    if (constitutionAdapter.getItem(i).isLastOne()) {
                        scoresSumList.add(scoresSum);
                        scoresSum = 0;
                    } else {
                        scores = Integer.valueOf(constitutionAdapter.getItem(i).getChildList().get(j).getScores());
                        scoresSum = scoresSum + scores;
                    }
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
            scoresSumList.clear();
            Toast.makeText(this, "请将问题全部回答", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < scoresSumList.size(); i++) {
                HashMap filterObj = new HashMap();
                filterObj.put("fieldID", "sumScores" + i);
                filterObj.put("contents", String.valueOf(scoresSumList.get(i)));
                filter.add(filterObj);
            }
            listHashMap.put("filterObj", filter);
            json = gson.toJson(listHashMap);
            replace1 = json.replace("json", "%7B%20%22appKey%22%3A%22dc4d8d31d749ecb86157449d2fb804e0%22%2C%20%09%22timeSpan%22%3A%2220101020%22%20%7D");
            replace2 = replace1.replace("%", "%25");
            PutInfoToNet();
        }
    }

    private void PutInfoToNet() {
        OkHttpUtils
                .postString()
                .url(Base.saveFieldURL)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("\"" + replace2 + "\"")
                .build()
                .execute(new GsonCallBack<ResultBean>() {

                    @Override
                    public void onSuccess(ResultBean response) throws JSONException {
                        boolean message = response.isMessage();
                        if (message) {
                            Toast.makeText(AddConstitutionActivity.this, response.getData(), Toast.LENGTH_SHORT).show();
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
}
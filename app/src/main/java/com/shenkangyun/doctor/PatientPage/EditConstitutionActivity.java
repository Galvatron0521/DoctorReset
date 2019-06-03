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
import com.shenkangyun.doctor.BeanFolder.EditConBean;
import com.shenkangyun.doctor.BeanFolder.ResultBean;
import com.shenkangyun.doctor.PatientPage.Adapter.EditConAdapter;
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

public class EditConstitutionActivity extends AppCompatActivity {

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
    private String patientID;
    private String fieldRecordID;

    private String id;
    private String json;
    private String replace1, replace2;
    private int scores;
    private int scoresSum;
    private String displayName;

    private String[] fieldID;
    private String[] contents;
    private EditConAdapter editConAdapter;
    private LinearLayoutManager linearLayoutManager;

    private Gson gson = new Gson();
    private List<HashMap<String, String>> filter = new ArrayList<>();
    private List<Integer> scoresSumList = new ArrayList<>();
    private HashMap<String, List<HashMap<String, String>>> listHashMap = new HashMap<>();
    private List<EditConBean.DataBean.FieldListBean.ChildListBeanX> allChildList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_constitution);
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
        moduleCode = intent.getStringExtra("moduleCode");
        moduleName = intent.getStringExtra("moduleName");
        patientID = intent.getStringExtra("patientID");
        fieldRecordID = intent.getStringExtra("fieldRecordID");

        fieldID = new String[]{"act", "patientID", "moduleCode", "moduleName", "appType", "scores", "fieldRecordID", "data"};
        contents = new String[]{"editFieldRecordJson2", patientID, "SP020133", "中医体质分类与判定自测表", "1", "0", fieldRecordID, "json"};

        editConAdapter = new EditConAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        questionRecycler.setLayoutManager(linearLayoutManager);
        questionRecycler.setAdapter(editConAdapter);
        GetInfoFromNet();
    }

    private void GetInfoFromNet() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "editScaleNew")
                .addParams("moduleCode", moduleCode)
                .addParams("moduleName", moduleName)
                .addParams("patientID", patientID)
                .addParams("appType", "1")
                .addParams("rootUrl", "")
                .addParams("diseasesid", "")
                .addParams("fieldRecordID", fieldRecordID)
                .addParams("data", new editScaleNew("1", Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<EditConBean>() {
                    @Override
                    public void onSuccess(EditConBean response) throws JSONException {
                        //这是最外层的集合
                        for (int i = 0; i < response.getData().getFieldList().size(); i++) {
                            //这是第二层的ChildList
                            for (int j = 0; j < response.getData().getFieldList().get(i).getChildList().size(); j++) {
                                EditConBean.DataBean.FieldListBean.ChildListBeanX childListBeanX = new EditConBean.DataBean.FieldListBean.ChildListBeanX();
                                for (int k = 0; k < response.getData().getFieldList().get(i).getChildList().get(j).getChildList().size(); k++) {
                                    String contents = response.getData().getFieldList().get(i).getChildList().get(j).getChildList().get(k).getContents();
                                    if (TextUtils.isEmpty(contents)) {
                                        response.getData().getFieldList().get(i).getChildList().get(j).getChildList().get(k).setChecked(false);
                                    } else {
                                        response.getData().getFieldList().get(i).getChildList().get(j).getChildList().get(k).setChecked(true);
                                    }
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
                        editConAdapter.setNewData(allChildList);
                        initClick();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void initClick() {
        editConAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.option_A:
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(0).setChecked(true);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_B:
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(1).setChecked(true);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_C:
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(2).setChecked(true);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_D:
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(3).setChecked(true);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(4).setChecked(false);
                        break;
                    case R.id.option_E:
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(4).setChecked(true);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(0).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(1).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(2).setChecked(false);
                        ((EditConAdapter) adapter).getItem(position).getChildList().get(3).setChecked(false);
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
            for (int j = 0; j < editConAdapter.getItem(i).getChildList().size(); j++) {
                if (editConAdapter.getItem(i).getChildList().get(j).isChecked()) {
                    id = editConAdapter.getItem(i).getChildList().get(j).getId();
                    displayName = editConAdapter.getItem(i).getChildList().get(j).getDisplayName();
                    scores = Integer.valueOf(editConAdapter.getItem(i).getChildList().get(j).getScores());

                    if (editConAdapter.getItem(i).isLastOne()) {
                        scoresSumList.add(scoresSum);
                        scoresSum = 0;
                    } else {
                        scores = Integer.valueOf(editConAdapter.getItem(i).getChildList().get(j).getScores());
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
                            Toast.makeText(EditConstitutionActivity.this, response.getData(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(2, intent);
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


    static class editScaleNew {
        private String mobileType;
        private String appKey;
        private String timeSpan;

        public editScaleNew(String mobileType, String appKey, String timeSpan) {
            this.mobileType = mobileType;
            this.appKey = appKey;
            this.timeSpan = timeSpan;

        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ListBean;
import com.shenkangyun.doctor.BeanFolder.ShowTableEntity;
import com.shenkangyun.doctor.PatientPage.Adapter.TreatmentListAdapter;
import com.shenkangyun.doctor.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class TreatmentActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.expend_list)
    RecyclerView expendList;

    private String moduleCode;
    private String moduleName;
    private String moduleUrl;
    private String patientID;
    private String months;

    private List<ShowTableEntity> tableEntities = new ArrayList<>();
    private TreatmentListAdapter treatmentListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("住院期间中医治疗方式调查表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initNetRequest();
    }

    private void initView() {
        Intent intent = getIntent();
        moduleCode = intent.getStringExtra("ModuleCode");
        moduleName = intent.getStringExtra("ModuleName");
        moduleUrl = intent.getStringExtra("ModuleUrl");
        patientID = intent.getStringExtra("patientID");
    }

    private void initNetRequest() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "patientsFieldNew")
                .addParams("moduleCode", moduleCode)
                .addParams("moduleName", moduleName)
                .addParams("moduleUrl", moduleUrl)
                .addParams("patientID", patientID)
                .addParams("appType", "2")
                .addParams("diseasesid", "")
                .addParams("data", new patientsFieldNew("1", Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        jsonToJavaObjectByNative(response);
                    }
                });
    }

    private void jsonToJavaObjectByNative(String response) {
        Gson gson = new Gson();
        ListBean showTableBean = gson.fromJson(response, ListBean.class);
        List<ListBean.DataBean.MonthRecordMapBean.AllMonthBean> allMonths = showTableBean.getData().getMonthRecordMap().getAllMonth();
        if (allMonths.size() != 0) {
            for (int i = 0; i < allMonths.size(); i++) {
                months = allMonths.get(i).getMonths();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject monthRecordMap = data.getJSONObject("monthRecordMap");
                    JSONArray Month = monthRecordMap.getJSONArray(months);
                    String title = data.getString("title");
                    for (int j = 0; j < Month.length() + 1; j++) {
                        if (j == 0) {
                            ShowTableEntity tableEntity = new ShowTableEntity(1);
                            tableEntity.setTitle(months);
                            tableEntities.add(tableEntity);
                        }
                        ShowTableEntity tableEntity = new ShowTableEntity(2);
                        JSONObject info = Month.getJSONObject(j);
                        String results = info.getString("results");
                        String groupScore = info.getString("groupScore");
                        int fieldRecordID = info.getInt("id");
                        Long updateTime = info.getLong("updateTime");
                        tableEntity.setGroupScore(groupScore);
                        tableEntity.setResults(results);
                        tableEntity.setUpdateTime(updateTime);
                        tableEntity.setFieldRecordID(fieldRecordID);
                        tableEntity.setModelName(title);
                        tableEntities.add(tableEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            initAdapter();
        }
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddTreatmentActivity.class);
        intent.putExtra("ModuleCode", moduleCode);
        intent.putExtra("ModuleName", moduleName);
        intent.putExtra("ModuleUrl", moduleUrl);
        intent.putExtra("patientID", patientID);
        startActivityForResult(intent, 0);
    }

    private void initAdapter() {
        treatmentListAdapter = new TreatmentListAdapter();
        layoutManager = new LinearLayoutManager(this);
        expendList.setLayoutManager(layoutManager);
        expendList.setAdapter(treatmentListAdapter);
        treatmentListAdapter.setNewData(tableEntities);
        //设置分组的监听
        treatmentListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int fieldRecordID = ((TreatmentListAdapter) adapter).getItem(position).getFieldRecordID();
                Intent intent = new Intent(TreatmentActivity.this, EditTreatmentActivity.class);
                intent.putExtra("moduleCode", moduleCode);
                intent.putExtra("moduleName", moduleName);
                intent.putExtra("patientID", patientID);
                intent.putExtra("fieldRecordID", String.valueOf(fieldRecordID));
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            tableEntities.clear();
            initNetRequest();
        }
        if (requestCode == 1 && resultCode == 2) {
            tableEntities.clear();
            initNetRequest();
        }
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

    static class patientsFieldNew {
        private String appKey;
        private String mobileType;
        private String timeSpan;

        public patientsFieldNew(String mobileType, String appKey, String timeSpan) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}
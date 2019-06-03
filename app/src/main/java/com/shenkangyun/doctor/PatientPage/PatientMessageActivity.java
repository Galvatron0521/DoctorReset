package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ListBean;
import com.shenkangyun.doctor.BeanFolder.ShowTableEntity;
import com.shenkangyun.doctor.PatientPage.Adapter.QuestionListAdapter;
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
import okhttp3.Call;

public class PatientMessageActivity extends AppCompatActivity {


    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.timeLine)
    RecyclerView timeLine;

    private String months;
    private String patientID;
    private List<ShowTableEntity> tableEntities = new ArrayList<>();
    private QuestionListAdapter questionListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("问卷时间记录");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initData();
        initNetRequest();
    }

    private void initData() {
        Intent intent = getIntent();
        patientID = intent.getStringExtra("id");
    }

    private void initNetRequest() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "patientMessageNew")
                .addParams("data", new patientMessageNew("2", "1", patientID, Base.appKey, Base.timeSpan).toJson())
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
        ListBean listBean = gson.fromJson(response, ListBean.class);
        List<ListBean.DataBean.MonthRecordMapBean.AllMonthBean> allMonths = listBean.getData().getMonthRecordMap().getAllMonth();
        if (allMonths.size() != 0) {
            for (int i = 0; i < allMonths.size(); i++) {
                months = allMonths.get(i).getMonths();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject monthRecordMap = data.getJSONObject("monthRecordMap");
                    JSONArray Month = monthRecordMap.getJSONArray(months);
                    for (int j = 0; j < Month.length() + 1; j++) {
                        if (j == 0) {
                            ShowTableEntity tableEntity = new ShowTableEntity(1);
                            tableEntity.setTitle(months);
                            tableEntities.add(tableEntity);
                        }
                        ShowTableEntity tableEntity = new ShowTableEntity(2);
                        JSONObject info = Month.getJSONObject(j);
                        String results = info.getString("results");
                        String scores = info.getString("scores");
                        String groupScore = info.getString("groupScore");
                        String moduleName = info.getString("moduleName");
                        int fieldRecordID = info.getInt("id");
                        long updateTime = info.getLong("updateTime");
                        tableEntity.setScores(scores);
                        tableEntity.setGroupScore(groupScore);
                        tableEntity.setResults(results);
                        tableEntity.setUpdateTime(updateTime);
                        tableEntity.setFieldRecordID(fieldRecordID);
                        tableEntity.setModelName(moduleName);
                        tableEntities.add(tableEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            initAdapter();
        }
    }

    private void initAdapter() {
        questionListAdapter = new QuestionListAdapter();
        layoutManager = new LinearLayoutManager(this);
        timeLine.setLayoutManager(layoutManager);
        timeLine.setAdapter(questionListAdapter);
        questionListAdapter.setNewData(tableEntities);
        //设置分组的监听
        questionListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

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

    static class patientMessageNew {

        private String appType;
        private String mobileType;
        private String patientID;
        private String appKey;
        private String timeSpan;

        public patientMessageNew(String appType, String mobileType, String patientID, String appKey, String timeSpan) {
            this.appType = appType;
            this.mobileType = mobileType;
            this.patientID = patientID;
            this.appKey = appKey;
            this.timeSpan = timeSpan;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}
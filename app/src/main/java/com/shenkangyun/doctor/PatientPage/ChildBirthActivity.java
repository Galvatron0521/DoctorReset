package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.shenkangyun.doctor.BeanFolder.ChildBirthBean;
import com.shenkangyun.doctor.PatientPage.Adapter.ChildBirthAdapter;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.shenkangyun.doctor.UtilsFolder.RecyclerViewDivider;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ChildBirthActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.questionRecycler)
    RecyclerView questionRecycler;
    @BindView(R.id.easyLayout)
    SwipeRefreshLayout easyLayout;

    private List<ChildBirthBean.DataBean.ListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;
    private ChildBirthAdapter childBirthAdapter;

    private String id;
    private int size;
    private int pageNo = 0;
    private int pageCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_birth);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("分娩情况列表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initData();
        initRefresh();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        childBirthAdapter = new ChildBirthAdapter();
        manager = new LinearLayoutManager(this);
        questionRecycler.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL,
                2, getResources().getColor(R.color.back_ash)));
        questionRecycler.setLayoutManager(manager);
        questionRecycler.setAdapter(childBirthAdapter);
    }

    private void initData() {
        pageNo = 0;
        pageCount = 10;
        totalList.clear();
        final List<ChildBirthBean.DataBean.ListBean> childlist = new ArrayList<>();
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "selectChildbirthList")
                .addParams("data", new selectChildbirthList("2", "1", String.valueOf(pageNo), String.valueOf(pageCount),
                        id, Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<ChildBirthBean>() {
                    @Override
                    public void onSuccess(ChildBirthBean response) {
                        size = response.getData().getList().size();
                        for (int i = 0; i < response.getData().getList().size(); i++) {
                            ChildBirthBean.DataBean.ListBean listBean = new ChildBirthBean.DataBean.ListBean();
                            long createTime = response.getData().getList().get(i).getCreateTime();
                            long updateTime = response.getData().getList().get(i).getUpdateTime();
                            int tableID = response.getData().getList().get(i).getId();
                            int childWeeks = response.getData().getList().get(i).getChildWeeks();
                            int patientID = response.getData().getList().get(i).getPatientID();
                            int tire = response.getData().getList().get(i).getTire();
                            int chan = response.getData().getList().get(i).getChan();
                            String bearing = response.getData().getList().get(i).getBearing();
                            int fullterm = response.getData().getList().get(i).getFullterm();
                            int preterm = response.getData().getList().get(i).getPreterm();
                            int abortion = response.getData().getList().get(i).getAbortion();
                            int exist = response.getData().getList().get(i).getExist();
                            int birth = response.getData().getList().get(i).getBirth();
                            String complication = response.getData().getList().get(i).getComplication();
                            String height = response.getData().getList().get(i).getHeight();
                            String weight = response.getData().getList().get(i).getWeight();
                            int tfmilk = response.getData().getList().get(i).getTfmilk();
                            int femilk = response.getData().getList().get(i).getFemilk();
                            int firstmilk = response.getData().getList().get(i).getFirstmilk();
                            int mood = response.getData().getList().get(i).getMood();
                            String birthDate = response.getData().getList().get(i).getBirthDate();

                            listBean.setCreateTime(createTime);
                            listBean.setUpdateTime(updateTime);
                            listBean.setId(tableID);
                            listBean.setPatientID(patientID);
                            listBean.setTire(tire);
                            listBean.setChan(chan);
                            listBean.setChildWeeks(childWeeks);
                            listBean.setBearing(bearing);
                            listBean.setFullterm(fullterm);
                            listBean.setPreterm(preterm);
                            listBean.setAbortion(abortion);
                            listBean.setExist(exist);
                            listBean.setBirth(birth);
                            listBean.setComplication(complication);
                            listBean.setHeight(height);
                            listBean.setWeight(weight);
                            listBean.setTfmilk(tfmilk);
                            listBean.setFemilk(femilk);
                            listBean.setFirstmilk(firstmilk);
                            listBean.setMood(mood);
                            listBean.setBirthDate(birthDate);

                            childlist.add(listBean);
                            totalList.add(listBean);
                        }
                        childBirthAdapter.setNewData(childlist);
                        if (easyLayout.isRefreshing()) {
                            easyLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
        initLoadMore();
        initClick();
    }

    private void initLoadMore() {
        childBirthAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                questionRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<ChildBirthBean.DataBean.ListBean> childList = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils.post().url(Base.URL)
                                    .addParams("act", "selectChildbirthList")
                                    .addParams("data", new selectChildbirthList("2", "1", String.valueOf(pageNo), String.valueOf(pageCount),
                                            id, Base.appKey, Base.timeSpan).toJson())
                                    .build().execute(new GsonCallBack<ChildBirthBean>() {
                                @Override
                                public void onSuccess(final ChildBirthBean response) throws JSONException {
                                    size = response.getData().getList().size();
                                    for (int i = 0; i < response.getData().getList().size(); i++) {
                                        ChildBirthBean.DataBean.ListBean listBean = new ChildBirthBean.DataBean.ListBean();
                                        long createTime = response.getData().getList().get(i).getCreateTime();
                                        long updateTime = response.getData().getList().get(i).getUpdateTime();
                                        int tableID = response.getData().getList().get(i).getId();
                                        int childWeeks = response.getData().getList().get(i).getChildWeeks();
                                        int patientID = response.getData().getList().get(i).getPatientID();
                                        int tire = response.getData().getList().get(i).getTire();
                                        int chan = response.getData().getList().get(i).getChan();
                                        String bearing = response.getData().getList().get(i).getBearing();
                                        int fullterm = response.getData().getList().get(i).getFullterm();
                                        int preterm = response.getData().getList().get(i).getPreterm();
                                        int abortion = response.getData().getList().get(i).getAbortion();
                                        int exist = response.getData().getList().get(i).getExist();
                                        int birth = response.getData().getList().get(i).getBirth();
                                        String complication = response.getData().getList().get(i).getComplication();
                                        String height = response.getData().getList().get(i).getHeight();
                                        String weight = response.getData().getList().get(i).getWeight();
                                        int tfmilk = response.getData().getList().get(i).getTfmilk();
                                        int femilk = response.getData().getList().get(i).getFemilk();
                                        int firstmilk = response.getData().getList().get(i).getFirstmilk();
                                        int mood = response.getData().getList().get(i).getMood();
                                        String birthDate = response.getData().getList().get(i).getBirthDate();

                                        listBean.setCreateTime(createTime);
                                        listBean.setUpdateTime(updateTime);
                                        listBean.setId(tableID);
                                        listBean.setPatientID(patientID);
                                        listBean.setTire(tire);
                                        listBean.setChan(chan);
                                        listBean.setChildWeeks(childWeeks);
                                        listBean.setBearing(bearing);
                                        listBean.setFullterm(fullterm);
                                        listBean.setPreterm(preterm);
                                        listBean.setAbortion(abortion);
                                        listBean.setExist(exist);
                                        listBean.setBirth(birth);
                                        listBean.setComplication(complication);
                                        listBean.setHeight(height);
                                        listBean.setWeight(weight);
                                        listBean.setTfmilk(tfmilk);
                                        listBean.setFemilk(femilk);
                                        listBean.setFirstmilk(firstmilk);
                                        listBean.setMood(mood);
                                        listBean.setBirthDate(birthDate);

                                        childList.add(listBean);
                                        totalList.add(listBean);
                                    }
                                    childBirthAdapter.addData(childList);
                                    childBirthAdapter.loadMoreComplete();
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                        } else {
                            childBirthAdapter.loadMoreEnd();
                        }
                    }
                }, 2000);

            }
        }, questionRecycler);
    }

    private void initClick() {
        childBirthAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ChildBirthActivity.this, EditChildActivity.class);
                intent.putExtra("tableID", totalList.get(position).getId());
                intent.putExtra("createTime", totalList.get(position).getCreateTime());
                intent.putExtra("updateTime", totalList.get(position).getUpdateTime());
                intent.putExtra("chan", totalList.get(position).getChan());
                intent.putExtra("patientID", totalList.get(position).getPatientID());
                intent.putExtra("preterm", totalList.get(position).getPreterm());
                intent.putExtra("tire", totalList.get(position).getTire());
                intent.putExtra("bearing", totalList.get(position).getBearing());
                intent.putExtra("fullterm", totalList.get(position).getFullterm());
                intent.putExtra("abortion", totalList.get(position).getAbortion());
                intent.putExtra("exist", totalList.get(position).getExist());
                intent.putExtra("birth", totalList.get(position).getBirth());
                intent.putExtra("complication", totalList.get(position).getComplication());
                intent.putExtra("height", totalList.get(position).getHeight());
                intent.putExtra("weight", totalList.get(position).getWeight());
                intent.putExtra("Tfmilk", totalList.get(position).getTfmilk());
                intent.putExtra("femilk", totalList.get(position).getFemilk());
                intent.putExtra("firstMilk", totalList.get(position).getFirstmilk());
                intent.putExtra("mood", totalList.get(position).getMood());
                intent.putExtra("birthDate", totalList.get(position).getBirthDate());
                intent.putExtra("childWeeks", totalList.get(position).getChildWeeks());
                startActivityForResult(intent, 1);
            }
        });
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddChildActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }


    private void initRefresh() {
        easyLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 1) {
            initData();
        }
        if (requestCode == 1 && resultCode == 2) {
            initData();
        }
    }

    static class selectChildbirthList {

        private String appType;
        private String mobileType;
        private String pageNo;
        private String pageCount;
        private String patientID;
        private String appKey;
        private String timeSpan;

        public selectChildbirthList(String appType, String mobileType, String pageNo,
                                    String pageCount, String patientID, String appKey, String timeSpan) {
            this.appType = appType;
            this.mobileType = mobileType;
            this.pageNo = pageNo;
            this.pageCount = pageCount;
            this.patientID = patientID;
            this.appKey = appKey;
            this.timeSpan = timeSpan;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

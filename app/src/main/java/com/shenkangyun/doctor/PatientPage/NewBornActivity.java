package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.shenkangyun.doctor.BeanFolder.NewBornBean;
import com.shenkangyun.doctor.PatientPage.Adapter.ChildBirthAdapter;
import com.shenkangyun.doctor.PatientPage.Adapter.NewBornAdapter;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.shenkangyun.doctor.UtilsFolder.RecyclerViewDivider;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewBornActivity extends AppCompatActivity {

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

    private List<NewBornBean.DataBean.ListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;
    private NewBornAdapter newBornAdapter;

    private String id;
    private int size;
    private int pageNo = 0;
    private int pageCount = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_born);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("新生儿情况列表");
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
        newBornAdapter = new NewBornAdapter();
        manager = new LinearLayoutManager(this);
        questionRecycler.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL,
                2, getResources().getColor(R.color.back_ash)));
        questionRecycler.setLayoutManager(manager);
        questionRecycler.setAdapter(newBornAdapter);
    }

    private void initData() {
        pageNo = 0;
        pageCount = 10;
        totalList.clear();
        final List<NewBornBean.DataBean.ListBean> childlist = new ArrayList<>();
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "selectNewchildList")
                .addParams("data", new selectNewchildList("2", "1", String.valueOf(pageNo), String.valueOf(pageCount),
                        id, Base.appKey, Base.timeSpan).toJson())
                .build()
                .execute(new GsonCallBack<NewBornBean>() {
                    @Override
                    public void onSuccess(NewBornBean response) {
                        size = response.getData().getList().size();
                        for (int i = 0; i < response.getData().getList().size(); i++) {
                            NewBornBean.DataBean.ListBean listBean = new NewBornBean.DataBean.ListBean();
                            long createTime = response.getData().getList().get(i).getCreateTime();
                            long updateTime = response.getData().getList().get(i).getUpdateTime();
                            int tableID = response.getData().getList().get(i).getId();
                            int patientID = response.getData().getList().get(i).getPatientID();
                            String childsex = response.getData().getList().get(i).getChildsex();
                            String childheight = response.getData().getList().get(i).getChildheight();
                            String childweight = response.getData().getList().get(i).getChildweight();
                            String aPgarOne = response.getData().getList().get(i).getAPgarOne();
                            String aPgarFive = response.getData().getList().get(i).getAPgarFive();
                            String reason = response.getData().getList().get(i).getReason();

                            listBean.setCreateTime(createTime);
                            listBean.setUpdateTime(updateTime);
                            listBean.setId(tableID);
                            listBean.setPatientID(patientID);
                            listBean.setChildsex(childsex);
                            listBean.setChildheight(childheight);
                            listBean.setChildweight(childweight);
                            listBean.setAPgarOne(aPgarOne);
                            listBean.setAPgarFive(aPgarFive);
                            listBean.setReason(reason);

                            childlist.add(listBean);
                            totalList.add(listBean);
                        }
                        newBornAdapter.setNewData(childlist);
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
        newBornAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                questionRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<NewBornBean.DataBean.ListBean> childList = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils.post().url(Base.URL)
                                    .addParams("act", "selectNewchildList")
                                    .addParams("data", new selectNewchildList("2", "1", String.valueOf(pageNo), String.valueOf(pageCount),
                                            id, Base.appKey, Base.timeSpan).toJson())
                                    .build().execute(new GsonCallBack<NewBornBean>() {
                                @Override
                                public void onSuccess(final NewBornBean response) throws JSONException {
                                    size = response.getData().getList().size();
                                    for (int i = 0; i < response.getData().getList().size(); i++) {
                                        NewBornBean.DataBean.ListBean listBean = new NewBornBean.DataBean.ListBean();
                                        long createTime = response.getData().getList().get(i).getCreateTime();
                                        long updateTime = response.getData().getList().get(i).getUpdateTime();
                                        int tableID = response.getData().getList().get(i).getId();
                                        int patientID = response.getData().getList().get(i).getPatientID();
                                        String childsex = response.getData().getList().get(i).getChildsex();
                                        String childheight = response.getData().getList().get(i).getChildheight();
                                        String childweight = response.getData().getList().get(i).getChildweight();
                                        String aPgarOne = response.getData().getList().get(i).getAPgarOne();
                                        String aPgarFive = response.getData().getList().get(i).getAPgarFive();
                                        String reason = response.getData().getList().get(i).getReason();

                                        listBean.setCreateTime(createTime);
                                        listBean.setUpdateTime(updateTime);
                                        listBean.setId(tableID);
                                        listBean.setPatientID(patientID);
                                        listBean.setChildsex(childsex);
                                        listBean.setChildheight(childheight);
                                        listBean.setChildweight(childweight);
                                        listBean.setAPgarOne(aPgarOne);
                                        listBean.setAPgarFive(aPgarFive);
                                        listBean.setReason(reason);

                                        childList.add(listBean);
                                        totalList.add(listBean);
                                    }
                                    newBornAdapter.addData(childList);
                                    newBornAdapter.loadMoreComplete();
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                        } else {
                            newBornAdapter.loadMoreEnd();
                        }
                    }
                }, 2000);

            }
        }, questionRecycler);
    }

    private void initClick() {
        newBornAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewBornActivity.this, EditBornActivity.class);
                intent.putExtra("tableID", totalList.get(position).getId());
                intent.putExtra("createTime", totalList.get(position).getCreateTime());
                intent.putExtra("updateTime", totalList.get(position).getUpdateTime());
                intent.putExtra("patientID", totalList.get(position).getPatientID());
                intent.putExtra("childsex", totalList.get(position).getChildsex());
                intent.putExtra("childheight", totalList.get(position).getChildheight());
                intent.putExtra("childweight", totalList.get(position).getChildweight());
                intent.putExtra("APgarOne", totalList.get(position).getAPgarOne());
                intent.putExtra("APgarFive", totalList.get(position).getAPgarFive());
                intent.putExtra("Reason", totalList.get(position).getReason());
                startActivityForResult(intent, 1);
            }
        });
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddBornActivity.class);
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

    static class selectNewchildList {

        private String appType;
        private String mobileType;
        private String pageNo;
        private String pageCount;
        private String patientID;
        private String appKey;
        private String timeSpan;

        public selectNewchildList(String appType, String mobileType, String pageNo,
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

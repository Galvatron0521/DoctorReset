package com.shenkangyun.doctor.MainPage.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ScienceBean;
import com.shenkangyun.doctor.MainPage.ScienceActivity;
import com.shenkangyun.doctor.MainPage.ScienceDetailsActivity;
import com.shenkangyun.doctor.MainPage.adapter.ScienceListAdapter;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.shenkangyun.doctor.UtilsFolder.RecyclerViewDivider;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KePuFragment extends Fragment {

    @BindView(R.id.scienceRecycler)
    RecyclerView scienceRecycler;
    @BindView(R.id.easyLayout)
    SwipeRefreshLayout easyLayout;

    private List<ScienceBean.DataBean.ListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;
    private ScienceListAdapter scienceListAdapter;

    private int pageNo = 0;
    private int pageCount = 10;
    private int size;

    private String name;
    private int id;
    private String content;
    private long createTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kepu, container, false);
        ButterKnife.bind(this, view);
        initView();
        initNetRequest();
        initRefresh();
        return view;
    }

    private void initView() {
        scienceListAdapter = new ScienceListAdapter();
        manager = new LinearLayoutManager(getContext());
        scienceRecycler.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL,
                2, getResources().getColor(R.color.split_ash)));
        scienceRecycler.setLayoutManager(manager);
        scienceRecycler.setAdapter(scienceListAdapter);
    }

    private void initNetRequest() {
        pageNo = 0;
        pageCount = 10;
        totalList.clear();
        final List<ScienceBean.DataBean.ListBean> listBeans = new ArrayList<>();
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "scienceknowledgeList")
                .addParams("data", new scienceknowledgeList(Base.getMD5Str(), Base.getTimeSpan(), "1", "2",
                        String.valueOf(pageNo), String.valueOf(pageCount)).toJson())
                .build()
                .execute(new GsonCallBack<ScienceBean>() {
                    @Override
                    public void onSuccess(ScienceBean response) {
                        size = response.getData().getList().size();
                        for (int i = 0; i < response.getData().getList().size(); i++) {
                            ScienceBean.DataBean.ListBean listBean = new ScienceBean.DataBean.ListBean();
                            name = response.getData().getList().get(i).getArticleName();
                            id = response.getData().getList().get(i).getId();
                            content = response.getData().getList().get(i).getContent();
                            createTime = response.getData().getList().get(i).getCreateTime();
                            listBean.setArticleName(name);
                            listBean.setId(id);
                            listBean.setContent(content);
                            listBean.setCreateTime(createTime);

                            listBeans.add(listBean);
                            totalList.add(listBean);
                        }
                        scienceListAdapter.setNewData(listBeans);
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
        scienceListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                scienceRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<ScienceBean.DataBean.ListBean> listBeans = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils.post().url(Base.URL)
                                    .addParams("act", "scienceknowledgeList")
                                    .addParams("data", new scienceknowledgeList(Base.getMD5Str(), Base.getTimeSpan(), "1", "2",
                                            String.valueOf(pageNo), String.valueOf(pageCount)).toJson())
                                    .build().execute(new GsonCallBack<ScienceBean>() {
                                @Override
                                public void onSuccess(final ScienceBean response) throws JSONException {
                                    size = response.getData().getList().size();
                                    for (int i = 0; i < response.getData().getList().size(); i++) {
                                        ScienceBean.DataBean.ListBean listBean = new ScienceBean.DataBean.ListBean();
                                        name = response.getData().getList().get(i).getArticleName();
                                        id = response.getData().getList().get(i).getId();
                                        createTime = response.getData().getList().get(i).getCreateTime();
                                        content = response.getData().getList().get(i).getContent();

                                        listBean.setArticleName(name);
                                        listBean.setId(id);
                                        listBean.setContent(content);
                                        listBean.setCreateTime(createTime);

                                        listBeans.add(listBean);
                                        totalList.add(listBean);
                                    }
                                    scienceListAdapter.addData(listBeans);
                                    scienceListAdapter.loadMoreComplete();
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });
                        } else {
                            scienceListAdapter.loadMoreEnd();
                        }
                    }
                }, 2000);

            }
        }, scienceRecycler);
    }

    private void initRefresh() {
        easyLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initNetRequest();
            }
        });
    }

    private void initClick() {
        scienceListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.content:
                        Intent intent = new Intent(getContext(), ScienceDetailsActivity.class);
                        intent.putExtra("id", totalList.get(position).getId());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    static class scienceknowledgeList {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String pageNo;
        private String pageCount;

        public scienceknowledgeList(String appKey, String timeSpan, String mobileType,
                                    String appType, String pageNo, String pageCount) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.pageNo = pageNo;
            this.pageCount = pageCount;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}
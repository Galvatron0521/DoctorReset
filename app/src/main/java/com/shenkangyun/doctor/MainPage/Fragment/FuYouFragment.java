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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.DeleteBean;
import com.shenkangyun.doctor.BeanFolder.PatientBean;
import com.shenkangyun.doctor.MainPage.SearchActivity;
import com.shenkangyun.doctor.MainPage.adapter.PatientListAdapter;
import com.shenkangyun.doctor.PatientPage.PatientInfoActivity;
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


public class FuYouFragment extends Fragment {

    @BindView(R.id.patientRecycler)
    RecyclerView patientRecycler;
    @BindView(R.id.easyLayout)
    SwipeRefreshLayout easyLayout;

    private List<PatientBean.DataBean.ListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;
    private PatientListAdapter patientListAdapter;

    private int pageNo = 0;
    private int pageCount = 10;

    private int size;
    private String idCard;
    private String name;
    private int age;
    private String birthday;
    private String sex;
    private String mobile;
    private String num;
    private int id;
    private String loginName;
    private long createTime;
    private long updateTime;
    private String provinceID;
    private String cityID;
    private String regionID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fuyou, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        initRefresh();
        return view;
    }

    private void initView() {
        patientListAdapter = new PatientListAdapter();
        manager = new LinearLayoutManager(getContext());
        patientRecycler.addItemDecoration(new RecyclerViewDivider(getContext(), LinearLayoutManager.VERTICAL,
                2, getResources().getColor(R.color.back_ash)));
        patientRecycler.setLayoutManager(manager);
        patientRecycler.setAdapter(patientListAdapter);
    }

    private void initData() {
        pageNo = 0;
        pageCount = 10;
        totalList.clear();
        final List<PatientBean.DataBean.ListBean> PatientList = new ArrayList<>();
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "patientslist")
                .addParams("data", new patientslist(Base.appKey, Base.timeSpan, "1", "2", String.valueOf(pageNo), String.valueOf(pageCount),
                        "", "").toJson())
                .build()
                .execute(new GsonCallBack<PatientBean>() {
                    @Override
                    public void onSuccess(PatientBean response) {
                        size = response.getData().getList().size();
                        for (int i = 0; i < response.getData().getList().size(); i++) {
                            PatientBean.DataBean.ListBean listBean = new PatientBean.DataBean.ListBean();
                            age = response.getData().getList().get(i).getAge();
                            birthday = response.getData().getList().get(i).getBrithday();
                            id = response.getData().getList().get(i).getId();
                            idCard = response.getData().getList().get(i).getIdCard();
                            mobile = response.getData().getList().get(i).getMobile();
                            name = response.getData().getList().get(i).getName();
                            num = response.getData().getList().get(i).getNum();
                            sex = response.getData().getList().get(i).getSex();
                            cityID = response.getData().getList().get(i).getCityID();
                            regionID = response.getData().getList().get(i).getRegionID();
                            loginName = response.getData().getList().get(i).getLoginName();
                            provinceID = response.getData().getList().get(i).getProvinceID();
                            createTime = response.getData().getList().get(i).getCreateTime();
                            updateTime = response.getData().getList().get(i).getUpdateTime();
                            listBean.setAge(age);
                            listBean.setBrithday(birthday);
                            listBean.setId(id);
                            listBean.setIdCard(idCard);
                            listBean.setMobile(mobile);
                            listBean.setName(name);
                            listBean.setNum(num);
                            listBean.setSex(sex);
                            listBean.setLoginName(loginName);
                            listBean.setCreateTime(createTime);
                            listBean.setUpdateTime(updateTime);
                            listBean.setProvinceID(provinceID);
                            listBean.setCityID(cityID);
                            listBean.setRegionID(regionID);

                            PatientList.add(listBean);
                            totalList.add(listBean);
                        }
                        patientListAdapter.setNewData(PatientList);
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
        patientListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                patientRecycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<PatientBean.DataBean.ListBean> PatientList = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils
                                    .post()
                                    .url(Base.URL)
                                    .addParams("act", "patientslist")
                                    .addParams("data", new patientslist(Base.getMD5Str(), Base.getTimeSpan(), "1", "2", String.valueOf(pageNo), String.valueOf(pageCount)
                                            , "", "").toJson())
                                    .build()
                                    .execute(new GsonCallBack<PatientBean>() {
                                        @Override
                                        public void onSuccess(final PatientBean response) throws JSONException {
                                            size = response.getData().getList().size();
                                            for (int i = 0; i < response.getData().getList().size(); i++) {
                                                PatientBean.DataBean.ListBean listBean = new PatientBean.DataBean.ListBean();
                                                age = response.getData().getList().get(i).getAge();
                                                birthday = response.getData().getList().get(i).getBrithday();
                                                id = response.getData().getList().get(i).getId();
                                                idCard = response.getData().getList().get(i).getIdCard();
                                                mobile = response.getData().getList().get(i).getMobile();
                                                name = response.getData().getList().get(i).getName();
                                                num = response.getData().getList().get(i).getNum();
                                                sex = response.getData().getList().get(i).getSex();
                                                cityID = response.getData().getList().get(i).getCityID();
                                                regionID = response.getData().getList().get(i).getRegionID();
                                                loginName = response.getData().getList().get(i).getLoginName();
                                                provinceID = response.getData().getList().get(i).getProvinceID();
                                                createTime = response.getData().getList().get(i).getCreateTime();
                                                updateTime = response.getData().getList().get(i).getUpdateTime();
                                                listBean.setAge(age);
                                                listBean.setBrithday(birthday);
                                                listBean.setId(id);
                                                listBean.setIdCard(idCard);
                                                listBean.setMobile(mobile);
                                                listBean.setName(name);
                                                listBean.setNum(num);
                                                listBean.setSex(sex);
                                                listBean.setLoginName(loginName);
                                                listBean.setCreateTime(createTime);
                                                listBean.setUpdateTime(updateTime);
                                                listBean.setProvinceID(provinceID);
                                                listBean.setCityID(cityID);
                                                listBean.setRegionID(regionID);

                                                PatientList.add(listBean);
                                                totalList.add(listBean);
                                            }
                                            patientListAdapter.addData(PatientList);
                                            patientListAdapter.loadMoreComplete();
                                        }

                                        @Override
                                        public void onError(Exception e) {

                                        }
                                    });
                        } else {
                            patientListAdapter.loadMoreEnd();
                        }
                    }
                }, 2000);

            }
        }, patientRecycler);
    }

    private void initRefresh() {
        easyLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initClick() {
        patientListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.content:
                        Intent intent = new Intent(getContext(), PatientInfoActivity.class);
                        intent.putExtra("id", totalList.get(position).getId());
                        intent.putExtra("idCard", totalList.get(position).getIdCard());
                        intent.putExtra("name", totalList.get(position).getName());
                        intent.putExtra("age", totalList.get(position).getAge());
                        intent.putExtra("sex", totalList.get(position).getSex());
                        intent.putExtra("birthday", totalList.get(position).getBrithday());
                        intent.putExtra("mobile", totalList.get(position).getMobile());
                        intent.putExtra("loginName", totalList.get(position).getLoginName());
                        startActivity(intent);
                        break;
                    case R.id.right:
                        initDelete(position);
                        patientListAdapter.remove(position);
                        patientListAdapter.notifyAll();
                        break;
                }
            }
        });
    }

    private void initDelete(int position) {
        int idDel = totalList.get(position).getId();
        OkHttpUtils.post().url(Base.URL)
                .addParams("act", "DeletePatient")
                .addParams("data", new DeletePatient(Base.getMD5Str(), Base.getTimeSpan(), "1", "2", String.valueOf(idDel)).toJson())
                .build().execute(new GsonCallBack<DeleteBean>() {
            @Override
            public void onSuccess(DeleteBean response) throws JSONException {
                String status = response.getStatus();
                if ("0".equals(status)) {
                    Toast.makeText(getContext(), response.getData().getData(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), response.getData().getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @OnClick(R.id.layout_search)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SearchActivity.class);
        startActivity(intent);
    }

    static class patientslist {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String pageNo;
        private String pageCount;
        private String name;
        private String diseasesID;

        public patientslist(String appKey, String timeSpan, String mobileType, String appType, String pageNo,
                            String pageCount, String name, String diseasesID) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.pageNo = pageNo;
            this.pageCount = pageCount;
            this.name = name;
            this.diseasesID = diseasesID;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }

    static class DeletePatient {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String id;

        public DeletePatient(String appKey, String timeSpan, String mobileType, String appType, String id) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.id = id;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

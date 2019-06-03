package com.shenkangyun.doctor.MainPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.DeleteBean;
import com.shenkangyun.doctor.BeanFolder.PatientBean;
import com.shenkangyun.doctor.MainPage.adapter.PatientListAdapter;
import com.shenkangyun.doctor.PatientPage.PatientInfoActivity;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.search_ed)
    EditText searchEd;
    @BindView(R.id.RecyclerView)
    RecyclerView RecyclerView;

    private List<PatientBean.DataBean.ListBean> totalList = new ArrayList<>();
    private LinearLayoutManager manager;
    private PatientListAdapter patientListAdapter;
    private int pageNo = 0;
    private int pageCount = 8;
    private int size;
    private String patientName;

    private String idCard;
    private String name;
    private int age;
    private String birthday;
    private String sex;
    private String mobile;
    private String num;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("患者搜索");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        searchEd.addTextChangedListener(this);
        patientListAdapter = new PatientListAdapter();
        manager = new LinearLayoutManager(SearchActivity.this);
        RecyclerView.setLayoutManager(manager);
        RecyclerView.setAdapter(patientListAdapter);
    }

    @OnClick(R.id.Search)
    public void onViewClicked() {
        patientName = searchEd.getText().toString();
        if (!TextUtils.isEmpty(patientName.trim())) {
            final List<PatientBean.DataBean.ListBean> PatientList = new ArrayList<>();
            OkHttpUtils.post().url(Base.URL)
                    .addParams("act", "patientslist")
                    .addParams("data", new patientslist(Base.getMD5Str(), Base.getTimeSpan(), "1", "2",
                            String.valueOf(pageNo), String.valueOf(pageCount), patientName, "").toJson())
                    .build().execute(new GsonCallBack<PatientBean>() {
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
                        listBean.setAge(age);
                        listBean.setBrithday(birthday);
                        listBean.setId(id);
                        listBean.setIdCard(idCard);
                        listBean.setMobile(mobile);
                        listBean.setName(name);
                        listBean.setNum(num);
                        listBean.setSex(sex);

                        PatientList.add(listBean);
                        totalList.add(listBean);
                    }
                    patientListAdapter.setNewData(PatientList);
                }

                @Override
                public void onError(Exception e) {

                }
            });
            initLoadMore();
            initClick();
        }
    }

    private void initLoadMore() {
        patientListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                RecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final List<PatientBean.DataBean.ListBean> PatientList = new ArrayList<>();
                        if (!(size < pageCount)) {
                            pageNo = pageNo + size;
                            OkHttpUtils.post().url(Base.URL)
                                    .addParams("act", "patientslist")
                                    .addParams("data", new patientslist(Base.getMD5Str(), Base.getTimeSpan(), "1", "2",
                                            String.valueOf(pageNo), String.valueOf(pageCount), patientName, "").toJson())
                                    .build().execute(new GsonCallBack<PatientBean>() {
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
                                        listBean.setAge(age);
                                        listBean.setBrithday(birthday);
                                        listBean.setId(id);
                                        listBean.setIdCard(idCard);
                                        listBean.setMobile(mobile);
                                        listBean.setName(name);
                                        listBean.setNum(num);
                                        listBean.setSex(sex);

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
        }, RecyclerView);
    }

    private void initClick() {
        patientListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.content:
                        Intent intent = new Intent(SearchActivity.this, PatientInfoActivity.class);
                        intent.putExtra("id", totalList.get(position).getId());
                        intent.putExtra("idCard", totalList.get(position).getIdCard());
                        intent.putExtra("name", totalList.get(position).getName());
                        intent.putExtra("age", totalList.get(position).getAge());
                        intent.putExtra("birthday", totalList.get(position).getBrithday());
                        intent.putExtra("sex", totalList.get(position).getSex());
                        intent.putExtra("mobile", totalList.get(position).getMobile());
                        startActivity(intent);
                        break;
                    case R.id.right:
                        initDelete(position);
                        patientListAdapter.remove(position);
                        patientListAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    private void initDelete(int pos) {
        int idDel = totalList.get(pos).getId();
        OkHttpUtils.post().url(Base.URL).addParams("act", "DeletePatient")
                .addParams("data", new DeletePatient(Base.getMD5Str(), Base.getTimeSpan(), "1", "2", String.valueOf(idDel)).toJson())
                .build().execute(new GsonCallBack<DeleteBean>() {
            @Override
            public void onSuccess(DeleteBean response) throws JSONException {
                String status = response.getStatus();
                if ("0".equals(status)){
                    String data = response.getData().getData();
                    Toast.makeText(SearchActivity.this, data, Toast.LENGTH_SHORT).show();
                }else {
                    String data = response.getData().getData();
                    Toast.makeText(SearchActivity.this, data, Toast.LENGTH_SHORT).show();
                }

                pageNo = 0;
                pageCount = 8;
                totalList.clear();
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        pageNo = 0;
        pageCount = 8;
        totalList.clear();
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

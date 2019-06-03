package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ResponseBean;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddBornActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.ed_weight)
    EditText edWeight;
    @BindView(R.id.ed_height)
    EditText edHeight;
    @BindView(R.id.ed_APgarOne)
    EditText edAPgarOne;
    @BindView(R.id.ed_APgarFive)
    EditText edAPgarFive;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.ed_reason)
    EditText edReason;
    @BindView(R.id.ReasonLayout)
    LinearLayout ReasonLayout;

    private ListPopupWindow mListSexs;
    private ListPopupWindow mListReasons;

    private List<String> reasons = new ArrayList<>();
    private List<String> Sexs = new ArrayList<>();

    private String id;
    private String sexNum;
    private String sex;
    private String reasonNum;
    private String height;
    private String weight;
    private String apgarOne;
    private String apgarFive;
    private String reason;
    private String reasonTx;
    private String reasonShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_born);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("新生儿情况");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Sexs.add("男");
        Sexs.add("女");

        reasons.add("早产");
        reasons.add("母亲妊娠合并症");
        reasons.add("新生儿感染");
        reasons.add("新生儿窒息");
        reasons.add("高危儿");
        reasons.add("其他");
        reasons.add("无");
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

    @OnClick({R.id.tv_sex, R.id.tv_reason, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sex:
                mListSexs = new ListPopupWindow(this);
                mListSexs.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, Sexs));
                mListSexs.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                mListSexs.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                mListSexs.setAnchorView(tvSex);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
                mListSexs.setModal(true);//设置是否是模式
                mListSexs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        tvSex.setText(Sexs.get(position));
                        sexNum = Sexs.get(position);
                        mListSexs.dismiss();
                    }
                });
                mListSexs.show();
                break;
            case R.id.tv_reason:
                mListReasons = new ListPopupWindow(this);
                mListReasons.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, reasons));
                mListReasons.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                mListReasons.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                mListReasons.setAnchorView(tvReason);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
                mListReasons.setModal(true);//设置是否是模式
                mListReasons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        tvReason.setText(reasons.get(position));
                        reasonShow = reasons.get(position);
                        if ("其他".equals(reasonShow)) {
                            ReasonLayout.setVisibility(View.VISIBLE);
                        } else {
                            ReasonLayout.setVisibility(View.GONE);
                        }
                        mListReasons.dismiss();
                    }
                });
                mListReasons.show();
                break;
            case R.id.tv_save:
                initData();
                break;
        }
    }

    private void initData() {
        height = edHeight.getText().toString();
        weight = edWeight.getText().toString();
        apgarOne = edAPgarOne.getText().toString();
        apgarFive = edAPgarFive.getText().toString();
        sex = tvSex.getText().toString();
        reasonTx = tvReason.getText().toString();
        reasonNum = edReason.getText().toString();
        if (TextUtils.isEmpty(sex)) {
            Toast.makeText(this, "婴儿性别不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(height)) {
            Toast.makeText(this, "婴儿身长不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "婴儿体重不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apgarOne)) {
            Toast.makeText(this, "新生儿APgar评分不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(apgarFive)) {
            Toast.makeText(this, "新生儿APgar评分不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(sexNum))) {
            Toast.makeText(this, "婴儿性别不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(reasonTx)) {
            Toast.makeText(this, "请选择婴儿住院原因！", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(reasonNum) && (ReasonLayout.getVisibility() != View.GONE)) {
            Toast.makeText(this, "婴儿住院原因不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        initNetRequest();
    }

    private void initNetRequest() {
        if (reasonTx.equals("其他") && !TextUtils.isEmpty(reasonNum)) {
            reason = reasonNum;
        } else {
            reason = reasonTx;
        }
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "insertNewchild")
                .addParams("data", new insertNewchild(Base.appKey, Base.timeSpan, "1", "2",
                        id, sex, apgarOne, apgarFive, weight, height, reason).toJson())
                .build()
                .execute(new GsonCallBack<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean response) {
                        String status = response.getStatus();
                        if ("0".equals(status)) {
                            Toast.makeText(AddBornActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(1,intent);
                            finish();
                        } else {
                            Toast.makeText(AddBornActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    static class insertNewchild {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String patientID;
        private String childsex;
        private String aPgarOne;
        private String aPgarFive;
        private String childweight;
        private String childheight;
        private String reason;

        public insertNewchild(String appKey, String timeSpan, String mobileType, String appType, String patientID,
                              String childsex, String aPgarOne, String aPgarFive, String childweight, String childheight, String reason) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.patientID = patientID;
            this.childsex = childsex;
            this.aPgarOne = aPgarOne;
            this.aPgarFive = aPgarFive;
            this.childheight = childheight;
            this.childweight = childweight;
            this.reason = reason;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

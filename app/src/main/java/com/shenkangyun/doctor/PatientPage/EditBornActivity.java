package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditBornActivity extends AppCompatActivity {

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

    private int tableID;
    private long createTime;
    private long updateTime;
    private int patientID;
    private String childsex;
    private String childheight;
    private String childweight;
    private String aPgarOneInt;
    private String aPgarFiveInt;
    private String reasonInt;
    private String reasonTv;

    private boolean isNumeric = false;
    private boolean isWord = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_born);
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
        setIntentData();
    }

    private void initView() {
        Intent intent = getIntent();
        tableID = intent.getIntExtra("tableID", 0);
        createTime = intent.getLongExtra("createTime", 0);
        updateTime = intent.getLongExtra("updateTime", 0);
        patientID = intent.getIntExtra("patientID", 0);
        childsex = intent.getStringExtra("childsex");
        childheight = intent.getStringExtra("childheight");
        childweight = intent.getStringExtra("childweight");
        aPgarOneInt = intent.getStringExtra("APgarOne");
        aPgarFiveInt = intent.getStringExtra("APgarFive");
        reasonInt = intent.getStringExtra("Reason");

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


    private void setIntentData() {
        sexNum = childsex;
        tvSex.setText(childsex);
        height = childheight;
        edHeight.setText(childheight);
        weight = childheight;
        edWeight.setText(childweight);
        apgarOne = aPgarOneInt;
        if (!TextUtils.isEmpty(aPgarOneInt)) {
            edAPgarOne.setText(aPgarOneInt);
        }
        apgarFive = aPgarOneInt;
        if (!TextUtils.isEmpty(aPgarFiveInt)) {
            edAPgarFive.setText(aPgarFiveInt);
        }

        reason = reasonInt;
        if (!TextUtils.isEmpty(reasonInt)) {
            isNumeric = reasonInt.matches("[0-9]");
            String regex = "[\\u4e00-\\u9fa5]";
            isWord = reasonInt.matches(regex);
            if (isNumeric == true && isWord == false) {
                for (int i = 0; i < reasons.size(); i++) {
                    if (Integer.valueOf(reasonInt) == i) {
                        tvReason.setText(reasons.get(i).toString());
                        break;
                    } else {
                        tvReason.setText(reasonInt);
                    }
                }
            }
            if ((isNumeric == false && isWord == true) || (isNumeric == false && isWord == false)) {
                tvReason.setText(reasonInt);
            }
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
                        reasonNum = reasons.get(position);
                        if ("其他".equals(reasonNum)) {
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
        reasonTv = tvReason.getText().toString();
        if (ReasonLayout.getVisibility() == View.VISIBLE) {
            reason = edReason.getText().toString();
        } else {
            reason = reasonTv;
        }

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
        if ("请选择".equals(sex)||TextUtils.isEmpty(String.valueOf(sexNum))) {
            Toast.makeText(this, "婴儿性别不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("请选择".equals(reasonTv)||TextUtils.isEmpty(reason) && ReasonLayout.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "婴儿住院原因不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        initNetRequest();
    }

    private void initNetRequest() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "updateNewchild")
                .addParams("data", new updateNewchild(Base.appKey, Base.timeSpan, "1", "2",
                        String.valueOf(patientID), String.valueOf(tableID), String.valueOf(sexNum), apgarOne, apgarFive, weight, height, reason).toJson())
                .build().execute(new GsonCallBack<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean response) throws JSONException {
                String status = response.getStatus();
                if ("0".equals(status)) {
                    Toast.makeText(EditBornActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                } else {
                    Toast.makeText(EditBornActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    static class updateNewchild {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String patientID;
        private String newchildID;
        private String childsex;
        private String aPgarOne;
        private String aPgarFive;
        private String childweight;
        private String childheight;
        private String reason;

        public updateNewchild(String appKey, String timeSpan, String mobileType, String appType, String patientID, String newchildID,
                              String childsex, String aPgarOne, String aPgarFive, String childweight, String childheight, String reason) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.patientID = patientID;
            this.newchildID = newchildID;
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

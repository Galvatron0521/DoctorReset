package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.PushBean;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostActivity extends AppCompatActivity {
    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.ed_postContent)
    EditText edPostContent;

    private String id;
    private String postContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("推送发送");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @OnClick(R.id.tv_post)
    public void onViewClicked() {
        postContent = edPostContent.getText().toString();
        if (TextUtils.isEmpty(postContent) || "请填写内容".equals(postContent)) {
            Toast.makeText(this, "推送内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            PutPostToPatient();
        }
    }

    private void PutPostToPatient() {
        OkHttpUtils.post().url(Base.URL)
                .addParams("act", "sendPushToPatient")
                .addParams("patientID", id)
                .addParams("data", new sendPushToPatient("1", "2", Base.appKey, Base.timeSpan, postContent).toJson())
                .build()
                .execute(new GsonCallBack<PushBean>() {
                    @Override
                    public void onSuccess(PushBean response) throws JSONException {
                        int status = response.getData();
                        if (0 == status) {
                            Toast.makeText(PostActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PostActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
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

    static class sendPushToPatient {
        private String appType;
        private String mobileType;
        private String appKey;
        private String timeSpan;
        private String content;

        public sendPushToPatient(String appType, String mobileType, String appKey, String timeSpan, String content) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.content = content;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

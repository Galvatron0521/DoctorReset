package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.Constants;
import com.shenkangyun.doctor.UtilsFolder.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostIntentActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    private String id;
    private String URL;
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_intent);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("推送分享");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        StringBuilder builder = new StringBuilder();
        builder.append(Base.WeChat_URL).append("?act=").append("selectQuestion").append("&data={\"appKey\":\"")
                .append("dc4d8d31d749ecb86157449d2fb804e0").append("\",").append("\"timeSpan\":\"").append("20101020").append("\",")
                .append("\"appType\":\"").append("1").append("\",")
                .append("\"mobileType\":\"").append("1").append("\",")
                .append("\"patientID\":\"").append(String.valueOf(id)).append("\"").append("}")
                .append("?&from=singlemessage&isappinstalled=0");
        URL = builder.toString();
    }

    @OnClick({R.id.layout_share, R.id.layout_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_share:
                isWeiXinAppInstall();
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = URL;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "问卷调查";
                msg.description = "针对产妇身心健康进行的问卷型测试";
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_wenjuan);
                msg.thumbData = Util.bmpToByteArray(thumb, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "webpage";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                iwxapi.sendReq(req);
                break;
            case R.id.layout_post:
                Intent intentPost = new Intent(this, PostActivity.class);
                intentPost.putExtra("id", String.valueOf(id));
                startActivity(intentPost);
                break;
        }
    }

    public boolean isWeiXinAppInstall() {
        if (iwxapi == null)
            iwxapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        if (iwxapi.isWXAppInstalled()) {
            return true;
        } else {
            Toast.makeText(this, "您没有安装微信", Toast.LENGTH_SHORT).show();
            return false;
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
}

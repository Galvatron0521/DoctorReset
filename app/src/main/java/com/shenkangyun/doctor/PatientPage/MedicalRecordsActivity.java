package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicalRecordsActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.mWebView)
    WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("就诊记录");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initWebView();
    }

    private void initWebView() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        StringBuilder builder = new StringBuilder();
        builder.append(Base.URL).append("?act=").append("patientsField3").append("&data={\"appKey\":\"")
                .append("dc4d8d31d749ecb86157449d2fb804e0").append("\",").append("\"timeSpan\":\"").append("20101020").append("\",")
                .append("\"appType\":\"").append("2").append("\",")
                .append("\"mobileType\":\"").append("1").append("\",")
                .append("\"patientID\":\"").append(id).append("\"").append("}");
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(builder.toString());
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

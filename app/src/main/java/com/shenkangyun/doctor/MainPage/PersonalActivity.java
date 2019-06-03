package com.shenkangyun.doctor.MainPage;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.DBFolder.User;
import com.shenkangyun.doctor.R;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.doc_name)
    TextView docName;
    @BindView(R.id.doc_age)
    TextView docAge;
    @BindView(R.id.doc_idCard)
    TextView docIdCard;
    @BindView(R.id.doc_birthday)
    TextView docBirthday;
    @BindView(R.id.doc_mobile)
    TextView docMobile;

    private String name;
    private String birthday;
    private String mobile;
    private String idCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("个人中心");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            name = all.get(i).getName();
            birthday = all.get(i).getBirthday();
            mobile = all.get(i).getMobile();
            idCard = all.get(i).getIDCard();
        }
        if (!TextUtils.isEmpty(name)) {
            String firstWord = name.substring(0, 1);
            docName.setText(name);
            tvWord.setText(firstWord);
        }
        if (!TextUtils.isEmpty(mobile)) {
            docMobile.setText(mobile);

        } else {
            docMobile.setText("数据未填写");
        }

        if (!TextUtils.isEmpty(birthday)) {
            docBirthday.setText(birthday);
        } else {
            docBirthday.setText("数据未填写");
        }

        if (!TextUtils.isEmpty(idCard)) {
            docIdCard.setText(idCard);
        } else {
            docIdCard.setText("数据未填写");
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

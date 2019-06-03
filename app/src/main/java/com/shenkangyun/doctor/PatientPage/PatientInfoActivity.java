package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientInfoActivity extends AppCompatActivity {


    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.hz_name)
    TextView hzName;
    @BindView(R.id.hz_age)
    TextView hzAge;
    @BindView(R.id.hz_idCard)
    TextView hzIdCard;
    @BindView(R.id.hz_birthday)
    TextView hzBirthday;
    @BindView(R.id.hz_mobile)
    TextView hzMobile;
    private int id;
    private String idCard;
    private String name;
    private int age;
    private String sex;
    private String birthday;
    private String mobile;
    private String loginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("患者详情");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initDate();
    }

    private void initDate() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        idCard = intent.getStringExtra("idCard");
        name = intent.getStringExtra("name");
        sex = intent.getStringExtra("sex");
        age = intent.getIntExtra("age", 0);
        birthday = intent.getStringExtra("birthday");
        mobile = intent.getStringExtra("mobile");
        loginName = intent.getStringExtra("loginName");

        if (!TextUtils.isEmpty(name)) {
            String firstWord = name.substring(0, 1);
            hzName.setText(name);
            tvWord.setText(firstWord);
        } else {
            String firstWord = loginName.substring(0, 1);
            hzName.setText(loginName);
            tvWord.setText(firstWord);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(age)).append("岁");
        hzAge.setText(builder.toString());
        hzIdCard.setText(idCard);
        hzMobile.setText(mobile);
        hzBirthday.setText(birthday);
    }

    @OnClick({R.id.medical_records, R.id.time_records, R.id.physical_condition, R.id.table_Milk, R.id.table_Pression, R.id.img_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.medical_records:
                Intent intentMedical = new Intent(this, MedicalRecordsActivity.class);
                intentMedical.putExtra("id", String.valueOf(id));
                startActivity(intentMedical);
                break;
            case R.id.time_records:
                Intent intentTime = new Intent(this, PatientMessageActivity.class);
                intentTime.putExtra("id", String.valueOf(id));
                startActivity(intentTime);
                break;
            case R.id.physical_condition:
                Intent intentQuestion = new Intent(this, QuestionnaireActivity.class);
                intentQuestion.putExtra("id", String.valueOf(id));
                startActivity(intentQuestion);
                break;
            case R.id.table_Milk:
                Intent intentMilk = new Intent(this, MilkActivity.class);
                intentMilk.putExtra("id", String.valueOf(id));
                startActivity(intentMilk);
                break;
            case R.id.table_Pression:
                Intent intentPression = new Intent(this, DepressedActivity.class);
                intentPression.putExtra("id", String.valueOf(id));
                startActivity(intentPression);
                break;
            case R.id.img_post:
                Intent intentPost = new Intent(this, PostIntentActivity.class);
                intentPost.putExtra("id", String.valueOf(id));
                startActivity(intentPost);
                break;
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

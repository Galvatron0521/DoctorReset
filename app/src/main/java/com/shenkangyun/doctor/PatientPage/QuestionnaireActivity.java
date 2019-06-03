package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionnaireActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("医生填写内容");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @OnClick({R.id.Childbirth, R.id.newborn, R.id.physical_condition})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Childbirth:
                Intent intentChild = new Intent(this, ChildBirthActivity.class);
                intentChild.putExtra("id", id);
                startActivity(intentChild);
                break;
            case R.id.newborn:
                Intent intentNewborn = new Intent(this, NewBornActivity.class);
                intentNewborn.putExtra("id", id);
                startActivity(intentNewborn);
                break;
            case R.id.physical_condition:
                Intent intentMother = new Intent(this, MotherActivity.class);
                intentMother.putExtra("id", id);
                startActivity(intentMother);
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

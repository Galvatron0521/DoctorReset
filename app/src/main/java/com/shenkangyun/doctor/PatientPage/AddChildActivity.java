package com.shenkangyun.doctor.PatientPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ResponseBean;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddChildActivity extends AppCompatActivity {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.ed_week)
    EditText edWeek;
    @BindView(R.id.ed_day)
    EditText edDay;
    @BindView(R.id.ed_Tai)
    EditText edTai;
    @BindView(R.id.ed_Chan)
    EditText edChan;
    @BindView(R.id.Fetal_azimuth)
    EditText FetalAzimuth;
    @BindView(R.id.full_term)
    EditText fullTerm;
    @BindView(R.id.preterm)
    EditText preterm;
    @BindView(R.id.abortion)
    EditText abortion;
    @BindView(R.id.exist)
    EditText exist;
    @BindView(R.id.birth)
    TextView birth;
    @BindView(R.id.complication)
    TextView complication;
    @BindView(R.id.birthDate)
    TextView birthDate;
    @BindView(R.id.height)
    EditText height;
    @BindView(R.id.weight)
    EditText weight;
    @BindView(R.id.femilk)
    RadioGroup femilk;
    @BindView(R.id.firstmilk)
    RadioGroup firstmilk;
    @BindView(R.id.mood)
    RadioGroup mood;
    @BindView(R.id.tfmilk)
    RadioGroup tfmilk;
    @BindView(R.id.tfmilk_zero)
    RadioButton tfmilkZero;
    @BindView(R.id.tfmilk_one)
    RadioButton tfmilkOne;
    @BindView(R.id.tfmilk_two)
    RadioButton tfmilkTwo;
    @BindView(R.id.femilk_zero)
    RadioButton femilkZero;
    @BindView(R.id.femilk_one)
    RadioButton femilkOne;
    @BindView(R.id.femilk_two)
    RadioButton femilkTwo;
    @BindView(R.id.firstmilk_zero)
    RadioButton firstmilkZero;
    @BindView(R.id.firstmilk_one)
    RadioButton firstmilkOne;
    @BindView(R.id.mood_zero)
    RadioButton moodZero;
    @BindView(R.id.mood_one)
    RadioButton moodOne;
    @BindView(R.id.firstmilk_two)
    RadioButton firstmilkTwo;
    @BindView(R.id.ed_complication)
    EditText edComplication;
    @BindView(R.id.complication_plus)
    LinearLayout complicationPlus;
    @BindView(R.id.tfmilk_three)
    RadioButton tfmilkThree;
    @BindView(R.id.femilk_three)
    RadioButton femilkThree;
    @BindView(R.id.mood_two)
    RadioButton moodTwo;

    private ListPopupWindow mListBirths;
    private ListPopupWindow mListComplications;

    private List<String> births = new ArrayList<>();
    private List<String> complications = new ArrayList<>();

    private String id;

    private String weekNum;
    private String dayNum;
    private int childWeeks;
    private String tire;
    private String chan;
    private String bearing;
    private String Fullterm;
    private String Preterm;
    private String Abortion;
    private String Exist;
    private int birthNum;
    private int Complication;
    private String ComplicationTxt;
    private String Height;
    private String Weight;
    private int Tfmilk = 5;
    private int Femilk = 5;
    private int Firstmilk = 5;
    private int Mood = 5;
    private String format;
    private String dataString;
    private String complicationString;
    private String birthString;
    private String edComplicationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("分娩情况");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initPopupWindow();
        initClick();
    }

    private void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        births.add("顺产");
        births.add("剖宫产");
        births.add("产钳产");

        complications.add("妊娠期糖尿病");
        complications.add("妊娠期高血压");
        complications.add("产后出血");
        complications.add("羊水过多");
        complications.add("羊水过少");
        complications.add("胎儿窘迫");
        complications.add("胎膜早破");
        complications.add("子痫前期重度");
        complications.add("其他");
        complications.add("无");

        edDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String dayNum = edDay.getText().toString();
                if (!TextUtils.isEmpty(dayNum) && Integer.valueOf(dayNum) >= 7) {
                    Toast.makeText(AddChildActivity.this, "请正确填入天数格式", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initPopupWindow() {
        mListBirths = new ListPopupWindow(this);
        mListBirths.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, births));
        mListBirths.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListBirths.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListBirths.setAnchorView(birth);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListBirths.setModal(true);//设置是否是模式
        mListBirths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                birth.setText(births.get(position));
                birthNum = position;
                mListBirths.dismiss();
            }
        });

        mListComplications = new ListPopupWindow(this);
        mListComplications.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, complications));
        mListComplications.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListComplications.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListComplications.setAnchorView(complication);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListComplications.setModal(true);//设置是否是模式
        mListComplications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                complication.setText(complications.get(position));
                if (complications.get(position).equals("其他")) {
                    complicationPlus.setVisibility(View.VISIBLE);
                } else {
                    complicationPlus.setVisibility(View.GONE);
                }
                Complication = position;
                ComplicationTxt = complications.get(position);
                mListComplications.dismiss();
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

    @OnClick({R.id.birth, R.id.complication, R.id.tv_save, R.id.birthDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birth:
                mListBirths.show();
                break;
            case R.id.birthDate:
                DatePickDialog datePickDialog = new DatePickDialog(this);
                TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                //设置上下年分限制
                datePickDialog.setYearLimt(80);
                //设置标题
                datePickDialog.setTitle("选择时间");
                //设置类型
                datePickDialog.setType(DateType.TYPE_YMD);
                //设置点击确定按钮回调
                datePickDialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        format = simpleDateFormat.format(date);
                        birthDate.setText(format);
                    }
                });
                datePickDialog.show();
                break;
            case R.id.complication:
                mListComplications.show();
                break;
            case R.id.tv_save:
                initData();
                break;
        }
    }

    private void initData() {
        weekNum = edWeek.getText().toString();
        dayNum = edDay.getText().toString();
        if ((!TextUtils.isEmpty(weekNum)) && (!TextUtils.isEmpty(dayNum))) {
            childWeeks = Integer.parseInt(weekNum) * 7 + Integer.parseInt(dayNum);
        }
        tire = edTai.getText().toString();
        chan = edChan.getText().toString();
        bearing = FetalAzimuth.getText().toString();
        Fullterm = fullTerm.getText().toString();
        Preterm = preterm.getText().toString();
        Abortion = abortion.getText().toString();
        Exist = exist.getText().toString();
        edComplicationTxt = edComplication.getText().toString();
        Height = height.getText().toString();
        Weight = weight.getText().toString();
        complicationString = complication.getText().toString();
        dataString = birthDate.getText().toString();
        birthString = birth.getText().toString();
        if (TextUtils.isEmpty(String.valueOf(weekNum))) {
            Toast.makeText(this, "孕周周数不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(dayNum))) {
            Toast.makeText(this, "孕周天数不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tire)) {
            Toast.makeText(this, "胎次不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(chan)) {
            Toast.makeText(this, "产次不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(bearing)) {
            Toast.makeText(this, "胎方位不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(Fullterm)) {
//            Toast.makeText(this, "足月产情况不能为空！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(Preterm)) {
//            Toast.makeText(this, "足月产情况不能为空！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(Abortion)) {
//            Toast.makeText(this, "请选择并发症类型！", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(Exist)) {
//            Toast.makeText(this, "请选择受教育程度！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if ("请选择".equals(birthString) || TextUtils.isEmpty(String.valueOf(birthNum))) {
            Toast.makeText(this, "请选择分娩方式！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("请选择".equals(dataString) || TextUtils.isEmpty(String.valueOf(format))) {
            Toast.makeText(this, "请选择分娩时间！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("请选择".equals(complicationString) || TextUtils.isEmpty(String.valueOf(Complication))) {
            Toast.makeText(this, "请选择妊娠合并症！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(edComplicationTxt) && (complicationPlus.getVisibility() != View.GONE)) {
            Toast.makeText(this, "并发症内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Height)) {
            Toast.makeText(this, "身高不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Weight)) {
            Toast.makeText(this, "体重不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Tfmilk == 5) {
            Toast.makeText(this, "请选择产后24小时泌乳量情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Femilk == 5) {
            Toast.makeText(this, "请选择产后48小时泌乳量情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Firstmilk == 5) {
            Toast.makeText(this, "请选择初次泌乳时间情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Mood == 5) {
            Toast.makeText(this, "请选择产后情绪情况！", Toast.LENGTH_SHORT).show();
            return;
        }

        initNetRequest();
    }

    private void initNetRequest() {
        if (ComplicationTxt.equals("其他") && !TextUtils.isEmpty(edComplicationTxt)) {
            ComplicationTxt = edComplicationTxt;
        } else {
            ComplicationTxt = String.valueOf(Complication);
        }
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "insertChildbirth")
                .addParams("data", new insertChildbirth(Base.getMD5Str(), Base.getTimeSpan(), "1", "2",
                        id, String.valueOf(childWeeks), tire, chan, bearing, Fullterm, Preterm, Abortion, Exist, String.valueOf(birthNum),
                        ComplicationTxt, Height, Weight, String.valueOf(Tfmilk),
                        String.valueOf(Femilk), String.valueOf(Firstmilk), String.valueOf(Mood), format).toJson())
                .build().execute(new GsonCallBack<ResponseBean>() {
            @Override
            public void onSuccess(ResponseBean response) throws JSONException {
                String status = response.getStatus();
                if ("0".equals(status)) {
                    Toast.makeText(AddChildActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                } else {
                    Toast.makeText(AddChildActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void initClick() {
        tfmilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (tfmilkZero.getId() == i) {
                    Tfmilk = 0;
                } else if (tfmilkOne.getId() == i) {
                    Tfmilk = 1;
                } else if (tfmilkTwo.getId() == i) {
                    Tfmilk = 2;
                } else {
                    Tfmilk = 3;
                }
            }
        });

        femilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (femilkZero.getId() == i) {
                    Femilk = 0;
                } else if (femilkOne.getId() == i) {
                    Femilk = 1;
                } else if (femilkTwo.getId() == i) {
                    Femilk = 2;
                } else {
                    Femilk = 3;
                }
            }
        });

        firstmilk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (firstmilkZero.getId() == i) {
                    Firstmilk = 0;
                } else if (firstmilkOne.getId() == i) {
                    Firstmilk = 1;
                } else if (firstmilkTwo.getId() == i) {
                    Firstmilk = 2;
                } else {
                    Firstmilk = 3;
                }
            }
        });

        mood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (moodZero.getId() == i) {
                    Mood = 0;
                } else if (moodOne.getId() == i) {
                    Mood = 1;
                } else {
                    Mood = 2;
                }
            }
        });
    }

    static class insertChildbirth {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String patientID;
        private String childWeeks;
        private String tire;
        private String chan;
        private String bearing;
        private String fullterm;
        private String preterm;
        private String abortion;
        private String exist;
        private String birth;
        private String complication;
        private String height;
        private String weight;
        private String tfmilk;
        private String femilk;
        private String firstmilk;
        private String mood;
        private String birthDate;

        public insertChildbirth(String appKey, String timeSpan, String mobileType, String appType, String patientID,
                                String childWeeks, String tire, String chan, String bearing, String fullterm,
                                String preterm, String abortion, String exist, String birth, String complication, String height,
                                String weight, String tfmilk, String femilk, String firstmilk, String mood, String birthDate) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.patientID = patientID;
            this.childWeeks = childWeeks;
            this.tire = tire;
            this.chan = chan;
            this.bearing = bearing;
            this.fullterm = fullterm;
            this.preterm = preterm;
            this.abortion = abortion;
            this.exist = exist;
            this.birth = birth;
            this.complication = complication;
            this.height = height;
            this.weight = weight;
            this.tfmilk = tfmilk;
            this.femilk = femilk;
            this.firstmilk = firstmilk;
            this.mood = mood;
            this.birthDate = birthDate;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

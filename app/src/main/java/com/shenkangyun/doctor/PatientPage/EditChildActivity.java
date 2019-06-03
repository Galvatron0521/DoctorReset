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
import android.util.Log;
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

public class EditChildActivity extends AppCompatActivity {

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
    EditText pretermEd;
    @BindView(R.id.abortion)
    EditText abortionEd;
    @BindView(R.id.exist)
    EditText existEd;
    @BindView(R.id.birth)
    TextView birthEd;
    @BindView(R.id.complication)
    TextView complicationTv;
    @BindView(R.id.height)
    EditText heightEd;
    @BindView(R.id.weight)
    EditText weightEd;
    @BindView(R.id.tfmilk_zero)
    RadioButton tfmilkZero;
    @BindView(R.id.tfmilk_one)
    RadioButton tfmilkOne;
    @BindView(R.id.tfmilk_two)
    RadioButton tfmilkTwo;
    @BindView(R.id.tfmilk_three)
    RadioButton tfmilkThree;
    @BindView(R.id.femilk_zero)
    RadioButton femilkZero;
    @BindView(R.id.femilk_one)
    RadioButton femilkOne;
    @BindView(R.id.femilk_two)
    RadioButton femilkTwo;
    @BindView(R.id.femilk_three)
    RadioButton femilkThree;
    @BindView(R.id.firstmilk_zero)
    RadioButton firstmilkZero;
    @BindView(R.id.firstmilk_one)
    RadioButton firstmilkOne;
    @BindView(R.id.firstmilk_two)
    RadioButton firstmilkTwo;
    @BindView(R.id.mood_zero)
    RadioButton moodZero;
    @BindView(R.id.mood_one)
    RadioButton moodOne;
    @BindView(R.id.mood_two)
    RadioButton moodTwo;
    @BindView(R.id.tfmilk)
    RadioGroup tfmilkRa;
    @BindView(R.id.femilk)
    RadioGroup femilkRa;
    @BindView(R.id.firstmilk)
    RadioGroup firstmilkRa;
    @BindView(R.id.mood)
    RadioGroup moodRa;
    @BindView(R.id.tv_birthDate)
    TextView tvBirthDate;
    @BindView(R.id.ed_complication)
    EditText edComplication;
    @BindView(R.id.complication_plus)
    LinearLayout complicationPlus;

    private int tableID;
    private long createTime;
    private long updateTime;
    private int chan;
    private int patientID;
    private int preterm;
    private int tire;
    private String bearing;
    private int fullterm;
    private int abortion;
    private int exist;
    private int birth;
    private String complication;
    private String height;
    private String weight;
    private int tfmilk;
    private int femilk;
    private int firstMilk;
    private int mood;
    private String birthDate;
    private int childWeeks;

    private String weekNum;
    private String dayNum;
    private int childWeeksNum;
    private String tireNum;
    private String chanNum;
    private String bearingTe;
    private String Fullterm;
    private String Preterm;
    private String Abortion;
    private String Exist;
    private int birthNum;
    private String Height;
    private String Weight;
    private int Tfmilk;
    private int Femilk;
    private int Firstmilk;
    private int Mood;

    private String format;
    private String complicationString;
    private String birthString;
    private String ComplicationTxt;
    private String edComplicationTxt;

    private boolean isNumeric = false;
    private boolean isWord = false;

    private ListPopupWindow mListBirths;
    private ListPopupWindow mListComplications;
    private SimpleDateFormat simpleDateFormat;
    private List<String> births = new ArrayList<>();
    private List<String> complications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            toolBarTitle.setText("分娩情况列表");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initData();
        initPopupWindow();
        initClick();
    }

    private void initPopupWindow() {
        mListBirths = new ListPopupWindow(this);
        mListBirths.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, births));
        mListBirths.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListBirths.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListBirths.setAnchorView(birthEd);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListBirths.setModal(true);//设置是否是模式
        mListBirths.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                birthEd.setText(births.get(position));
                birthNum = position;
                mListBirths.dismiss();
            }
        });

        mListComplications = new ListPopupWindow(this);
        mListComplications.setAdapter(new ArrayAdapter<String>(this, R.layout.item_popup, complications));
        mListComplications.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListComplications.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        mListComplications.setAnchorView(complicationTv);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
        mListComplications.setModal(true);//设置是否是模式
        mListComplications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                complicationTv.setText(complications.get(position));
                if (complications.get(position).equals("其他")) {
                    complicationPlus.setVisibility(View.VISIBLE);
                } else {
                    complicationPlus.setVisibility(View.GONE);
                }
                complication = complications.get(position);
                mListComplications.dismiss();
            }
        });
    }

    private void initClick() {
        tfmilkRa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        femilkRa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        firstmilkRa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

        moodRa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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

    private void initView() {
        Intent intent = getIntent();
        tableID = intent.getIntExtra("tableID", 0);
        createTime = intent.getLongExtra("createTime", 0);
        updateTime = intent.getLongExtra("updateTime", 0);
        chan = intent.getIntExtra("chan", 0);
        patientID = intent.getIntExtra("patientID", 0);
        preterm = intent.getIntExtra("preterm", 0);
        tire = intent.getIntExtra("tire", 0);
        bearing = intent.getStringExtra("bearing");
        fullterm = intent.getIntExtra("fullterm", 0);
        abortion = intent.getIntExtra("abortion", 0);
        exist = intent.getIntExtra("exist", 0);
        birth = intent.getIntExtra("birth", 0);
        complication = intent.getStringExtra("complication");
        height = intent.getStringExtra("height");
        weight = intent.getStringExtra("weight");
        tfmilk = intent.getIntExtra("Tfmilk", 0);
        femilk = intent.getIntExtra("femilk", 0);
        firstMilk = intent.getIntExtra("firstMilk", 0);
        mood = intent.getIntExtra("mood", 0);
        birthDate = intent.getStringExtra("birthDate");
        childWeeks = intent.getIntExtra("childWeeks", 0);


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
                    Toast.makeText(EditChildActivity.this, "请正确填入天数格式", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData() {
        int weekNum = childWeeks / 7;
        int dayNum = childWeeks % 7;
        edWeek.setText(String.valueOf(weekNum));
        edDay.setText(String.valueOf(dayNum));
        edTai.setText(String.valueOf(tire));
        edChan.setText(String.valueOf(chan));
        FetalAzimuth.setText(bearing);
        fullTerm.setText(String.valueOf(fullterm));
        pretermEd.setText(String.valueOf(preterm));
        abortionEd.setText(String.valueOf(abortion));
        existEd.setText(String.valueOf(exist));
        for (int i = 0; i < births.size(); i++) {
            if (birth == i) {
                birthEd.setText(births.get(i));
                birthNum = i;
            }
        }
        if (!TextUtils.isEmpty(complication)) {
            isNumeric = complication.matches("[0-9]");
            String regex = "[\\u4e00-\\u9fa5]";
            isWord = complication.matches(regex);
            if (isNumeric == true && isWord == false) {
                for (int i = 0; i < complications.size(); i++) {
                    if (Integer.valueOf(complication) == i) {
                        complicationTv.setText(complications.get(i));
                        break;
                    } else {
                        complicationTv.setText(complication);
                    }
                }
            }
            if ((isNumeric == false && isWord == true) || (isNumeric == false && isWord == false)) {
                complicationTv.setText(complication);
            }
        }
        heightEd.setText(height);
        weightEd.setText(weight);
        tvBirthDate.setText(birthDate);

        Tfmilk = tfmilk;
        Femilk = femilk;
        Firstmilk = firstMilk;
        Mood = mood;

        if (tfmilk == 0) {
            tfmilkZero.setChecked(true);
            tfmilkOne.setChecked(false);
            tfmilkTwo.setChecked(false);
            tfmilkThree.setChecked(false);
        }
        if (tfmilk == 1) {
            tfmilkZero.setChecked(false);
            tfmilkOne.setChecked(true);
            tfmilkTwo.setChecked(false);
            tfmilkThree.setChecked(false);
        }
        if (tfmilk == 2) {
            tfmilkZero.setChecked(false);
            tfmilkOne.setChecked(false);
            tfmilkTwo.setChecked(true);
            tfmilkThree.setChecked(false);
        }
        if (tfmilk == 3) {
            tfmilkZero.setChecked(false);
            tfmilkOne.setChecked(false);
            tfmilkTwo.setChecked(false);
            tfmilkThree.setChecked(true);
        }

        if (femilk == 0) {
            femilkZero.setChecked(true);
            femilkOne.setChecked(false);
            femilkTwo.setChecked(false);
            femilkThree.setChecked(false);
        }
        if (femilk == 1) {
            femilkZero.setChecked(false);
            femilkOne.setChecked(true);
            femilkTwo.setChecked(false);
            femilkThree.setChecked(false);
        }
        if (femilk == 2) {
            femilkZero.setChecked(false);
            femilkOne.setChecked(false);
            femilkTwo.setChecked(true);
            femilkThree.setChecked(false);
        }
        if (femilk == 3) {
            femilkZero.setChecked(false);
            femilkOne.setChecked(false);
            femilkTwo.setChecked(false);
            femilkThree.setChecked(true);
        }

        if (firstMilk == 0) {
            firstmilkZero.setChecked(true);
            firstmilkOne.setChecked(false);
            firstmilkTwo.setChecked(false);
        }
        if (firstMilk == 1) {
            firstmilkZero.setChecked(false);
            firstmilkOne.setChecked(true);
            firstmilkTwo.setChecked(false);
        }
        if (firstMilk == 2) {
            firstmilkZero.setChecked(false);
            firstmilkOne.setChecked(false);
            firstmilkTwo.setChecked(true);
        }

        if (mood == 0) {
            moodZero.setChecked(true);
            moodOne.setChecked(false);
            moodTwo.setChecked(false);
        }
        if (mood == 1) {
            moodZero.setChecked(false);
            moodOne.setChecked(true);
            moodTwo.setChecked(false);
        }
        if (mood == 2) {
            moodZero.setChecked(false);
            moodOne.setChecked(false);
            moodTwo.setChecked(true);
        }
    }

    @OnClick({R.id.birth, R.id.complication, R.id.tv_save, R.id.tv_birthDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.birth:
                mListBirths.show();
                break;
            case R.id.complication:
                mListComplications.show();
                break;
            case R.id.tv_save:
                getNetData();
                break;
            case R.id.tv_birthDate:
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

                        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        format = simpleDateFormat.format(date);
                        tvBirthDate.setText(format);
                    }
                });
                datePickDialog.show();
                break;
        }
    }

    private void getNetData() {
        weekNum = edWeek.getText().toString();
        dayNum = edDay.getText().toString();
        childWeeks = Integer.parseInt(weekNum) * 7 + Integer.parseInt(dayNum);
        tireNum = edTai.getText().toString();
        chanNum = edChan.getText().toString();
        bearing = FetalAzimuth.getText().toString();
        Fullterm = fullTerm.getText().toString();
        Preterm = pretermEd.getText().toString();
        Abortion = abortionEd.getText().toString();
        edComplicationTxt = edComplication.getText().toString();
        Exist = existEd.getText().toString();
        Height = heightEd.getText().toString();
        Weight = weightEd.getText().toString();
        birthString = birthEd.getText().toString();
        complicationString = complicationTv.getText().toString();
        if (TextUtils.isEmpty(String.valueOf(weekNum))) {
            Toast.makeText(this, "孕周不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(dayNum))) {
            Toast.makeText(this, "孕周不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tireNum)) {
            Toast.makeText(this, "胎次不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(chanNum)) {
            Toast.makeText(this, "产次年龄不能为空！", Toast.LENGTH_SHORT).show();
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
        if (TextUtils.isEmpty(Height)) {
            Toast.makeText(this, "身高不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Weight)) {
            Toast.makeText(this, "体重不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("请选择".equals(birthString) || TextUtils.isEmpty(String.valueOf(birthNum))) {
            Toast.makeText(this, "请选择分娩方式！", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("请选择".equals(complicationString) || TextUtils.isEmpty(complication)) {
            Toast.makeText(this, "请选择妊娠合并症！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(edComplicationTxt) && (complicationPlus.getVisibility() != View.GONE)) {
            Toast.makeText(this, "并发症内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(String.valueOf(Tfmilk))) {
            Toast.makeText(this, "请选择产后24小时泌乳量情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(Femilk))) {
            Toast.makeText(this, "请选择产后48小时泌乳量情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(Firstmilk))) {
            Toast.makeText(this, "请选择初次泌乳时间情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(String.valueOf(Mood))) {
            Toast.makeText(this, "请选择产后情绪情况！", Toast.LENGTH_SHORT).show();
            return;
        }
        initNetRequest();
    }

    private void initNetRequest() {
        if (complicationString.equals("其他") && (!TextUtils.isEmpty(edComplicationTxt))) {
            ComplicationTxt = edComplicationTxt;
        } else {
            ComplicationTxt = complication;
        }
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "updateChildbirth")
                .addParams("data", new updateChildbirth(Base.appKey, Base.timeSpan, "1", "2",
                        String.valueOf(patientID), String.valueOf(childWeeks), String.valueOf(tableID), tireNum, chanNum, bearing, Fullterm, Preterm, Abortion, Exist, String.valueOf(birthNum),
                        ComplicationTxt, Height, Weight, String.valueOf(Tfmilk),
                        String.valueOf(Femilk), String.valueOf(Firstmilk), String.valueOf(Mood)).toJson())
                .build()
                .execute(new GsonCallBack<ResponseBean>() {
                    @Override
                    public void onSuccess(ResponseBean response) throws JSONException {
                        String status = response.getStatus();
                        if ("0".equals(status)) {
                            Toast.makeText(EditChildActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            setResult(2, intent);
                            finish();
                        } else {
                            Toast.makeText(EditChildActivity.this, response.getData().getData(), Toast.LENGTH_SHORT).show();
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

    static class updateChildbirth {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String patientID;
        private String childWeeks;
        private String childbirthID;
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

        public updateChildbirth(String appKey, String timeSpan, String mobileType, String appType, String patientID,
                                String childWeeks, String childbirthID, String tire, String chan, String bearing, String fullterm,
                                String preterm, String abortion, String exist, String birth, String complication, String height,
                                String weight, String tfmilk, String femilk, String firstmilk, String mood) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.patientID = patientID;
            this.childWeeks = childWeeks;
            this.childbirthID = childbirthID;
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
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }

}
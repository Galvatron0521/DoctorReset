package com.shenkangyun.doctor.LoginPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.BaseFolder.AppConst;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.LoginBean;
import com.shenkangyun.doctor.DBFolder.User;
import com.shenkangyun.doctor.MainPage.MainActivity;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.shenkangyun.doctor.UtilsFolder.NToast;
import com.shenkangyun.doctor.UtilsFolder.TagAliasOperatorHelper;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shenkangyun.doctor.UtilsFolder.TagAliasOperatorHelper.ACTION_SET;
import static com.shenkangyun.doctor.UtilsFolder.TagAliasOperatorHelper.sequence;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.de_login_phone)
    EditText deLoginPhone;
    @BindView(R.id.de_login_password)
    EditText deLoginPassword;
    @BindView(R.id.de_img_backgroud)
    ImageView deImgBackgroud;

    private String phoneString;
    private String passwordString;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private int userId;
    private int sex;
    private String name;
    private String idCard;
    private String mobile;
    private String loginName;
    private String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(LoginActivity.this, 55);
        ButterKnife.bind(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        initView();
    }

    private void initView() {
        String oldPhone = sp.getString(AppConst.LOGING_PHONE, "");
        String oldPassword = sp.getString(AppConst.LOGING_PASSWORD, "");

        if (!TextUtils.isEmpty(oldPhone) && !TextUtils.isEmpty(oldPassword)) {
            phoneString = oldPhone;
            passwordString = oldPassword;
            deLoginPhone.setText(oldPhone);
            deLoginPassword.setText(oldPassword);
            goToMain();
        }
        if (!TextUtils.isEmpty(oldPhone) && TextUtils.isEmpty(oldPassword)) {
            phoneString = oldPhone;
            deLoginPhone.setText(oldPhone);
        }
    }

    @OnClick({R.id.de_login_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.de_login_sign:
                phoneString = deLoginPhone.getText().toString().trim();
                passwordString = deLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phoneString)) {
                    NToast.shortToast(this, R.string.phone_number_is_null);
                    return;
                }
//                if (!AMUtils.isMobile(phoneString)) {
//                    NToast.shortToast(this, R.string.Illegal_phone_number);
//                    deLoginPhone.setShakeAnimation();
//                    return;
//                }
                if (TextUtils.isEmpty(passwordString)) {
                    NToast.shortToast(this, R.string.password_is_null);
                    return;
                }
                if (passwordString.contains(" ")) {
                    NToast.shortToast(this, R.string.password_cannot_contain_spaces);
                    return;
                }
                goToMain();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String userName = data.getStringExtra("UserName");
        String passWord = data.getStringExtra("PassWord");
        if (requestCode == 0 && resultCode == 1
                && !TextUtils.isEmpty(userName) && !TextUtils.isEmpty(passWord)) {
            deLoginPhone.setText(userName);
            deLoginPassword.setText(passWord);
        }
    }

    private void goToMain() {
        LitePal.deleteAll(User.class);
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "login")
                .addParams("data", new Login(Base.getMD5Str(), Base.getTimeSpan(), "1", "2", phoneString, passwordString).toJson())
                .build()
                .execute(new GsonCallBack<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean response) {
                        String status = response.getStatus();
                        if ("0".equals(status)) {
                            initUserInfo(response);
                            onTagAliasAction(phoneString);
                            initEM();
                            editor.putString(AppConst.LOGING_PHONE, phoneString);
                            editor.putString(AppConst.LOGING_PASSWORD, passwordString);
                            editor.commit();
                            NToast.shortToast(LoginActivity.this, R.string.login_success);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void initUserInfo(LoginBean response) {

        userId = response.getData().getPatient().getUserID();
        name = response.getData().getPatient().getName();
        idCard = response.getData().getPatient().getIDCard();
        sex = response.getData().getPatient().getSex();
        mobile = response.getData().getPatient().getMobile();
        birthday = response.getData().getPatient().getBrithday();
        loginName = response.getData().getPatient().getLoginName();

        User user = new User();
        user.setUserID(userId);
        user.setMobile(mobile);
        user.setIDCard(idCard);
        user.setName(name);
        user.setBirthday(birthday);
        user.setLoginName(loginName);
        user.save();
    }

    public void onTagAliasAction(String alias) {
        int action = -1;
        boolean isAliasAction = true;

        if (TextUtils.isEmpty(alias)) {
            return;
        }
        action = ACTION_SET;

        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.action = action;
        sequence++;
        tagAliasBean.alias = alias;

        tagAliasBean.isAliasAction = isAliasAction;
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(), sequence, tagAliasBean);
    }

    private void initEM() {

    }

    static class Login {
        private String appKey;
        private String timeSpan;
        private String mobileType;
        private String appType;
        private String username;
        private String password;

        public Login(String appKey, String timeSpan, String mobileType, String appType, String username, String password) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.mobileType = mobileType;
            this.appType = appType;
            this.username = username;
            this.password = password;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

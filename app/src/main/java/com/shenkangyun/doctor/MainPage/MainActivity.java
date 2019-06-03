package com.shenkangyun.doctor.MainPage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.shenkangyun.doctor.MainPage.Fragment.FuYouFragment;
import com.shenkangyun.doctor.MainPage.Fragment.KePuFragment;
import com.shenkangyun.doctor.MainPage.Fragment.UserFragment;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.ExampleUtil;
import com.shenkangyun.doctor.UtilsFolder.LocalBroadcastManager;
import com.shenkangyun.doctor.UtilsFolder.NetworkChangeReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.rb_fuyou)
    RadioButton rbFuyou;
    @BindView(R.id.rb_kepu)
    RadioButton rbKepu;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.img_add)
    ImageView imgAdd;

    private FragmentManager fragmentManager;
    private Fragment fuYouFragment, kePuFragment, userFragment, replaceFragment;

    private IntentFilter filter;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver changeReceiver;
    public static boolean isForeground = false;

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "cn.jpush.android.intent.NOTIFICATION_RECEIVED";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.login_red));
        registerMessageReceiver();
        initView();
        initFragment();
        initClick();
    }

    private void initView() {
        toolBarTitle.setText("妇幼");
        fuYouFragment = new FuYouFragment();
        kePuFragment = new KePuFragment();
        userFragment = new UserFragment();
        fragmentManager = getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, fuYouFragment);
        fragmentTransaction.commit();
        replaceFragment = fuYouFragment;
    }

    private void initClick() {
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPatientActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(changeReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void registerMessageReceiver() {
        intentFilter = new IntentFilter();
        changeReceiver = new NetworkChangeReceiver();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(changeReceiver, intentFilter);

        mMessageReceiver = new MessageReceiver();
        filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.rb_fuyou:
                toolBarTitle.setText("妇幼");
                replaceFragment(fuYouFragment, fragmentTransaction);
                if (imgAdd.getVisibility() == View.GONE) {
                    imgAdd.setVisibility(View.VISIBLE);
                } else {
                    imgAdd.setVisibility(View.GONE);
                }
                break;
            case R.id.rb_kepu:
                toolBarTitle.setText("科普");
                replaceFragment(kePuFragment, fragmentTransaction);
                imgAdd.setVisibility(View.GONE);
                break;
            case R.id.rb_user:
                toolBarTitle.setText("我的");
                replaceFragment(userFragment, fragmentTransaction);
                imgAdd.setVisibility(View.GONE);
                break;
        }
    }

    public void replaceFragment(Fragment showFragment, FragmentTransaction fragmentTransaction) {
        if (showFragment.isAdded()) {
            fragmentTransaction.hide(replaceFragment).show(showFragment).commit();
        } else {
            fragmentTransaction.hide(replaceFragment).add(R.id.frameLayout, showFragment).commit();
        }
        replaceFragment = showFragment;
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}

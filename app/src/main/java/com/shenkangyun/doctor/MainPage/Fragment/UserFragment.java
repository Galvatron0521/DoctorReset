package com.shenkangyun.doctor.MainPage.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shenkangyun.doctor.BaseFolder.AppConst;
import com.shenkangyun.doctor.BaseFolder.Base;
import com.shenkangyun.doctor.BeanFolder.ApkUpBean;
import com.shenkangyun.doctor.DBFolder.User;
import com.shenkangyun.doctor.LoginPage.LoginActivity;
import com.shenkangyun.doctor.R;
import com.shenkangyun.doctor.UtilsFolder.APKVersionCodeUtils;
import com.shenkangyun.doctor.UtilsFolder.GsonCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.litepal.LitePal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

public class UserFragment extends Fragment {
    @BindView(R.id.tv_word)
    TextView tvWord;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    private String name;
    private String birthday;
    private String mobile;
    private SharedPreferences sp;

    private ProgressDialog pd;
    private String versionName;
    private String appVersion;
    private String appUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        versionName = APKVersionCodeUtils.getVerName(getActivity());
        sp = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        tvVersion.setText(versionName);
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            name = all.get(i).getName();
            birthday = all.get(i).getBirthday();
            mobile = all.get(i).getMobile();
        }
        if (!TextUtils.isEmpty(name)) {
            String firstWord = name.substring(0, 1);
            tvName.setText(name);
            tvWord.setText(firstWord);
        }
        if (!TextUtils.isEmpty(mobile)) {
            tvNumber.setText(mobile);

        } else {
            tvNumber.setText("数据未填写");
        }

        if (!TextUtils.isEmpty(birthday)) {
            tvDate.setText(birthday);
        } else {
            tvDate.setText("数据未填写");
        }
    }

    @OnClick({R.id.layout_update, R.id.img_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_update:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(getContext(), "请授权！", Toast.LENGTH_LONG).show();

                        // 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intentSet = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intentSet.setData(uri);
                        intentSet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentSet);
                    } else {
                        // 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10000);
                    }
                } else {
                    // 已经获得授权，可以打电话
                    initUpdate();
                }
                break;
            case R.id.img_quit:
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(AppConst.LOGING_PASSWORD, "");
                edit.commit();
                Intent intentExit = new Intent(getContext(), LoginActivity.class);
                startActivity(intentExit);
                getActivity().finish();
                break;
        }
    }

    private void initUpdate() {
        OkHttpUtils.post()
                .url(Base.URL)
                .addParams("act", "appVesionCompare")
                .addParams("data", new appVesionCompare(Base.appKey, Base.timeSpan, 2, "1", versionName).toJson())
                .build()
                .execute(new GsonCallBack<ApkUpBean>() {
                    @Override
                    public void onSuccess(ApkUpBean response) throws JSONException {
                        appVersion = response.getData().getData().getApp_parent_version();
                        appUrl = response.getData().getData().getApp_parent_version_url();
                        if (appVersion.equals(versionName)) {
                            Toast.makeText(getContext(), "已是最新版本", Toast.LENGTH_SHORT).show();
                        } else {
                            showDialogUpdate();//弹出提示版本更新的对话框
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case 10000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    initUpdate();
                } else {
                    // 授权失败！
                    Toast.makeText(getActivity(), "授权失败！", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showDialogUpdate() {
        // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 设置提示框的标题
        builder.setTitle("版本升级").
                // 设置要显示的信息
                        setMessage("发现新版本！请及时更新").
                // 设置确定按钮
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadNewVersionProgress();//下载最新的版本程序
                    }
                }).setNegativeButton("取消", null);

        // 生产对话框
        AlertDialog alertDialog = builder.create();
        // 显示对话框
        alertDialog.show();

    }

    private void loadNewVersionProgress() {
        //进度条对话框
        pd = new ProgressDialog(getActivity());
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        //启动子线程下载任务
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(appUrl, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    //下载apk失败
                    Message message = new Message();
                    message.arg1 = 1;
                    handler.sendMessage(message);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                pd.dismiss();
                Toast.makeText(getContext(), "下载新版本失败", Toast.LENGTH_LONG).show();
            }
        }
    };

    private void installApk(File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //来判断应用是否有权限安装apk
            boolean b = getActivity().getPackageManager().canRequestPackageInstalls();
            if (b) {
               install(file);
            } else {
                //设置安装未知应用来源的权限
                Intent intentInstall = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                startActivityForResult(intentInstall, 10012);
            }
        } else {
            install(file);
        }
    }

    private void install(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) { //provider authorities
            Uri apkUri = FileProvider.getUriForFile(getActivity(), "com.shenkangyun.doctor.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }


    private File getFileFromServer(String appUrl, ProgressDialog pd) throws IOException {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(appUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    static class appVesionCompare {
        private String appKey;
        private String timeSpan;
        private int appType;
        private String mobileType;
        private String version;

        public appVesionCompare(String appKey, String timeSpan, int appType, String mobileType, String version) {
            this.appKey = appKey;
            this.timeSpan = timeSpan;
            this.appType = appType;
            this.mobileType = mobileType;
            this.version = version;
        }

        public String toJson() {
            return new Gson().toJson(this);
        }
    }
}

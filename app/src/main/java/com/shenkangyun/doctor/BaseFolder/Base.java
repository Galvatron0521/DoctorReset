package com.shenkangyun.doctor.BaseFolder;


import com.shenkangyun.doctor.DBFolder.User;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/5.
 */

public class Base {
//    public static final String URL = "http://192.168.0.210:8080/skyapp_cfyou/api/app_patient/";
//    public static final String saveFieldURL = "http://192.168.0.210:8080/skyapp_cfyou/form/saveFieldRecordJson.json";

//        public static final String URL = "http://192.168.0.118:8080/skyapp_cfyou/api/app_patient/";
//    public static final String saveFieldURL = "http://192.168.0.118:8080/skyapp_cfyou/form/saveFieldRecordJson.json";
    public static final String saveFieldURL = "http://111.17.215.37:806/skyapp_cfyou/form/saveFieldRecordJson.json";
    public static final String URL = "http://111.17.215.37:806/skyapp_cfyou/api/app_patient/";
    public static final String WeChat_URL = "http://shenkangyun.com:806/skyapp_cfyou/api/app_patient/";

    public static final String appKey = "dc4d8d31d749ecb86157449d2fb804e0";
    public static final String timeSpan = "20101020";

    public static String getMD5Str() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd");
        String md5 = new Md5Hash("shenkangyun_canlian_patient", timeFormat.format(new Date()), 1).toString();
        return md5;
    }

    public static String getTimeSpan() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd");
        return timeFormat.format(new Date());
    }

    public static int getUserID() {
        int userID = 0;
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            userID = all.get(i).getUserID();
        }
        return userID;
    }

    public static String getLoginName() {
        String loginName = "";
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            loginName = all.get(i).getLoginName();
        }
        return loginName;
    }

    public static String getMobile() {
        String mobile = "";
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            mobile = all.get(i).getMobile();
        }
        return mobile;
    }

    public static String getIDCard() {
        String idCard = "";
        List<User> all = LitePal.findAll(User.class);
        for (int i = 0; i < all.size(); i++) {
            idCard = all.get(i).getIDCard();
        }
        return idCard;
    }
}
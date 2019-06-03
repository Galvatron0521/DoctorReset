package com.shenkangyun.doctor.MainPage.adapter;

import android.text.TextUtils;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.PatientBean;
import com.shenkangyun.doctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/7/31.
 */

public class PatientListAdapter extends BaseQuickAdapter<PatientBean.DataBean.ListBean, BaseViewHolder> {

    public PatientListAdapter() {
        super(R.layout.item_patient, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PatientBean.DataBean.ListBean item) {
        if (TextUtils.isEmpty(item.getName()) || "null".equals(item.getName())) {
            helper.setText(R.id.hz_name, item.getLoginName());
        } else {
            helper.setText(R.id.hz_name, item.getName());
        }

        if (TextUtils.isEmpty(item.getName()) || "null".equals(item.getName())) {
            if (!TextUtils.isEmpty(item.getLoginName())) {
                String firstWord = item.getLoginName().substring(0, 1);
                helper.setText(R.id.hz_xing, firstWord);
            } else {
                helper.setText(R.id.hz_xing, "空");
            }

        } else {
            String firstWord = item.getName().substring(0, 1);
            helper.setText(R.id.hz_xing, firstWord);
        }

        helper.addOnClickListener(R.id.content);
        helper.addOnClickListener(R.id.right);
    }
}

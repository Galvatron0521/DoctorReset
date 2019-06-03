package com.shenkangyun.doctor.PatientPage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.NewBornBean;
import com.shenkangyun.doctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/8.
 */

public class NewBornAdapter extends BaseQuickAdapter<NewBornBean.DataBean.ListBean, BaseViewHolder> {

    public NewBornAdapter() {
        super(R.layout.item_child_birth, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewBornBean.DataBean.ListBean item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date(item.getCreateTime());
        String format = dateFormat.format(date);
        helper.setText(R.id.table_time, format);
    }
}
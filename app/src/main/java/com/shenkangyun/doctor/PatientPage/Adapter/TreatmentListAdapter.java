package com.shenkangyun.doctor.PatientPage.Adapter;

import android.text.TextUtils;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.AddTreatBean;
import com.shenkangyun.doctor.BeanFolder.ShowTableEntity;
import com.shenkangyun.doctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/6.
 */

public class TreatmentListAdapter extends BaseMultiItemQuickAdapter<ShowTableEntity, BaseViewHolder> {


    public TreatmentListAdapter() {
        super(null);
        addItemType(ShowTableEntity.TITLE, R.layout.item_title);
        addItemType(ShowTableEntity.TABLE, R.layout.item_treat_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowTableEntity item) {
        switch (item.getItemType()) {
            case 1:
                helper.setText(R.id.table_time_title, item.getTitle());
                break;
            case 2:
                Date date = new Date(item.getUpdateTime());
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日yyyy年");
                String format = dateFormat.format(date);
                helper.setText(R.id.table_time, format);
                helper.setText(R.id.table_title, item.getModelName());
                helper.addOnClickListener(R.id.table_content);
                break;
        }
    }

}
package com.shenkangyun.doctor.PatientPage.Adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.ShowTableEntity;
import com.shenkangyun.doctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/4.
 */

public class ConstitutionListAdapter extends BaseMultiItemQuickAdapter<ShowTableEntity, BaseViewHolder> {

    public ConstitutionListAdapter() {
        super(null);
        addItemType(ShowTableEntity.TITLE, R.layout.item_title);
        addItemType(ShowTableEntity.TABLE, R.layout.item_constitution);
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
                helper.setText(R.id.table_groupScore, item.getGroupScore());
                if ("null".equals(item.getResults()) || TextUtils.isEmpty(item.getResults())) {
                    helper.setVisible(R.id.layout_result, false);
                } else {
                    helper.setText(R.id.table_result, item.getResults());
                    helper.setVisible(R.id.layout_result, true);
                }
                helper.setText(R.id.table_title, item.getModelName());
                helper.addOnClickListener(R.id.table_content);
                break;
        }
    }
}

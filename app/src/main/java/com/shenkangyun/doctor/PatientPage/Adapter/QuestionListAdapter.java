package com.shenkangyun.doctor.PatientPage.Adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.ShowTableEntity;
import com.shenkangyun.doctor.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/12/3.
 */

public class QuestionListAdapter extends BaseMultiItemQuickAdapter<ShowTableEntity, BaseViewHolder> {

    public QuestionListAdapter() {
        super(null);
        addItemType(ShowTableEntity.TITLE, R.layout.item_title);
        addItemType(ShowTableEntity.TABLE, R.layout.item_table);
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
                if ("null".equals(item.getScores()) || TextUtils.isEmpty(item.getScores())) {
                    helper.setVisible(R.id.layout_score, false);
                } else {
                    helper.setText(R.id.table_score, item.getScores());
                    helper.setVisible(R.id.layout_score, true);
                }
                if ("null".equals(item.getResults()) || TextUtils.isEmpty(item.getResults())) {
                    helper.setVisible(R.id.layout_result, false);
                } else {
                    helper.setText(R.id.table_result, item.getResults());
                    helper.setVisible(R.id.layout_result, true);
                }
                helper.setText(R.id.table_title, item.getModelName());
                if (item.getModelName().equals("中医体质分类与判定自测表")) {
                    helper.setText(R.id.table_groupScore, item.getGroupScore());
                    helper.setVisible(R.id.layout_score, false);
                    helper.setVisible(R.id.layout_groupScore, true);
                }
                helper.addOnClickListener(R.id.table_content);
                break;
        }
    }
}

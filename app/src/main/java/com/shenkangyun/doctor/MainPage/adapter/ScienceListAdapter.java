package com.shenkangyun.doctor.MainPage.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.ScienceBean;
import com.shenkangyun.doctor.R;

/**
 * Created by Administrator on 2018/8/3.
 */

public class ScienceListAdapter extends BaseQuickAdapter<ScienceBean.DataBean.ListBean, BaseViewHolder> {
    public ScienceListAdapter() {
        super(R.layout.item_science_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScienceBean.DataBean.ListBean item) {
        helper.setText(R.id.science_name, item.getArticleName());
        helper.addOnClickListener(R.id.content);
    }
}

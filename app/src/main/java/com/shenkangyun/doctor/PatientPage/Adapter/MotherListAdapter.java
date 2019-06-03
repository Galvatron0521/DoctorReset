package com.shenkangyun.doctor.PatientPage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.MotherBean;
import com.shenkangyun.doctor.R;

/**
 * Created by Administrator on 2018/12/4.
 */

public class MotherListAdapter extends BaseQuickAdapter<MotherBean.DataBean.SubmenuListBean, BaseViewHolder> {
    public MotherListAdapter() {
        super(R.layout.item_mother_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, MotherBean.DataBean.SubmenuListBean item) {
        helper.setText(R.id.moduleNum, String.valueOf(item.getModuleNum()));
        helper.setText(R.id.moduleName, item.getModuleName());
        helper.addOnClickListener(R.id.content);
    }
}

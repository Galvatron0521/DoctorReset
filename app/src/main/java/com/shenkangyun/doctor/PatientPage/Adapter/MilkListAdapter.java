package com.shenkangyun.doctor.PatientPage.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.LactationBean;
import com.shenkangyun.doctor.R;

/**
 * Created by Administrator on 2018/12/12.
 */

public class MilkListAdapter extends BaseQuickAdapter<LactationBean.DataBean.SubmenuListBean, BaseViewHolder> {
    public MilkListAdapter() {
        super(R.layout.item_mother_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, LactationBean.DataBean.SubmenuListBean item) {
        helper.setText(R.id.moduleNum, String.valueOf(item.getModuleNum()));
        helper.setText(R.id.moduleName, item.getModuleName());
        helper.addOnClickListener(R.id.content);
    }
}

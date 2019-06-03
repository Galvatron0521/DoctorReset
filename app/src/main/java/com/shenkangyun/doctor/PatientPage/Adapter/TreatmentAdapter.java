package com.shenkangyun.doctor.PatientPage.Adapter;

import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.AddTreatBean;
import com.shenkangyun.doctor.R;

/**
 * Created by Administrator on 2018/12/4.
 */

public class TreatmentAdapter extends BaseQuickAdapter<AddTreatBean.DataBean.FieldListBean.ChildListBeanX, BaseViewHolder> {


    public TreatmentAdapter() {
        super(R.layout.item_treat_add, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddTreatBean.DataBean.FieldListBean.ChildListBeanX item) {

        RadioGroup rg = helper.getView(R.id.option);
        if (!item.getChildList().get(0).isChecked() && !item.getChildList().get(1).isChecked()) {
            rg.clearCheck();
        } else {
            if (item.getChildList().get(0).isChecked()) {
                helper.setChecked(R.id.option_A, true);
            }

            if (item.getChildList().get(1).isChecked()) {
                helper.setChecked(R.id.option_B, true);
            }
        }
        helper.setText(R.id.question_title, item.getDisplayName());
        helper.setText(R.id.option_A, item.getChildList().get(0).getDisplayName());
        helper.setText(R.id.option_B, item.getChildList().get(1).getDisplayName());

        helper.addOnClickListener(R.id.option_A);
        helper.addOnClickListener(R.id.option_B);
    }

}
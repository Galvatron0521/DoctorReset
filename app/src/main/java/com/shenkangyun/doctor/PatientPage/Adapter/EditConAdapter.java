package com.shenkangyun.doctor.PatientPage.Adapter;

import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shenkangyun.doctor.BeanFolder.EditConBean;
import com.shenkangyun.doctor.R;

/**
 * Created by Administrator on 2018/12/4.
 */

public class EditConAdapter extends BaseQuickAdapter<EditConBean.DataBean.FieldListBean.ChildListBeanX, BaseViewHolder> {


    public EditConAdapter() {
        super(R.layout.item_con_question, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, EditConBean.DataBean.FieldListBean.ChildListBeanX item) {

        RadioGroup rg = helper.getView(R.id.option);

        if (!item.getChildList().get(0).isChecked() && !item.getChildList().get(1).isChecked() && !item.getChildList().get(2).isChecked()
                && !item.getChildList().get(3).isChecked() && !item.getChildList().get(4).isChecked()) {
            rg.clearCheck();
        } else {
            if (item.getChildList().get(0).isChecked()) {
                helper.setChecked(R.id.option_A, true);
            }

            if (item.getChildList().get(1).isChecked()) {
                helper.setChecked(R.id.option_B, true);
            }

            if (item.getChildList().get(2).isChecked()) {
                helper.setChecked(R.id.option_C, true);
            }

            if (item.getChildList().get(3).isChecked()) {
                helper.setChecked(R.id.option_D, true);
            }
            if (item.getChildList().get(4).isChecked()) {
                helper.setChecked(R.id.option_E, true);
            }
        }

        helper.setText(R.id.question_title, item.getFieldName());
        helper.setText(R.id.option_A, item.getChildList().get(0).getDisplayName());
        helper.setText(R.id.option_B, item.getChildList().get(1).getDisplayName());
        helper.setText(R.id.option_C, item.getChildList().get(2).getDisplayName());
        helper.setText(R.id.option_D, item.getChildList().get(3).getDisplayName());
        helper.setText(R.id.option_E, item.getChildList().get(4).getDisplayName());

        helper.addOnClickListener(R.id.option_A);
        helper.addOnClickListener(R.id.option_B);
        helper.addOnClickListener(R.id.option_C);
        helper.addOnClickListener(R.id.option_D);
        helper.addOnClickListener(R.id.option_E);
    }

}

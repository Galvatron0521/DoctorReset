package com.shenkangyun.doctor.BeanFolder;

/**
 * Created by Administrator on 2018/11/22.
 */

public class ResultBean {

    /**
     * message : true
     * data : 成功!
     */

    private boolean message;
    private String data;

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

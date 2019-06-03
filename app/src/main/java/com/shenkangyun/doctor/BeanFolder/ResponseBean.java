package com.shenkangyun.doctor.BeanFolder;

/**
 * Created by Administrator on 2018/8/11.
 */

public class ResponseBean {

    /**
     * status : 0
     * data : {"data":"添加成功!"}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * data : 添加成功!
         */

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

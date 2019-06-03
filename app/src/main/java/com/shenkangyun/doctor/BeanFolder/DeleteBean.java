package com.shenkangyun.doctor.BeanFolder;

/**
 * Created by Administrator on 2018/8/2.
 */

public class DeleteBean {

    /**
     * status : 0
     * data : {"data":"删除成功!"}
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
         * data : 删除成功!
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

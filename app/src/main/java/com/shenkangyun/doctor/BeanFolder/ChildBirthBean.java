package com.shenkangyun.doctor.BeanFolder;

import java.util.List;

/**
 * Created by Administrator on 2018/12/6.
 */

public class ChildBirthBean {

    /**
     * data : {"pageCount":10,"totalCount":2,"pageNo":0,"list":[{"id":114,"patientID":39,"childWeeks":62,"tire":1,"chan":1,"bearing":"枕先露","fullterm":1,"preterm":0,"abortion":0,"exist":0,"birth":1,"complication":"6","height":"15","weight":"3","tfmilk":0,"femilk":0,"firstmilk":1,"mood":0,"createUser":null,"createTime":1547452144000,"updateTime":1547452144000,"delFlag":0,"delTime":null,"remark":null,"birthDate":"2001-01-14","pushFlag":0},{"id":102,"patientID":39,"childWeeks":280,"tire":1,"chan":1,"bearing":"枕先露","fullterm":0,"preterm":0,"abortion":0,"exist":0,"birth":2,"complication":"null","height":"170","weight":"70","tfmilk":2,"femilk":1,"firstmilk":1,"mood":0,"createUser":null,"createTime":1533197835000,"updateTime":1545024141000,"delFlag":0,"delTime":null,"remark":null,"birthDate":"2017-12-12","pushFlag":0}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageCount : 10
         * totalCount : 2
         * pageNo : 0
         * list : [{"id":114,"patientID":39,"childWeeks":62,"tire":1,"chan":1,"bearing":"枕先露","fullterm":1,"preterm":0,"abortion":0,"exist":0,"birth":1,"complication":"6","height":"15","weight":"3","tfmilk":0,"femilk":0,"firstmilk":1,"mood":0,"createUser":null,"createTime":1547452144000,"updateTime":1547452144000,"delFlag":0,"delTime":null,"remark":null,"birthDate":"2001-01-14","pushFlag":0},{"id":102,"patientID":39,"childWeeks":280,"tire":1,"chan":1,"bearing":"枕先露","fullterm":0,"preterm":0,"abortion":0,"exist":0,"birth":2,"complication":"null","height":"170","weight":"70","tfmilk":2,"femilk":1,"firstmilk":1,"mood":0,"createUser":null,"createTime":1533197835000,"updateTime":1545024141000,"delFlag":0,"delTime":null,"remark":null,"birthDate":"2017-12-12","pushFlag":0}]
         */

        private int pageCount;
        private int totalCount;
        private int pageNo;
        private List<ListBean> list;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 114
             * patientID : 39
             * childWeeks : 62
             * tire : 1
             * chan : 1
             * bearing : 枕先露
             * fullterm : 1
             * preterm : 0
             * abortion : 0
             * exist : 0
             * birth : 1
             * complication : 6
             * height : 15
             * weight : 3
             * tfmilk : 0
             * femilk : 0
             * firstmilk : 1
             * mood : 0
             * createUser : null
             * createTime : 1547452144000
             * updateTime : 1547452144000
             * delFlag : 0
             * delTime : null
             * remark : null
             * birthDate : 2001-01-14
             * pushFlag : 0
             */

            private int id;
            private int patientID;
            private int childWeeks;
            private int tire;
            private int chan;
            private String bearing;
            private int fullterm;
            private int preterm;
            private int abortion;
            private int exist;
            private int birth;
            private String complication;
            private String height;
            private String weight;
            private int tfmilk;
            private int femilk;
            private int firstmilk;
            private int mood;
            private Object createUser;
            private long createTime;
            private long updateTime;
            private int delFlag;
            private Object delTime;
            private Object remark;
            private String birthDate;
            private int pushFlag;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPatientID() {
                return patientID;
            }

            public void setPatientID(int patientID) {
                this.patientID = patientID;
            }

            public int getChildWeeks() {
                return childWeeks;
            }

            public void setChildWeeks(int childWeeks) {
                this.childWeeks = childWeeks;
            }

            public int getTire() {
                return tire;
            }

            public void setTire(int tire) {
                this.tire = tire;
            }

            public int getChan() {
                return chan;
            }

            public void setChan(int chan) {
                this.chan = chan;
            }

            public String getBearing() {
                return bearing;
            }

            public void setBearing(String bearing) {
                this.bearing = bearing;
            }

            public int getFullterm() {
                return fullterm;
            }

            public void setFullterm(int fullterm) {
                this.fullterm = fullterm;
            }

            public int getPreterm() {
                return preterm;
            }

            public void setPreterm(int preterm) {
                this.preterm = preterm;
            }

            public int getAbortion() {
                return abortion;
            }

            public void setAbortion(int abortion) {
                this.abortion = abortion;
            }

            public int getExist() {
                return exist;
            }

            public void setExist(int exist) {
                this.exist = exist;
            }

            public int getBirth() {
                return birth;
            }

            public void setBirth(int birth) {
                this.birth = birth;
            }

            public String getComplication() {
                return complication;
            }

            public void setComplication(String complication) {
                this.complication = complication;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }

            public int getTfmilk() {
                return tfmilk;
            }

            public void setTfmilk(int tfmilk) {
                this.tfmilk = tfmilk;
            }

            public int getFemilk() {
                return femilk;
            }

            public void setFemilk(int femilk) {
                this.femilk = femilk;
            }

            public int getFirstmilk() {
                return firstmilk;
            }

            public void setFirstmilk(int firstmilk) {
                this.firstmilk = firstmilk;
            }

            public int getMood() {
                return mood;
            }

            public void setMood(int mood) {
                this.mood = mood;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public int getDelFlag() {
                return delFlag;
            }

            public void setDelFlag(int delFlag) {
                this.delFlag = delFlag;
            }

            public Object getDelTime() {
                return delTime;
            }

            public void setDelTime(Object delTime) {
                this.delTime = delTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getBirthDate() {
                return birthDate;
            }

            public void setBirthDate(String birthDate) {
                this.birthDate = birthDate;
            }

            public int getPushFlag() {
                return pushFlag;
            }

            public void setPushFlag(int pushFlag) {
                this.pushFlag = pushFlag;
            }
        }
    }
}

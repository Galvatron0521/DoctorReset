package com.shenkangyun.doctor.BeanFolder;

import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */

public class PatientBean {

    /**
     * data : {"pageCount":8,"totalCount":10,"pageNo":0,"list":[{"id":39,"num":"PDJH180119","hospitalID":0,"loginName":"","name":"丁建红","sex":"女","age":30,"national":"","brithday":"1988-04-06","mobile":"12164385700","password":"e10adc3949ba59abbe56e057f20f883e","degree":1,"idCard":"342422198804064536","provinceID":"310000","cityID":"310100","regionID":"","address":"","postalCode":"","diseasesID":"1","profession":2,"husbandAge":30,"husbandProfession":1,"childWeeks":60,"complication":"无","height":"165","weight":"65","createUser":"","createTime":1516338074000,"updateTime":1530720000000,"delFlag":0,"delTime":"","remark":""},{"id":112,"num":"","hospitalID":"","loginName":"张保","name":"张保","sex":"","age":22,"national":"","brithday":"1994-09-07","mobile":"15165052583","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"37091119605212819","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":1,"husbandAge":23,"husbandProfession":5,"childWeeks":40,"complication":"2","height":"136","weight":"120","createUser":"","createTime":1536301373000,"updateTime":1536301411000,"delFlag":0,"delTime":"","remark":""},{"id":116,"num":"","hospitalID":"","loginName":"马健","name":"测试","sex":"","age":28,"national":"","brithday":"1991-11-12","mobile":"18854889200","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"370911199102076819","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":141,"complication":"5","height":"175","weight":"60","createUser":"","createTime":1541992757000,"updateTime":1541994747000,"delFlag":0,"delTime":"","remark":""},{"id":117,"num":"","hospitalID":"","loginName":"齐晓凤","name":"齐晓凤","sex":"","age":28,"national":"","brithday":"1990-05-26","mobile":"13127572325","password":"1fae74216f4b666944d10edb0ab7e866","degree":4,"idCard":"371122199005234758","provinceID":"310000","cityID":"310400","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":264,"complication":"5","height":"164","weight":"58","createUser":"","createTime":1541992925000,"updateTime":1541993973000,"delFlag":0,"delTime":"","remark":""},{"id":118,"num":"","hospitalID":"","loginName":"than","name":"","sex":"女","age":"","national":"","brithday":"","mobile":"13154889207","password":"e10adc3949ba59abbe56e057f20f883e","degree":"","idCard":"370911199605301234","provinceID":"","cityID":"","regionID":"","address":"","postalCode":"","diseasesID":"","profession":"","husbandAge":"","husbandProfession":"","childWeeks":"","complication":"","height":"","weight":"","createUser":"","createTime":1541993780000,"updateTime":"","delFlag":0,"delTime":"","remark":""},{"id":119,"num":"","hospitalID":"","loginName":"123","name":"老师","sex":"","age":27,"national":"","brithday":"1990-11-14","mobile":"13818966139","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"123456789","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":141,"complication":"0","height":"170","weight":"60","createUser":"","createTime":1542172456000,"updateTime":1542176548000,"delFlag":0,"delTime":"","remark":""},{"id":120,"num":"","hospitalID":"","loginName":"杨","name":"杨","sex":"","age":12,"national":"","brithday":"2018-11-14","mobile":"15753312367","password":"e10adc3949ba59abbe56e057f20f883e","degree":1,"idCard":"370911199108547711","provinceID":"","cityID":"","regionID":"","address":"","postalCode":"","diseasesID":"","profession":1,"husbandAge":12,"husbandProfession":0,"childWeeks":86,"complication":"3","height":"170","weight":"60","createUser":"","createTime":1542173293000,"updateTime":1542173366000,"delFlag":0,"delTime":"","remark":""},{"id":121,"num":"","hospitalID":"","loginName":"doctor","name":"测试","sex":"","age":27,"national":"","brithday":"1991-11-14","mobile":"18854889214","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"370911199102076815","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":0,"childWeeks":151,"complication":"0","height":"170","weight":"60","createUser":"","createTime":1542173561000,"updateTime":1542173734000,"delFlag":0,"delTime":"","remark":""}]}
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
         * pageCount : 8
         * totalCount : 10
         * pageNo : 0
         * list : [{"id":39,"num":"PDJH180119","hospitalID":0,"loginName":"","name":"丁建红","sex":"女","age":30,"national":"","brithday":"1988-04-06","mobile":"12164385700","password":"e10adc3949ba59abbe56e057f20f883e","degree":1,"idCard":"342422198804064536","provinceID":"310000","cityID":"310100","regionID":"","address":"","postalCode":"","diseasesID":"1","profession":2,"husbandAge":30,"husbandProfession":1,"childWeeks":60,"complication":"无","height":"165","weight":"65","createUser":"","createTime":1516338074000,"updateTime":1530720000000,"delFlag":0,"delTime":"","remark":""},{"id":112,"num":"","hospitalID":"","loginName":"张保","name":"张保","sex":"","age":22,"national":"","brithday":"1994-09-07","mobile":"15165052583","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"37091119605212819","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":1,"husbandAge":23,"husbandProfession":5,"childWeeks":40,"complication":"2","height":"136","weight":"120","createUser":"","createTime":1536301373000,"updateTime":1536301411000,"delFlag":0,"delTime":"","remark":""},{"id":116,"num":"","hospitalID":"","loginName":"马健","name":"测试","sex":"","age":28,"national":"","brithday":"1991-11-12","mobile":"18854889200","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"370911199102076819","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":141,"complication":"5","height":"175","weight":"60","createUser":"","createTime":1541992757000,"updateTime":1541994747000,"delFlag":0,"delTime":"","remark":""},{"id":117,"num":"","hospitalID":"","loginName":"齐晓凤","name":"齐晓凤","sex":"","age":28,"national":"","brithday":"1990-05-26","mobile":"13127572325","password":"1fae74216f4b666944d10edb0ab7e866","degree":4,"idCard":"371122199005234758","provinceID":"310000","cityID":"310400","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":264,"complication":"5","height":"164","weight":"58","createUser":"","createTime":1541992925000,"updateTime":1541993973000,"delFlag":0,"delTime":"","remark":""},{"id":118,"num":"","hospitalID":"","loginName":"than","name":"","sex":"女","age":"","national":"","brithday":"","mobile":"13154889207","password":"e10adc3949ba59abbe56e057f20f883e","degree":"","idCard":"370911199605301234","provinceID":"","cityID":"","regionID":"","address":"","postalCode":"","diseasesID":"","profession":"","husbandAge":"","husbandProfession":"","childWeeks":"","complication":"","height":"","weight":"","createUser":"","createTime":1541993780000,"updateTime":"","delFlag":0,"delTime":"","remark":""},{"id":119,"num":"","hospitalID":"","loginName":"123","name":"老师","sex":"","age":27,"national":"","brithday":"1990-11-14","mobile":"13818966139","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"123456789","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":2,"childWeeks":141,"complication":"0","height":"170","weight":"60","createUser":"","createTime":1542172456000,"updateTime":1542176548000,"delFlag":0,"delTime":"","remark":""},{"id":120,"num":"","hospitalID":"","loginName":"杨","name":"杨","sex":"","age":12,"national":"","brithday":"2018-11-14","mobile":"15753312367","password":"e10adc3949ba59abbe56e057f20f883e","degree":1,"idCard":"370911199108547711","provinceID":"","cityID":"","regionID":"","address":"","postalCode":"","diseasesID":"","profession":1,"husbandAge":12,"husbandProfession":0,"childWeeks":86,"complication":"3","height":"170","weight":"60","createUser":"","createTime":1542173293000,"updateTime":1542173366000,"delFlag":0,"delTime":"","remark":""},{"id":121,"num":"","hospitalID":"","loginName":"doctor","name":"测试","sex":"","age":27,"national":"","brithday":"1991-11-14","mobile":"18854889214","password":"e10adc3949ba59abbe56e057f20f883e","degree":4,"idCard":"370911199102076815","provinceID":"110000","cityID":"110100","regionID":"","address":"","postalCode":"","diseasesID":"","profession":2,"husbandAge":28,"husbandProfession":0,"childWeeks":151,"complication":"0","height":"170","weight":"60","createUser":"","createTime":1542173561000,"updateTime":1542173734000,"delFlag":0,"delTime":"","remark":""}]
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
             * id : 39
             * num : PDJH180119
             * hospitalID : 0
             * loginName :
             * name : 丁建红
             * sex : 女
             * age : 30
             * national :
             * brithday : 1988-04-06
             * mobile : 12164385700
             * password : e10adc3949ba59abbe56e057f20f883e
             * degree : 1
             * idCard : 342422198804064536
             * provinceID : 310000
             * cityID : 310100
             * regionID :
             * address :
             * postalCode :
             * diseasesID : 1
             * profession : 2
             * husbandAge : 30
             * husbandProfession : 1
             * childWeeks : 60
             * complication : 无
             * height : 165
             * weight : 65
             * createUser :
             * createTime : 1516338074000
             * updateTime : 1530720000000
             * delFlag : 0
             * delTime :
             * remark :
             */

            private int id;
            private String num;
            private int hospitalID;
            private String loginName;
            private String name;
            private String sex;
            private int age;
            private String national;
            private String brithday;
            private String mobile;
            private String password;
            private int degree;
            private String idCard;
            private String provinceID;
            private String cityID;
            private String regionID;
            private String address;
            private String postalCode;
            private String diseasesID;
            private int profession;
            private int husbandAge;
            private int husbandProfession;
            private int childWeeks;
            private String complication;
            private String height;
            private String weight;
            private String createUser;
            private long createTime;
            private long updateTime;
            private int delFlag;
            private String delTime;
            private String remark;
            private int patientNum;

            public int getPatientNum() {
                return patientNum;
            }

            public void setPatientNum(int patientNum) {
                this.patientNum = patientNum;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getHospitalID() {
                return hospitalID;
            }

            public void setHospitalID(int hospitalID) {
                this.hospitalID = hospitalID;
            }

            public String getLoginName() {
                return loginName;
            }

            public void setLoginName(String loginName) {
                this.loginName = loginName;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getNational() {
                return national;
            }

            public void setNational(String national) {
                this.national = national;
            }

            public String getBrithday() {
                return brithday;
            }

            public void setBrithday(String brithday) {
                this.brithday = brithday;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public int getDegree() {
                return degree;
            }

            public void setDegree(int degree) {
                this.degree = degree;
            }

            public String getIdCard() {
                return idCard;
            }

            public void setIdCard(String idCard) {
                this.idCard = idCard;
            }

            public String getProvinceID() {
                return provinceID;
            }

            public void setProvinceID(String provinceID) {
                this.provinceID = provinceID;
            }

            public String getCityID() {
                return cityID;
            }

            public void setCityID(String cityID) {
                this.cityID = cityID;
            }

            public String getRegionID() {
                return regionID;
            }

            public void setRegionID(String regionID) {
                this.regionID = regionID;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

            public String getDiseasesID() {
                return diseasesID;
            }

            public void setDiseasesID(String diseasesID) {
                this.diseasesID = diseasesID;
            }

            public int getProfession() {
                return profession;
            }

            public void setProfession(int profession) {
                this.profession = profession;
            }

            public int getHusbandAge() {
                return husbandAge;
            }

            public void setHusbandAge(int husbandAge) {
                this.husbandAge = husbandAge;
            }

            public int getHusbandProfession() {
                return husbandProfession;
            }

            public void setHusbandProfession(int husbandProfession) {
                this.husbandProfession = husbandProfession;
            }

            public int getChildWeeks() {
                return childWeeks;
            }

            public void setChildWeeks(int childWeeks) {
                this.childWeeks = childWeeks;
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

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
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

            public String getDelTime() {
                return delTime;
            }

            public void setDelTime(String delTime) {
                this.delTime = delTime;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}

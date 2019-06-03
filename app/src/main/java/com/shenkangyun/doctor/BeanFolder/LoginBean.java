package com.shenkangyun.doctor.BeanFolder;

/**
 * Created by Administrator on 2018/7/5.
 */

public class LoginBean {

    /**
     * status : 0
     * data : {"patient":{"UserID":1,"RoleID":9999,"HospitalID":0,"LoginName":"18854889207","Name":"admin","Sex":1,"National":"1","Brithday":"2016-10-11","Mobile":"18854889207","degree":0,"IDCard":"","PhotoID":0,"PhotoUrl":"","Address":"","Password":"e10adc3949ba59abbe56e057f20f883e","FirstLoginTime":1474273862000,"CreateTime":1472716156000,"UpdateTime":1514876656000,"DelFlag":0,"DelTime":null,"Remark":"333"},"appType":"2"}
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
         * patient : {"UserID":1,"RoleID":9999,"HospitalID":0,"LoginName":"18854889207","Name":"admin","Sex":1,"National":"1","Brithday":"2016-10-11","Mobile":"18854889207","degree":0,"IDCard":"","PhotoID":0,"PhotoUrl":"","Address":"","Password":"e10adc3949ba59abbe56e057f20f883e","FirstLoginTime":1474273862000,"CreateTime":1472716156000,"UpdateTime":1514876656000,"DelFlag":0,"DelTime":null,"Remark":"333"}
         * appType : 2
         */

        private PatientBean patient;
        private String appType;

        public PatientBean getPatient() {
            return patient;
        }

        public void setPatient(PatientBean patient) {
            this.patient = patient;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public static class PatientBean {
            /**
             * UserID : 1
             * RoleID : 9999
             * HospitalID : 0
             * LoginName : 18854889207
             * Name : admin
             * Sex : 1
             * National : 1
             * Brithday : 2016-10-11
             * Mobile : 18854889207
             * degree : 0
             * IDCard :
             * PhotoID : 0
             * PhotoUrl :
             * Address :
             * Password : e10adc3949ba59abbe56e057f20f883e
             * FirstLoginTime : 1474273862000
             * CreateTime : 1472716156000
             * UpdateTime : 1514876656000
             * DelFlag : 0
             * DelTime : null
             * Remark : 333
             */

            private int UserID;
            private int RoleID;
            private int HospitalID;
            private String LoginName;
            private String Name;
            private int Sex;
            private String National;
            private String Brithday;
            private String Mobile;
            private int degree;
            private String IDCard;
            private int PhotoID;
            private String PhotoUrl;
            private String Address;
            private String Password;
            private long FirstLoginTime;
            private long CreateTime;
            private long UpdateTime;
            private int DelFlag;
            private Object DelTime;
            private String Remark;

            public int getUserID() {
                return UserID;
            }

            public void setUserID(int UserID) {
                this.UserID = UserID;
            }

            public int getRoleID() {
                return RoleID;
            }

            public void setRoleID(int RoleID) {
                this.RoleID = RoleID;
            }

            public int getHospitalID() {
                return HospitalID;
            }

            public void setHospitalID(int HospitalID) {
                this.HospitalID = HospitalID;
            }

            public String getLoginName() {
                return LoginName;
            }

            public void setLoginName(String LoginName) {
                this.LoginName = LoginName;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getSex() {
                return Sex;
            }

            public void setSex(int Sex) {
                this.Sex = Sex;
            }

            public String getNational() {
                return National;
            }

            public void setNational(String National) {
                this.National = National;
            }

            public String getBrithday() {
                return Brithday;
            }

            public void setBrithday(String Brithday) {
                this.Brithday = Brithday;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public int getDegree() {
                return degree;
            }

            public void setDegree(int degree) {
                this.degree = degree;
            }

            public String getIDCard() {
                return IDCard;
            }

            public void setIDCard(String IDCard) {
                this.IDCard = IDCard;
            }

            public int getPhotoID() {
                return PhotoID;
            }

            public void setPhotoID(int PhotoID) {
                this.PhotoID = PhotoID;
            }

            public String getPhotoUrl() {
                return PhotoUrl;
            }

            public void setPhotoUrl(String PhotoUrl) {
                this.PhotoUrl = PhotoUrl;
            }

            public String getAddress() {
                return Address;
            }

            public void setAddress(String Address) {
                this.Address = Address;
            }

            public String getPassword() {
                return Password;
            }

            public void setPassword(String Password) {
                this.Password = Password;
            }

            public long getFirstLoginTime() {
                return FirstLoginTime;
            }

            public void setFirstLoginTime(long FirstLoginTime) {
                this.FirstLoginTime = FirstLoginTime;
            }

            public long getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(long CreateTime) {
                this.CreateTime = CreateTime;
            }

            public long getUpdateTime() {
                return UpdateTime;
            }

            public void setUpdateTime(long UpdateTime) {
                this.UpdateTime = UpdateTime;
            }

            public int getDelFlag() {
                return DelFlag;
            }

            public void setDelFlag(int DelFlag) {
                this.DelFlag = DelFlag;
            }

            public Object getDelTime() {
                return DelTime;
            }

            public void setDelTime(Object DelTime) {
                this.DelTime = DelTime;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }
        }
    }
}

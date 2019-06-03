package com.shenkangyun.doctor.BeanFolder;

import java.util.List;

/**
 * Created by Administrator on 2018/12/12.
 */

public class LactationBean {

    /**
     * data : {"pageCount":8,"totalCount":5,"submenuList":[{"moduleID":238,"crfType":1,"moduleType":1,"moduleName":"产后缺乳治疗调查表","moduleCode":"SP020124","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:11:26"},{"moduleID":266,"crfType":1,"moduleType":1,"moduleName":"产后缺乳治疗医生接诊记录","moduleCode":"SP020151","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-08-14 14:39:07"},{"moduleID":248,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-肝郁气滞型）","moduleCode":"SP020134","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:34"},{"moduleID":249,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-气血虚弱型）","moduleCode":"SP020135","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:53"},{"moduleID":250,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-痰湿阻滞型）","moduleCode":"SP020136","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:16:11"}],"currentPage":0,"patientID":39,"appType":"2"}
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
         * totalCount : 5
         * submenuList : [{"moduleID":238,"crfType":1,"moduleType":1,"moduleName":"产后缺乳治疗调查表","moduleCode":"SP020124","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-04-29 16:11:26"},{"moduleID":266,"crfType":1,"moduleType":1,"moduleName":"产后缺乳治疗医生接诊记录","moduleCode":"SP020151","moduleUrl":"/form/patientsfield2.html","CreateTime":"2018-08-14 14:39:07"},{"moduleID":248,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-肝郁气滞型）","moduleCode":"SP020134","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:34"},{"moduleID":249,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-气血虚弱型）","moduleCode":"SP020135","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:15:53"},{"moduleID":250,"crfType":1,"moduleType":1,"moduleName":"中医症候积分量表（产后缺乳-痰湿阻滞型）","moduleCode":"SP020136","moduleUrl":"/form/scaleRecordList.html","CreateTime":"2018-04-29 16:16:11"}]
         * currentPage : 0
         * patientID : 39
         * appType : 2
         */

        private int pageCount;
        private int totalCount;
        private int currentPage;
        private int patientID;
        private String appType;
        private List<SubmenuListBean> submenuList;

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

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPatientID() {
            return patientID;
        }

        public void setPatientID(int patientID) {
            this.patientID = patientID;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public List<SubmenuListBean> getSubmenuList() {
            return submenuList;
        }

        public void setSubmenuList(List<SubmenuListBean> submenuList) {
            this.submenuList = submenuList;
        }

        public static class SubmenuListBean {
            /**
             * moduleID : 238
             * crfType : 1
             * moduleType : 1
             * moduleName : 产后缺乳治疗调查表
             * moduleCode : SP020124
             * moduleUrl : /form/patientsfield2.html
             * CreateTime : 2018-04-29 16:11:26
             */

            private int moduleID;
            private int crfType;
            private int moduleType;
            private String moduleName;
            private String moduleCode;
            private String moduleUrl;
            private String CreateTime;
            private int moduleNum;

            public int getModuleNum() {
                return moduleNum;
            }

            public void setModuleNum(int moduleNum) {
                this.moduleNum = moduleNum;
            }

            public int getModuleID() {
                return moduleID;
            }

            public void setModuleID(int moduleID) {
                this.moduleID = moduleID;
            }

            public int getCrfType() {
                return crfType;
            }

            public void setCrfType(int crfType) {
                this.crfType = crfType;
            }

            public int getModuleType() {
                return moduleType;
            }

            public void setModuleType(int moduleType) {
                this.moduleType = moduleType;
            }

            public String getModuleName() {
                return moduleName;
            }

            public void setModuleName(String moduleName) {
                this.moduleName = moduleName;
            }

            public String getModuleCode() {
                return moduleCode;
            }

            public void setModuleCode(String moduleCode) {
                this.moduleCode = moduleCode;
            }

            public String getModuleUrl() {
                return moduleUrl;
            }

            public void setModuleUrl(String moduleUrl) {
                this.moduleUrl = moduleUrl;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }
        }
    }
}

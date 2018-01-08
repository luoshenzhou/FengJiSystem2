package com.haozhi.machinestatu.fengjisystem.bean;

import java.util.List;

/**
 * Created by LSZ on 2018/1/8.
 */
public class TitleName {

    public TitleName(String groupTitleName, List<GroupDataBean> groupData) {
        this.groupTitleName = groupTitleName;
        this.groupData = groupData;
    }

    /**
     * groupTitleName : 风机系统分组标题
     * groupData : [{"dataTitle":"风机系统分组子标题1","data":1},{"dataTitle":"风机系统分组子标题2","data":1}]
     */

    private String groupTitleName;
    private List<GroupDataBean> groupData;

    public String getGroupTitleName() {
        return groupTitleName;
    }

    public void setGroupTitleName(String groupTitleName) {
        this.groupTitleName = groupTitleName;
    }

    public List<GroupDataBean> getGroupData() {
        return groupData;
    }

    public void setGroupData(List<GroupDataBean> groupData) {
        this.groupData = groupData;
    }

    public static class GroupDataBean {

        public GroupDataBean(String dataTitle, int data) {
            this.dataTitle = dataTitle;
            this.data = data;
        }

        /**
         * dataTitle : 风机系统分组子标题1
         * data : 1
         */



        private String dataTitle;
        private int data;

        public String getDataTitle() {
            return dataTitle;
        }

        public void setDataTitle(String dataTitle) {
            this.dataTitle = dataTitle;
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }
}

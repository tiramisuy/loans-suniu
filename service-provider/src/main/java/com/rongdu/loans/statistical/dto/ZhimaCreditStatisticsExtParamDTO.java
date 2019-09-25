package com.rongdu.loans.statistical.dto;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/8/28.
 */
public class ZhimaCreditStatisticsExtParamDTO implements Serializable {

    private String fileCharset = "UTF-8";
    /**
     * 文件数据记录条数
     */
    private String records;
    /**
     * 主键列使用反馈字段进行组合，建议使用字段order_no
     和其它字段的组合确保主键稳定，对于一个order_no 只
     会有一条数据的情况，直接使用order_no 作为主键列
     */
    private String primaryKeyColumns;
    /**
     * 拓展参数，{“extparam1”:”value1”}
     */
    private String bizExtParams;
    /**
     * 反馈的json 格式的文件，其中{"records": 是每个文件
     的固定开头。
     */
    private byte[] file;
    /**
     * 单条数据的数据列，多个列以逗号隔开（“biz_date,l
     inked_merchant_id，user_credentials_type,user_cr
     edentials_no,user_name,order_no,scene_type,scene
     _desc,scene_status,create_amt,installment_due_da
     te,overdue_amt,gmt_ovd_date,rectify_flag,memo”）
     */
    private String columns = "biz_date,linked_merchant_id，user_credentials_type,user_credentials_no,user_name," +
            "order_no,scene_type,scene_desc,scene_status,create_amt,installment_due_date,overdue_amt,gmt_ovd_date,rectify_flag,memo";

    public String getFileCharset() {
        return fileCharset;
    }

    public void setFileCharset(String fileCharset) {
        this.fileCharset = fileCharset;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public String getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    public void setPrimaryKeyColumns(String primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    public String getBizExtParams() {
        return bizExtParams;
    }

    public void setBizExtParams(String bizExtParams) {
        this.bizExtParams = bizExtParams;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }
}

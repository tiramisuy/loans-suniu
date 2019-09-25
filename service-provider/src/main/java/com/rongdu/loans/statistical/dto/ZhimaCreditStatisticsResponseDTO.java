package com.rongdu.loans.statistical.dto;

import com.rongdu.common.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by zhangxiaolong on 2017/9/12.
 */
public class ZhimaCreditStatisticsResponseDTO implements Serializable {

    private String flowNo;

    private String resultCode;

    private Result resultData;

    private String resultDesc;

    public class Result implements Serializable{
        private String success;
        private String taskId;
        private String errorMessage;
        private String errorCode;

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }
    }

    public boolean success(){
        if (StringUtils.isBlank(resultCode)){
            return false;
        }
        if (StringUtils.equals(resultCode,"BQS000")){
            return true;
        }
        return false;
    }

    public String getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(String flowNo) {
        this.flowNo = flowNo;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public Result getResultData() {
        return resultData;
    }

    public void setResultData(Result resultData) {
        this.resultData = resultData;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }



}

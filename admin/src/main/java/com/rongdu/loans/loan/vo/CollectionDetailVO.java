package com.rongdu.loans.loan.vo;

import com.rongdu.loans.basic.vo.FileInfoVO;
import com.rongdu.loans.enums.UrgentRecallResult;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaolong on 2017/9/28.
 */
public class CollectionDetailVO implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;

    private List<RepayDetailListVO> detailList;
    private List<ContactToCollectionVO> contactList;
    private List<UrgentRecallResultVO> resultList;
    private List<CollectionRecordVO> recordList;
    private List<Map<String, Object>> contactConnect;
    private List<FileInfoVO> fileInfoList;
    private RepayWarnVO warnInfo;
    
    private boolean showContact;	//是否显示通讯录
	


	public boolean isShowContact() {
		return showContact;
	}

	public void setShowContact(boolean showContact) {
		this.showContact = showContact;
	}

	public List<Map<String, Object>> getContactConnect() {
		return contactConnect;
	}

	public void setContactConnect(List<Map<String, Object>> contactConnect) {
		this.contactConnect = contactConnect;
	}

	public List<RepayDetailListVO> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<RepayDetailListVO> detailList) {
        this.detailList = detailList;
    }

    public List<ContactToCollectionVO> getContactList() {
        return contactList;
    }

    public void setContactList(List<ContactToCollectionVO> contactList) {
        this.contactList = contactList;
    }

    public List<UrgentRecallResultVO> getResultList() {
        return resultList;
    }

    public void setResultList(List<UrgentRecallResultVO> resultList) {
        this.resultList = resultList;
    }

	public List<CollectionRecordVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<CollectionRecordVO> recordList) {
		this.recordList = recordList;
	}

	public List<FileInfoVO> getFileInfoList() {
		return fileInfoList;
	}

	public void setFileInfoList(List<FileInfoVO> fileInfoList) {
		this.fileInfoList = fileInfoList;
	}

	public RepayWarnVO getWarnInfo() {
		return warnInfo;
	}

	public void setWarnInfo(RepayWarnVO warnInfo) {
		this.warnInfo = warnInfo;
	}

    
    
    
}

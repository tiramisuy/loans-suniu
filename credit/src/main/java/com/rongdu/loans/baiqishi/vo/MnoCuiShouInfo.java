package com.rongdu.loans.baiqishi.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 催收指标
 * 
 * @author liuzhuang
 * @version 2017-07-10
 */
public class MnoCuiShouInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3298335684115891517L;
	private List<MnoCuiShouInfoDetail> notSureDunnings;
	private List<MnoCuiShouInfoDetail> dunnings;

	public List<MnoCuiShouInfoDetail> getNotSureDunnings() {
		return notSureDunnings;
	}

	public void setNotSureDunnings(List<MnoCuiShouInfoDetail> notSureDunnings) {
		this.notSureDunnings = notSureDunnings;
	}

	public List<MnoCuiShouInfoDetail> getDunnings() {
		return dunnings;
	}

	public void setDunnings(List<MnoCuiShouInfoDetail> dunnings) {
		this.dunnings = dunnings;
	}

}

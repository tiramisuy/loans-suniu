package com.rongdu.loans.xinyan.vo;

import java.io.Serializable;

public class RadarResultDetailVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String daikou_stddev_amt_succ_sum_day15;// 最近15天代扣成功标准差金额
	private String daikou_stddev_amt_fail_sum_day15;// 最近15天代扣失败标准差金额
	private String daikou_stddev_amt_fail_sum_day30;// 最近30天代扣失败标准差金额
	private String daikou_max_amt_succ_sum_day180;// 最近180天代扣成功最大金额
	private String daikou_max_amt_succ_sum_day90;// 最近90天代扣成功最大金额
	private String daikou_max_amt_succ_sum_day30;// 最近30天代扣成功最大金额
	private String daikou_max_amt_succ_sum_day7;// 最近7天代扣成功最大金额
	private String daikou_min_amt_fail_sum_day360;// 最近360天代扣失败最小金额
	private String daikou_min_amt_succ_sum_day15;// 最近15天代扣成功最小金额
	private String daikou_min_amt_all_sum_day7;// 最近7天代扣全部最小金额
	private String daifu_max_amt_succ_sum_day180;// 最近180天代付成功最大金额
	private String daifu_max_amt_all_sum_day7;// 最近7天代付全部最大金额
	private String daifu_min_amt_all_sum_day7;// 最近7天代付全部最小金额
	private String total_score;// 聚宝袋分
	private String daikou_max_amt_fail_sum_day90;// 最近90天代扣失败最大金额
	private String daikou_max_amt_fail_sum_day30;// 最近30天代扣失败最大金额
	private String daikou_stddev_amt_fail_sum_day90;// 最近90天代扣失败标准差金额
	private String daifu_min_amt_all_sum_day90;// 最近90天代付全部最小金额
	private String daikou_max_amt_all_sum_day30;// 最近30天代扣全部最大金额
	private String daikou_stddev_amt_fail_sum_day360;// 最近360天代扣失败标准差金额
	private String daifu_min_amt_succ_sum_day180;// 最近180天代付成功最小金额
	private String daikou_min_amt_all_sum_day30;// 最近30天代扣全部最小金额
	private String daikou_min_amt_all_sum_day360;// 最近360天代扣全部最小金额
	private String daikou_stddev_amt_all_sum_day15;// 最近15天代扣全部标准差金额
	private String daikou_min_amt_fail_sum_day7;// 最近7天代扣失败最小金额
	private String daikou_max_amt_all_sum_day7;// 最近7天代扣全部最大金额
	private String daikou_min_amt_all_sum_day15;// 最近15天代扣全部最小金额
	private String daikou_stddev_amt_all_sum_day90;// 最近90天代扣全部标准差金额
	private String daikou_max_amt_succ_sum_day360;// 最近360天代扣成功最大金额
	private String daifu_min_amt_succ_sum_day30;// 最近30天代付成功最小金额
	private String dis_alltime_xfjr_succlend_maxdis_cnt3;// 最近3次全部时间消费金融类距离非逾期借贷第一次距今天数
	private String dis_alltime_cdqpro_succpay_maxdis_cnt10;// 最近10次全部时间超短期现金贷距离非逾期还款第一次距今天数

	public String getDaikou_stddev_amt_succ_sum_day15() {
		return daikou_stddev_amt_succ_sum_day15;
	}

	public void setDaikou_stddev_amt_succ_sum_day15(String daikou_stddev_amt_succ_sum_day15) {
		this.daikou_stddev_amt_succ_sum_day15 = daikou_stddev_amt_succ_sum_day15;
	}

	public String getDaikou_stddev_amt_fail_sum_day15() {
		return daikou_stddev_amt_fail_sum_day15;
	}

	public void setDaikou_stddev_amt_fail_sum_day15(String daikou_stddev_amt_fail_sum_day15) {
		this.daikou_stddev_amt_fail_sum_day15 = daikou_stddev_amt_fail_sum_day15;
	}

	public String getDaikou_stddev_amt_fail_sum_day30() {
		return daikou_stddev_amt_fail_sum_day30;
	}

	public void setDaikou_stddev_amt_fail_sum_day30(String daikou_stddev_amt_fail_sum_day30) {
		this.daikou_stddev_amt_fail_sum_day30 = daikou_stddev_amt_fail_sum_day30;
	}

	public String getDaikou_max_amt_succ_sum_day180() {
		return daikou_max_amt_succ_sum_day180;
	}

	public void setDaikou_max_amt_succ_sum_day180(String daikou_max_amt_succ_sum_day180) {
		this.daikou_max_amt_succ_sum_day180 = daikou_max_amt_succ_sum_day180;
	}

	public String getDaikou_max_amt_succ_sum_day90() {
		return daikou_max_amt_succ_sum_day90;
	}

	public void setDaikou_max_amt_succ_sum_day90(String daikou_max_amt_succ_sum_day90) {
		this.daikou_max_amt_succ_sum_day90 = daikou_max_amt_succ_sum_day90;
	}

	public String getDaikou_max_amt_succ_sum_day30() {
		return daikou_max_amt_succ_sum_day30;
	}

	public void setDaikou_max_amt_succ_sum_day30(String daikou_max_amt_succ_sum_day30) {
		this.daikou_max_amt_succ_sum_day30 = daikou_max_amt_succ_sum_day30;
	}

	public String getDaikou_max_amt_succ_sum_day7() {
		return daikou_max_amt_succ_sum_day7;
	}

	public void setDaikou_max_amt_succ_sum_day7(String daikou_max_amt_succ_sum_day7) {
		this.daikou_max_amt_succ_sum_day7 = daikou_max_amt_succ_sum_day7;
	}

	public String getDaikou_min_amt_fail_sum_day360() {
		return daikou_min_amt_fail_sum_day360;
	}

	public void setDaikou_min_amt_fail_sum_day360(String daikou_min_amt_fail_sum_day360) {
		this.daikou_min_amt_fail_sum_day360 = daikou_min_amt_fail_sum_day360;
	}

	public String getDaikou_min_amt_succ_sum_day15() {
		return daikou_min_amt_succ_sum_day15;
	}

	public void setDaikou_min_amt_succ_sum_day15(String daikou_min_amt_succ_sum_day15) {
		this.daikou_min_amt_succ_sum_day15 = daikou_min_amt_succ_sum_day15;
	}

	public String getDaikou_min_amt_all_sum_day7() {
		return daikou_min_amt_all_sum_day7;
	}

	public void setDaikou_min_amt_all_sum_day7(String daikou_min_amt_all_sum_day7) {
		this.daikou_min_amt_all_sum_day7 = daikou_min_amt_all_sum_day7;
	}

	public String getDaifu_max_amt_succ_sum_day180() {
		return daifu_max_amt_succ_sum_day180;
	}

	public void setDaifu_max_amt_succ_sum_day180(String daifu_max_amt_succ_sum_day180) {
		this.daifu_max_amt_succ_sum_day180 = daifu_max_amt_succ_sum_day180;
	}

	public String getDaifu_max_amt_all_sum_day7() {
		return daifu_max_amt_all_sum_day7;
	}

	public void setDaifu_max_amt_all_sum_day7(String daifu_max_amt_all_sum_day7) {
		this.daifu_max_amt_all_sum_day7 = daifu_max_amt_all_sum_day7;
	}

	public String getDaifu_min_amt_all_sum_day7() {
		return daifu_min_amt_all_sum_day7;
	}

	public void setDaifu_min_amt_all_sum_day7(String daifu_min_amt_all_sum_day7) {
		this.daifu_min_amt_all_sum_day7 = daifu_min_amt_all_sum_day7;
	}

	public String getTotal_score() {
		return total_score;
	}

	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}

	public String getDaikou_max_amt_fail_sum_day90() {
		return daikou_max_amt_fail_sum_day90;
	}

	public void setDaikou_max_amt_fail_sum_day90(String daikou_max_amt_fail_sum_day90) {
		this.daikou_max_amt_fail_sum_day90 = daikou_max_amt_fail_sum_day90;
	}

	public String getDaikou_max_amt_fail_sum_day30() {
		return daikou_max_amt_fail_sum_day30;
	}

	public void setDaikou_max_amt_fail_sum_day30(String daikou_max_amt_fail_sum_day30) {
		this.daikou_max_amt_fail_sum_day30 = daikou_max_amt_fail_sum_day30;
	}

	public String getDaikou_stddev_amt_fail_sum_day90() {
		return daikou_stddev_amt_fail_sum_day90;
	}

	public void setDaikou_stddev_amt_fail_sum_day90(String daikou_stddev_amt_fail_sum_day90) {
		this.daikou_stddev_amt_fail_sum_day90 = daikou_stddev_amt_fail_sum_day90;
	}

	public String getDaifu_min_amt_all_sum_day90() {
		return daifu_min_amt_all_sum_day90;
	}

	public void setDaifu_min_amt_all_sum_day90(String daifu_min_amt_all_sum_day90) {
		this.daifu_min_amt_all_sum_day90 = daifu_min_amt_all_sum_day90;
	}

	public String getDaikou_max_amt_all_sum_day30() {
		return daikou_max_amt_all_sum_day30;
	}

	public void setDaikou_max_amt_all_sum_day30(String daikou_max_amt_all_sum_day30) {
		this.daikou_max_amt_all_sum_day30 = daikou_max_amt_all_sum_day30;
	}

	public String getDaikou_stddev_amt_fail_sum_day360() {
		return daikou_stddev_amt_fail_sum_day360;
	}

	public void setDaikou_stddev_amt_fail_sum_day360(String daikou_stddev_amt_fail_sum_day360) {
		this.daikou_stddev_amt_fail_sum_day360 = daikou_stddev_amt_fail_sum_day360;
	}

	public String getDaifu_min_amt_succ_sum_day180() {
		return daifu_min_amt_succ_sum_day180;
	}

	public void setDaifu_min_amt_succ_sum_day180(String daifu_min_amt_succ_sum_day180) {
		this.daifu_min_amt_succ_sum_day180 = daifu_min_amt_succ_sum_day180;
	}

	public String getDaikou_min_amt_all_sum_day30() {
		return daikou_min_amt_all_sum_day30;
	}

	public void setDaikou_min_amt_all_sum_day30(String daikou_min_amt_all_sum_day30) {
		this.daikou_min_amt_all_sum_day30 = daikou_min_amt_all_sum_day30;
	}

	public String getDaikou_min_amt_all_sum_day360() {
		return daikou_min_amt_all_sum_day360;
	}

	public void setDaikou_min_amt_all_sum_day360(String daikou_min_amt_all_sum_day360) {
		this.daikou_min_amt_all_sum_day360 = daikou_min_amt_all_sum_day360;
	}

	public String getDaikou_stddev_amt_all_sum_day15() {
		return daikou_stddev_amt_all_sum_day15;
	}

	public void setDaikou_stddev_amt_all_sum_day15(String daikou_stddev_amt_all_sum_day15) {
		this.daikou_stddev_amt_all_sum_day15 = daikou_stddev_amt_all_sum_day15;
	}

	public String getDaikou_min_amt_fail_sum_day7() {
		return daikou_min_amt_fail_sum_day7;
	}

	public void setDaikou_min_amt_fail_sum_day7(String daikou_min_amt_fail_sum_day7) {
		this.daikou_min_amt_fail_sum_day7 = daikou_min_amt_fail_sum_day7;
	}

	public String getDaikou_max_amt_all_sum_day7() {
		return daikou_max_amt_all_sum_day7;
	}

	public void setDaikou_max_amt_all_sum_day7(String daikou_max_amt_all_sum_day7) {
		this.daikou_max_amt_all_sum_day7 = daikou_max_amt_all_sum_day7;
	}

	public String getDaikou_min_amt_all_sum_day15() {
		return daikou_min_amt_all_sum_day15;
	}

	public void setDaikou_min_amt_all_sum_day15(String daikou_min_amt_all_sum_day15) {
		this.daikou_min_amt_all_sum_day15 = daikou_min_amt_all_sum_day15;
	}

	public String getDaikou_stddev_amt_all_sum_day90() {
		return daikou_stddev_amt_all_sum_day90;
	}

	public void setDaikou_stddev_amt_all_sum_day90(String daikou_stddev_amt_all_sum_day90) {
		this.daikou_stddev_amt_all_sum_day90 = daikou_stddev_amt_all_sum_day90;
	}

	public String getDaikou_max_amt_succ_sum_day360() {
		return daikou_max_amt_succ_sum_day360;
	}

	public void setDaikou_max_amt_succ_sum_day360(String daikou_max_amt_succ_sum_day360) {
		this.daikou_max_amt_succ_sum_day360 = daikou_max_amt_succ_sum_day360;
	}

	public String getDaifu_min_amt_succ_sum_day30() {
		return daifu_min_amt_succ_sum_day30;
	}

	public void setDaifu_min_amt_succ_sum_day30(String daifu_min_amt_succ_sum_day30) {
		this.daifu_min_amt_succ_sum_day30 = daifu_min_amt_succ_sum_day30;
	}

	public String getDis_alltime_xfjr_succlend_maxdis_cnt3() {
		return dis_alltime_xfjr_succlend_maxdis_cnt3;
	}

	public void setDis_alltime_xfjr_succlend_maxdis_cnt3(String dis_alltime_xfjr_succlend_maxdis_cnt3) {
		this.dis_alltime_xfjr_succlend_maxdis_cnt3 = dis_alltime_xfjr_succlend_maxdis_cnt3;
	}

	public String getDis_alltime_cdqpro_succpay_maxdis_cnt10() {
		return dis_alltime_cdqpro_succpay_maxdis_cnt10;
	}

	public void setDis_alltime_cdqpro_succpay_maxdis_cnt10(String dis_alltime_cdqpro_succpay_maxdis_cnt10) {
		this.dis_alltime_cdqpro_succpay_maxdis_cnt10 = dis_alltime_cdqpro_succpay_maxdis_cnt10;
	}

}

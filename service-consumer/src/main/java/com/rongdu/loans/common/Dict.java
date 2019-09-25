package com.rongdu.loans.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dict {
	public static Map<String, String> gender = new LinkedHashMap<String, String>();// 性别
	public static Map<String, String> haveHouse = new LinkedHashMap<String, String>();// 是否有房
	public static Map<String, String> houseLoan = new LinkedHashMap<String, String>();// 是否有房贷
	public static Map<String, String> haveCar = new LinkedHashMap<String, String>();// 是否有车
	public static Map<String, String> carLoan = new LinkedHashMap<String, String>();// 是否有车贷
	public static Map<String, String> marriage = new LinkedHashMap<String, String>(); // 婚姻
	public static Map<String, String> education = new LinkedHashMap<String, String>(); // 学历
	public static Map<String, String> jobSalary = new LinkedHashMap<String, String>();// 月收入
	public static Map<String, String> jobCompanyType = new LinkedHashMap<String, String>();// 公司类型
	public static Map<String, String> jobCompanyScale = new LinkedHashMap<String, String>();// 公司规模
	public static Map<String, String> jobIndustry = new LinkedHashMap<String, String>();// 工作行业
	public static Map<String, String> jobYears = new LinkedHashMap<String, String>();// 工作

	static {
		// 性别
		gender.put("1", "男");
		gender.put("2", "女");
		// 是否有房
		haveHouse.put("1", "有");
		haveHouse.put("2", "无");
		// 是否有房贷
		houseLoan.put("1", "有");
		houseLoan.put("2", "无");
		// 是否有车
		haveCar.put("1", "有");
		haveCar.put("2", "无");
		// 是否有车贷
		carLoan.put("1", "有");
		carLoan.put("2", "无");
		// 婚姻
		marriage.put("1", "已婚");
		marriage.put("2", "离异");
		marriage.put("3", "未婚");
		// 学历
		education.put("1", "高中或以下");
		education.put("2", "大专");
		education.put("3", "本科");
		education.put("4", "研究生或以上");
		// 月收入
		jobSalary.put("1", "1000以下");
		jobSalary.put("2", "1000-2000");
		jobSalary.put("3", "2000-3000");
		jobSalary.put("4", "3000-5000");
		jobSalary.put("5", "5000-10000");
		jobSalary.put("6", "10000-20000");
		jobSalary.put("7", "20000-30000");
		jobSalary.put("8", "30000-50000");
		jobSalary.put("9", "50000-100000");
		jobSalary.put("10", "100000元以上");
		// 公司类型
		jobCompanyType.put("1", "国家机关 ");
		jobCompanyType.put("2", "事业单位 ");
		jobCompanyType.put("3", "央企及下级单位 ");
		jobCompanyType.put("4", "地方国资委直属企业 ");
		jobCompanyType.put("5", "世界500强 ");
		jobCompanyType.put("6", "外资及合资企业 ");
		jobCompanyType.put("7", "一般上市公司 ");
		jobCompanyType.put("8", "一般民营企业 ");
		jobCompanyType.put("9", "其它 ");
		// 公司规模
		jobCompanyScale.put("1", "10以下 ");
		jobCompanyScale.put("2", "10-50人 ");
		jobCompanyScale.put("3", "50-100人 ");
		jobCompanyScale.put("4", "100-500人 ");
		jobCompanyScale.put("5", "500人以上 ");
		// 工作行业
		jobIndustry.put("1", "制造业");
		jobIndustry.put("2", "IT");
		jobIndustry.put("3", "政府机关");
		jobIndustry.put("4", "媒体/广告");
		jobIndustry.put("5", "零售/批发");
		jobIndustry.put("6", "教育/培训");
		jobIndustry.put("7", "公共事业");
		jobIndustry.put("8", "交通运输业");
		jobIndustry.put("9", "房地产业");
		jobIndustry.put("10", "能源业");
		jobIndustry.put("11", "金融/法律");
		jobIndustry.put("12", "餐饮/旅馆业");
		jobIndustry.put("13", "医疗/卫生/保健");
		jobIndustry.put("14", "建筑工程");
		jobIndustry.put("15", "农业");
		jobIndustry.put("16", "娱乐服务业");
		jobIndustry.put("17", "体育/艺术");
		jobIndustry.put("18", "公益组织");
		jobIndustry.put("19", "其它");
		// 工作年数
		jobYears.put("1", "1年以下");
		jobYears.put("2", "1-3年");
		jobYears.put("3", "3-5年");
		jobYears.put("4", "5年以上");
	}

}

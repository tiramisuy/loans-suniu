package com.rongdu.loans.test;

import com.rongdu.common.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
//		String birthday = "19980827";
//		getAgeByBirthday(birthday);

//		DecimalFormat df = new DecimalFormat("0.00%");
//		try {
//			System.out.println(df.parse("51.26%").doubleValue());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		String regex = "省|市|州|区|县|旗|自治区";
//		String str = "湖北省北京市";
//		System.out.println(str.replaceAll(regex,""));
//		System.out.println(StringUtils.replace(str,regex,""));
//
//		System.out.println(IkanalyzerUtils.getSimilarity("上海市","北京市"));
//		System.out.println(IkanalyzerUtils.getSimilarity("湖北省武汉市","湖北省武穴市"));
//		System.out.println(IkanalyzerUtils.getSimilarity("扬州市","杭州市"));

//		int count =79;
//		//总共电话号码数量
//		int total = 100;
//		double threshold = 0.20D;
//		double rate = 1-(double)count/total;
//		System.out.println(rate);
//		System.out.println(rate>threshold);

		Date month = null;
		month = DateUtils.parseDate("2017-08");
		System.out.println(month.getTime()<System.currentTimeMillis());
		if (month!=null&&month.getTime()<System.currentTimeMillis()){

		}


	}

	public static int getAgeByBirthday(String birthday){
		System.out.println(birthday);
		Date birthdayDate = DateUtils.parseDate(birthday);
		Calendar birthdayCalendar = Calendar.getInstance();
		birthdayCalendar.setTime(birthdayDate);
		Calendar calendar =  Calendar.getInstance();
		int age = calendar.get(Calendar.YEAR)- birthdayCalendar.get(Calendar.YEAR);
		System.out.println(age);
		return age;
	}
}

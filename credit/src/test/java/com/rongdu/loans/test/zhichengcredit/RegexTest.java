package com.rongdu.loans.test.zhichengcredit;

import com.rongdu.common.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static String continuousNumberPattern=null;

	public static void main(String[] args) {
		String str = "13488881235";
		System.out.println(str+"____"+isRepetitiveString(str,4));
		System.out.println(str+"____"+isContinuousNumber(str,4));
		System.out.println(getContinuousNumberPattern(4));
	}

	/**
	 * 匹配连续n个数字
	 * @param str
	 * @return
     */
	public static boolean isContinuousNumber(String str,int n){
		boolean match = false;
		String regex = "^.*(0123|1234|2345|3456|4567|6789|7890|9876|8765|7654|5432|4321).*$";
		if (str.matches(regex)){
			match = true;
		}
		return match;
	}
	public static String getContinuousNumberPattern(int n){
		if (continuousNumberPattern == null){
			int[] arr = {0,1,2,3,4,5,6,7,8,9};
			StringBuilder builder = new StringBuilder();
			for(int i=0;i<arr.length;i++){
				String temp = "";
				for(int j=0;j<n;j++){
					if ((i+j)<arr.length){
						temp = temp+arr[i+j];
					}
				}
				if(n==temp.length()){
					if (i<arr.length-1){
						builder.append(temp);
						builder.append("|");
						builder.append(StringUtils.reverse(temp));
						builder.append("|");
					}else{
						builder.append(temp);
						builder.append("|");
						builder.append(StringUtils.reverse(temp));
					}
				}
			}
			continuousNumberPattern = builder.toString();
		}
		return  continuousNumberPattern;
	};


	/**
	 * 匹配n个相同重复符
	 * @param str
	 * @param n
     * @return
     */
	public static boolean isRepetitiveString(String str,int n){
		boolean match = false;
		String regex = "^.*(.)\\1{"+(n-1)+"}.*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()){
			match = true;
		}
		return match;
	}
}

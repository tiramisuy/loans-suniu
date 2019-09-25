package com.rongdu.loans.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class ExtractHostTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String filePath = "D:/myeclipse-workspace1/bobcfc/WebContent/res/widget/baidu/map/SearchInfoWindow_min.js";
		File file = new File(filePath);
		String text = FileUtils.readFileToString(file, "utf-8");
		String regex = "\"([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+com";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		String result = null;
		int count = 0;
		Set<String> set = new LinkedHashSet<String>();
		while(matcher.find()){
			result = matcher.group();
			count++;
			set.add(result);
		}
		Iterator<String> iterator = set.iterator();
		count = 0;
		while(iterator.hasNext()){
			count++;
			System.out.println(StringUtils.substring(iterator.next(), 1));
		}
	}

}

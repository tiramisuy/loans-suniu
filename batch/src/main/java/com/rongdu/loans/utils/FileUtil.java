package com.rongdu.loans.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	
	public static File forceCreateFile(String path){
		File file  = new File(path);
		if (!file.exists()) {
			String dirPath = path.substring(0,path.lastIndexOf("/"));
			File dir = new File(dirPath);	
			if (!dir.exists()) {
				dir.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return file;
	}
	
	public static String formatFileSize(Long size){
		String str = null;
		if (size<0) {
			str = null;
		}else if(size<1024){
			str = String.valueOf(size)+"B";
		}else if(size<1024*1024){
			str = String.valueOf(size/1024)+"KB";
		}else if(size<1024*1024*1024){
			str = String.valueOf(size/(1024*1024))+"MB";
		}else{
			str = "大于1G";
		}
		return str;
	}
	
}

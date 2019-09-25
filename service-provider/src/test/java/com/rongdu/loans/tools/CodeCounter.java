/**
 *Copyright 2014-2017 ������ All rights reserved.
 */
package com.rongdu.loans.tools;

import java.io.*;
import java.util.ArrayList;

/**
 * ��������ͳ��
 * @author sunda
 * @version 2014-7-22
 */
public class CodeCounter {

	/**
	 * ��������ͳ��
	 */
	public static void main(String[] args) {
		String file = CodeCounter.class.getResource("/").getFile();
		String path = file.replace("target/test-classes", "src");

		ArrayList<File> al = getFile(new File(path));
		for (File f : al) {
			if (f.getName().matches(".*\\.java$")){ // ƥ��java��ʽ���ļ�
				count(f);
				System.out.println(f);
			}
		}
		System.out.println("ͳ���ļ���" + files);
		System.out.println("����������" + codeLines);
		System.out.println("ע��������" + commentLines);
		System.out.println("�հ�������" + blankLines);
	}
	
	static long files = 0;
	static long codeLines = 0;
	static long commentLines = 0;
	static long blankLines = 0;
	static ArrayList<File> fileArray = new ArrayList<File>();
	
	/**
	 * ���Ŀ¼�µ��ļ�����Ŀ¼�µ��ļ�
	 * @param f
	 * @return
	 */
	public static ArrayList<File> getFile(File f) {
		File[] ff = f.listFiles();
		for (File child : ff) {
			if (child.isDirectory()) {
				getFile(child);
			} else
				fileArray.add(child);
		}
		return fileArray;

	}

	/**
	 * ͳ�Ʒ���
	 * @param f
	 */
	private static void count(File f) {
		BufferedReader br = null;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = "";
			while ((line = br.readLine()) != null) {
				line = line.trim(); // ��ȥע��ǰ�Ŀո�
				if (line.matches("^[ ]*$")) { // ƥ�����
					blankLines++;
				} else if (line.startsWith("//")) {
					commentLines++;
				} else if (line.startsWith("/*") && !line.endsWith("*/")) {
					commentLines++;
					flag = true;
				} else if (line.startsWith("/*") && line.endsWith("*/")) {
					commentLines++;
				} else if (flag == true) {
					commentLines++;
					if (line.endsWith("*/")) {
						flag = false;
					}
				} else {
					codeLines++;
				}
			}
			files++;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
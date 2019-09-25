package com.rongdu.loans.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.common.collect.Maps;
import com.rongdu.core.utils.reflection.Reflections;

public class ExcelExporter {
	
	private static Map<String, CellStyle> styles;
	
	public <T> Workbook export(String title,List<T> data) {
		return execute(title, data,null);
	}
	
	public <T> Workbook export(String title,List<T> data,Workbook workbook) {
		return execute(title, data,workbook);
	}
	
	private <T> Workbook execute(String title,List<T> data,Workbook workbook) {
		// 声明一个工作薄
		if (workbook==null) {
			workbook = new HSSFWorkbook();			
		}
		try {
			//首先检查数据是否正确
			if (title == null) {
				throw new Exception("工作薄标题不能为空！");
			}
			if (data == null || data.size() == 0) {
				throw new Exception("导出数据不能为空！");
			}
			styles = createStyles(workbook);
			// 生成一个表格
			HSSFSheet sheet = (HSSFSheet) workbook.createSheet(title);
			@SuppressWarnings("unchecked")
			Class<T> pojoClass = (Class<T>) data.get(0).getClass();
			Field[] fields = pojoClass.getDeclaredFields();
			Excel excel = null;
			Map<String, Excel> map = new LinkedHashMap<String, Excel>();
			HSSFRow row = null;
			HSSFCell cell = null;
			int colIndex = 0;
			row = sheet.createRow(0);
			int rowNum = data.size()+1;
			for (Field field:fields) {
				excel = field.getAnnotation(Excel.class);
				if (null != excel) {
					map.put(field.getName(), excel);
					cell = row.createCell(colIndex++);
					cell.setCellStyle(styles.get("header"));
					cell.setCellValue(excel.fieldLabel());
					sheet.setColumnWidth(colIndex-1, 256*excel.fieldWidth());
				}
			}
			Set<String> keys = map.keySet();
			String value = null;
			Object val = null;
			T entity = null;
			for (int i = 1; i < rowNum; i++) {
				row = sheet.createRow(i);
				entity = data.get(i-1);
				colIndex = 0;
				for (String fieldName:keys) {
					val = Reflections.getFieldValue(entity, fieldName);
					value = val==null?null:val.toString();
					cell = row.createCell(colIndex++);
					cell.setCellStyle(styles.get("normal"));
					String convert = map.get(fieldName).convert();
					if(convert!=""&&!"".equals(convert)){
						String[] tmp = convert.split(";");
						for(String str:tmp){
							String[] tmp2 = str.split(":");
							if(tmp2[0].equals(value)){
								cell.setCellValue(tmp2[1]);	
							}
						}
					}else{
						cell.setCellValue(value);	
					}
					
					 
				}
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return workbook;
	}

	private static synchronized Map<String, CellStyle> createStyles(Workbook wb) {
		styles = Maps.newHashMap();
		DataFormat df = wb.createDataFormat();

		// --字体设定 --//

		//普通字体
		Font normalFont = wb.createFont();
		normalFont.setFontHeightInPoints((short) 10);

		//加粗字体
		Font boldFont = wb.createFont();
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		// 定义红色字体(用于突出显示某些数据)
		Font redFont = wb.createFont();
		redFont.setColor(Font.COLOR_RED);

		//蓝色加粗字体
		Font blueBoldFont = wb.createFont();
		blueBoldFont.setFontHeightInPoints((short) 10);
		blueBoldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		blueBoldFont.setColor(IndexedColors.BLUE.getIndex());

		// --Cell Style设定-- //
		
		//普通格式
		CellStyle normalStyle = wb.createCellStyle();
		normalStyle.setFont(normalFont);
		setBorder(normalStyle);
		styles.put("normal", normalStyle);
		
		//标题格式
		CellStyle headerStyle = wb.createCellStyle();
		headerStyle.setFont(boldFont);
		setBorder(headerStyle);
		styles.put("header", headerStyle);
		
		//红色字体
		CellStyle redStyle = wb.createCellStyle();
		redStyle.setFont(redFont);
		styles.put("red", redStyle);

		//日期格式
		CellStyle dateCellStyle = wb.createCellStyle();
		dateCellStyle.setFont(normalFont);
		dateCellStyle.setDataFormat(df.getFormat("yyyy"));
		setBorder(dateCellStyle);
		styles.put("dateCell", dateCellStyle);

		//数字格式
		CellStyle numberCellStyle = wb.createCellStyle();
		numberCellStyle.setFont(normalFont);
		numberCellStyle.setDataFormat(df.getFormat("#,##0.00"));
		setBorder(numberCellStyle);
		styles.put("numberCell", numberCellStyle);

		//合计列格式
		CellStyle totalStyle = wb.createCellStyle();
		totalStyle.setFont(blueBoldFont);
		totalStyle.setWrapText(true);
		totalStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		setBorder(totalStyle);
		styles.put("total", totalStyle);

		return styles;
	}

	private static void  setBorder(CellStyle style) {
		//设置边框
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	}
	
	
	public <T> Workbook export(String title,List<T> data, Class clz) {
		return execute(title, data,clz,null);
	}
	
	private <T> Workbook execute(String title,List<T> data,Class clz,Workbook workbook) {
		// 声明一个工作薄
		if (workbook==null) {
			workbook = new HSSFWorkbook();			
		}
		try {
			//首先检查数据是否正确
			if (title == null) {
				throw new Exception("工作薄标题不能为空！");
			}
			styles = createStyles(workbook);
			// 生成一个表格
			HSSFSheet sheet = (HSSFSheet) workbook.createSheet(title);
			@SuppressWarnings("unchecked")
			Field[] fields = clz.getDeclaredFields();
			Excel excel = null;
			Map<String, Excel> map = new LinkedHashMap<String, Excel>();
			HSSFRow row = null;
			HSSFCell cell = null;
			int colIndex = 0;
			row = sheet.createRow(0);
			int rowNum = data.size()+1;
			for (Field field:fields) {
				excel = field.getAnnotation(Excel.class);
				if (null != excel) {
					map.put(field.getName(), excel);
					cell = row.createCell(colIndex++);
					cell.setCellStyle(styles.get("header"));
					cell.setCellValue(excel.fieldLabel());
					sheet.setColumnWidth(colIndex-1, 256*excel.fieldWidth());
				}
			}
			if(null!=data&&data.size()>0){
				Set<String> keys = map.keySet();
				String value = null;
				Object val = null;
				T entity = null;
				for (int i = 1; i < rowNum; i++) {
					row = sheet.createRow(i);
					entity = data.get(i-1);
					colIndex = 0;
					for (String fieldName:keys) {
						val = Reflections.getFieldValue(entity, fieldName);
						value = val==null?null:val.toString();
						cell = row.createCell(colIndex++);
						cell.setCellStyle(styles.get("normal"));
						String convert = map.get(fieldName).convert();
						if(convert!=""&&!"".equals(convert)){
							String[] tmp = convert.split(";");
							for(String str:tmp){
								String[] tmp2 = str.split(":");
								if(tmp2[0].equals(value)){
									cell.setCellValue(tmp2[1]);	
								}
							}
						}else{
							cell.setCellValue(value);	
						}
						
						 
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return workbook;
	}
}

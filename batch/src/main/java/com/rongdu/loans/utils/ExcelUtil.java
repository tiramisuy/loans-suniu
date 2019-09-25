package com.rongdu.loans.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

import com.rongdu.core.utils.reflection.Reflections;
import com.rongdu.loans.exception.BizException;

public class ExcelUtil {

//	private static final Log logger = LogFactory.getLog(ExcelUtil.class);

	public static List<Object> readXls(String path,Class clazz) throws BizException {
		List<Object> list = new ArrayList<Object>();
		InputStream is;
		int rowSize = 0;
		HSSFCell cell = null;
		try {
			is = new FileInputStream(path);
			HSSFWorkbook hssWorkbook = new HSSFWorkbook(is);
			for (int rowNum = 0; rowNum < hssWorkbook.getNumberOfSheets(); rowNum++) {
				HSSFSheet hssfSheet = hssWorkbook.getSheetAt(rowNum);
				for (int rowIndex = 0; rowIndex < hssfSheet.getLastRowNum() + 2; rowIndex++) {
					HSSFRow row = hssfSheet.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() ;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
  					Field[] fileds = clazz.getFields();
					Object entity =clazz.newInstance();
					int columnNum = fileds.length> row.getLastCellNum()? row.getLastCellNum():fileds.length;
					for (short columnIndex = 0; columnIndex < columnNum; columnIndex++) {
					 
						Field field =fileds[columnIndex];
						Object valueObj;
	 					cell = row.getCell(columnIndex);
	  					if (cell != null) {
	  						String 	value= getCellValue(cell);
 							if(field.getType().isAssignableFrom(Long.class)) {
 								valueObj = new Long(value);
 							}else if(field.getType().isAssignableFrom(Integer.class)){
 								valueObj = new Integer(value);
 							}else{
 								valueObj = value;
 							}
 							Reflections.setFieldValue(entity, field.getName(), valueObj);
						}
					}
 						list.add(entity);
				}
			}
			is.close();
			
		} catch (FileNotFoundException e) {
 			e.printStackTrace();
 			throw new BizException("找不到对应的excel文件--"+e);
		}catch (IOException e) {
 			e.printStackTrace();
 			throw new BizException("读取excel文件时发生异常--"+e);
		}catch (IllegalAccessException e) {
			e.printStackTrace();
 			throw new BizException("excel字段与实体进行映射时候发生异常--"+e);
		}catch (InstantiationException e) {
			e.printStackTrace();
			throw new BizException("实例化实体类时发生异常--"+e);
		}
		
		return list;
	}

	public static void main(String[] args) {

	}
	
	
	private static String getCellValue(HSSFCell cell){
		String value="";
 		switch(cell.getCellType()){
		case Cell.CELL_TYPE_STRING:value = cell.getStringCellValue();break;
		case Cell.CELL_TYPE_NUMERIC: 
			if(DateUtil.isCellDateFormatted(cell)){
				Date date =cell.getDateCellValue();
				if(date!=null){
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				}else{
					value="";
				}
			}else{
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}break;
		case Cell.CELL_TYPE_FORMULA:value="";break;
		case Cell.CELL_TYPE_BLANK:break;
		case Cell.CELL_TYPE_ERROR:value ="";break;
		default :value ="";
		}
		return value;
	}

}

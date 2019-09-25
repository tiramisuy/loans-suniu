package com.rongdu.loans.fileserver.common;

import java.io.File;
import java.io.IOException;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class ImageMagick {


	/**
	 * * 根据坐标裁剪图片
	 * 
	 * @param srcPath
	 *            要裁剪图片的路径
	 * @param newPath
	 *            裁剪图片后的路径
	 * @param x吖
	 *            起始横坐标
	 * @param y
	 *            起始挫坐标
	 * @param x1
	 *            结束横坐标
	 * @param y1
	 *            结束挫坐标
	 */
	public synchronized static void cutImage(String srcPath, String newPath, int x, int y,
			int x1, int y1) throws Exception {
		int width = x1 - x;
		int height = y1 - y;
		GMOperation op = new GMOperation();
		op.addImage(srcPath);
		/**
		 * width：裁剪的宽度 height：裁剪的高度 x：裁剪的横坐标 y：裁剪的挫坐标
		 */
		op.crop(width, height, x, y);
		op.addImage(newPath);
		ConvertCmd convert = new ConvertCmd(true);

		convert.run(op);
	}

	/**
	 * 
	 * 根据尺寸缩放图片
	 * @param width
	 *            缩放后的图片宽度
	 * @param height
	 *            缩放后的图片高度
	 * @param srcPath
	 *            源图片路径
	 * @param newPath
	 *            缩放后图片的路径
	 * 
	 */
	public synchronized static String cutImage(int width, int height, String srcPath,
			String newPath, String quality, String type) throws Exception {

		String localFilePath = srcPath;
//		if(isFileExist(newPath)){
//			return null;
//		}
//		System.out.println( "123="+new FcacheClient().get(0, srcPath.substring(0,srcPath.lastIndexOf("."))));
		try {
//			String remoteFilePath =  new FcacheClient().get(0, srcPath.substring(0,srcPath.lastIndexOf("."))).getValue().getValue().toString();
////			String remoteFilePath =  "http://10.211.64.68:80/group2/M00/00/49/CtNARlShBeqAOMUxAABa2hQjz4I881.jpg";
//			if (type.equals("gif"))
//				throw new Exception("Not Support Gif format!");
//
//		String groupName = remoteFilePath.substring(0,remoteFilePath.indexOf("/"));
//			int ret = new FileManager().downloadFile(groupName,remoteFilePath.substring(remoteFilePath.indexOf("/")+1),
////					 new FileManager().downloadFile(groupName,remoteFilePath,
//					localFilePath);
			ConvertCmd cmd = new ConvertCmd(true);
			GMOperation op = new GMOperation();
			op.resize(width, height);
//			op.transparent("black");
			if(width>=400&&height>=400){
				op.addRawArgs("-quality", "60","-gravity","center");
			}
			else{
				op.addRawArgs("-quality", "70","-gravity","center");
			}
			
			op.addRawArgs("-extent",
					String.valueOf(width) + "x" + String.valueOf(height));
			op.addImage();
			op.addImage();
//			http://10.211.64.68:80/group2/M00/00/49/CtNARlShBeqAOMUxAABa2hQjz4I881.jpg
//			if(!isFileExist(localFilePath)){
////				new FileManager().downloadFile(groupName, "M00/00/00/" + srcPath,
////						localFilePath);
//				
//				new FileManager().downloadFile(groupName,remoteFilePath.substring(remoteFilePath.indexOf("/")+1),
//						localFilePath);
//			}
			
			cmd.run(op, SystemEnv.getProperty("local.image.path")+localFilePath, newPath);

		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * op.addImage();
		 * 
		 * 
		 * cmd.run(op,srcPath,newPath);
		 */

		return null;
	}

	
	public static  boolean isFileExist(String fileName){
		File name = new File(fileName);
		if(name.exists()){
			return true;
		}
		
		return false;
	}
	/**
	 * 给图片加水印
	 * 
	 * @param srcPath
	 *            源图片路径
	 */
	public synchronized static void addImgText(String srcPath, String desPath,String font,int pointsize,String color,String waterInfo,String locate,int x,int y)
			throws Exception {
		GMOperation op = new GMOperation();
		op.font(font).gravity(locate).pointsize(pointsize).fill(color)
				.draw("text "+x+","+y+"\'"+waterInfo+"\'");
		
		op.addImage();
		op.addImage();

		ConvertCmd cmd = new ConvertCmd(true);

		try {
			cmd.run(op, srcPath, desPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 /** 
     * 图片水印 
     * 
     * @param srcImagePath   源图片 
     * @param waterImagePath 水印 
     * @param destImagePath  生成图片 
     * @param gravity  图片位置 
     * @param dissolve 水印透明度 
     * @author sunz
     */ 
	public synchronized static void waterMark(String waterImagePath, String srcImagePath, String destImagePath) {  
        IMOperation op = new IMOperation();  
        op.gravity("center");  
        op.dissolve(120);  
        op.addImage(waterImagePath);  
        op.addImage(srcImagePath);  
        op.addImage(destImagePath);  
        CompositeCmd cmd = new CompositeCmd(true);  
        try {  
            cmd.run(op);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (IM4JavaException e) {  
            e.printStackTrace();  
        }  
    }  
	
	public static void main(String[] args) throws Exception {
		// cutImage(500,150,"/home/ben/Pictures/1.jpg","/home/ben/Pictures/new.jpg","100");
		// Long start = System.currentTimeMillis();
		// //cutImage(100,100,
		// "e:\\37AF7D10F2D8448A9A5.jpg","e:\\37AF7D10F2D8448A9A5_bak2.jpg",2,"100");
		// //
		// addImgText("/home/ben/Pictures/1.jpg","/home/ben/Pictures/output.jpg");
		// op.addImage();
		// Long end = System.currentTimeMillis();
		// System.out.println("time:"+(end-start)/3600);
//		System.getProperty("user.dir");
//		System.out.println(System.getProperty("user.dir")+"/water.png");
//		cutImage(100, 100, srcPath, newPath, quality, type);、
//		waterMark("D:/商品主图_水印logo.png", "d:/CtNARVS3ScuAKj93AAUZHMzf2nM066.jpg_800x800.jpg", "D:/CtNARVTYHNyAauliAAAuWmV1o_Q964——water.jpg");  
//		new FileManager().downloadFile("group2", "M00/00/03/CtNARlQ8062AAvIlAAveawyJ3RM329.jpg", "D:/111.jpg");
		cutImage(720, 328, "C:/Users/Administrator/Desktop/images/banner1.jpg", "C:/Users/Administrator/Desktop/banner1.jpg", 1+"", "jpg");
	}
}

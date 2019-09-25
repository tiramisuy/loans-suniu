package com.rongdu.loans.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * 图片处理工具类
 * 
 * @author neo
 *
 *  2013-08-26
 */
public class ImageUtils {
	
	public static void mergeImage(String imagePath1,String imagePath2,String targetImagePath) throws IOException{
		System.out.println("正在合并图片...");
		long start = System.currentTimeMillis();
		File file1 = new File(imagePath1);
  		BufferedImage image1 = ImageIO.read(file1);
		File file2 = new File(imagePath2);
		BufferedImage image2 = ImageIO.read(file2);
		// 图片宽度
		int width = image1.getWidth();
		// 图片高度
		int height = image1.getHeight();
		// 图片宽度
		int width2 = image2.getWidth();
		// 图片高度
		int height2 = image2.getHeight();
		int[] rgbArray1 = new int[width*height];
		rgbArray1 = image1.getRGB(0, 0, width, height, rgbArray1, 0, width);
		int[] rgbArray2 = new int[width2*height2];
		rgbArray2 = image2.getRGB(0, 0, width2, height2, rgbArray2, 0, width2);
        BufferedImage image = new BufferedImage(width+width2,height,image1.getType());
        // 设置左半部分的RGB
        image.setRGB(0, 0,width, height, rgbArray1, 0, width);
        // 设置右半部分的RGB
        image.setRGB(width,0,width2,height>height2?height2:height, rgbArray2, 0, width2);
        String suffix = imagePath1.substring(imagePath1.lastIndexOf('.')+1);
        File targetFile = new File(targetImagePath);
        ImageIO.write(image, suffix, targetFile);
        long end = System.currentTimeMillis();
        System.out.println("合并完成,耗时"+(end-start)/1000.0+"秒");
	}
	
	public static void mergeImage(InputStream is1,InputStream is2,String targetImagePath,String suffix) throws IOException{
		System.out.println("正在合并图片...");
		long start = System.currentTimeMillis();
	//	File file1 = new File(imagePath1);
  		BufferedImage image1 = ImageIO.read(is1);
	//	File file2 = new File(imagePath2);
		BufferedImage image2 = ImageIO.read(is2);
		// 图片宽度
		int width = image1.getWidth();
		// 图片高度
		int height = image1.getHeight();
		// 图片宽度
		int width2 = image2.getWidth();
		// 图片高度
		int height2 = image2.getHeight();
		int[] rgbArray1 = new int[width*height];
		rgbArray1 = image1.getRGB(0, 0, width, height, rgbArray1, 0, width);
		int[] rgbArray2 = new int[width2*height2];
		rgbArray2 = image2.getRGB(0, 0, width2, height2, rgbArray2, 0, width2);
        BufferedImage image = new BufferedImage(width+width2,height,image1.getType());
        // 设置左半部分的RGB
        image.setRGB(0, 0,width, height, rgbArray1, 0, width);
        // 设置右半部分的RGB
        image.setRGB(width,0,width2,height>height2?height2:height, rgbArray2, 0, width2);
        //String suffix = imagePath1.substring(imagePath1.lastIndexOf('.')+1);
        File targetFile = new File(targetImagePath);
        ImageIO.write(image, suffix, targetFile);
        long end = System.currentTimeMillis();
        System.out.println("合并完成,耗时"+(end-start)/1000.0+"秒");
	}
	
	public static void main(String[] args) throws IOException {
		String srcImgPath = "E:/1.jpg";
		String destImgPath = "e:/2.jpg";
		InputStream is1 = new FileInputStream(new File(srcImgPath));
		InputStream is2 = new FileInputStream(new File(destImgPath));
		String targetImagePath = "e:/3.jpg";
		mergeImage(is1,is2,targetImagePath,"jpg");
	}
}

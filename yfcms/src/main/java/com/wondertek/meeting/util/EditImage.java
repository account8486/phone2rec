package com.wondertek.meeting.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class EditImage {
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();

		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int hight) throws Exception {
		BufferedImage srcImage;
		// String ex =
		// fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		}
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || hight > 0) {
			srcImage = resize(srcImage, width, hight);
		}
		ImageIO.write(srcImage, imgType, saveFile);

	}


	public static void scaleUserLogo(String sPath, int outWidth,
			int outHeight, int l, int t, int r, int b) throws Exception {

		File sourceFile = new File(sPath);
		BufferedImage src = ImageIO.read(sourceFile);

		FileOutputStream outFile = new FileOutputStream(sPath);

		BufferedImage buff_img = new BufferedImage(outWidth, outHeight,
				BufferedImage.TYPE_INT_RGB);

		Graphics gs = buff_img.createGraphics();
		// //(l,t)(r,b)
		gs.drawImage(src, 0, 0, outWidth, outHeight, l, t, r, b, null);
		gs.dispose();
		JPEGImageEncoder encoder1 = JPEGCodec.createJPEGEncoder(outFile);
		encoder1.encode(buff_img);
		outFile.close();

		sourceFile = null;
		src = null;
		buff_img = null;
		gs = null;

	}
	public static Color getRandColor(int fc,int bc){
	    Random random = new Random();
	    if(fc>255) fc=255;
	    if(bc>255) bc=255;
	    int r=fc+random.nextInt(bc-fc);
	    int g=fc+random.nextInt(bc-fc);
	    int b=fc+random.nextInt(bc-fc);
	    return new Color(r,g,b);
	    }

	public static void main(String argv[]) {
		try {
			// EditImage.saveImageAsJpg("E:/a.JPG", "E:/b.JPG", 500, 500);
			EditImage
					.scaleUserLogo(
							"C:/Program Files/Apache Software Foundation/Tomcat 5.5/webapps/index/PhotoDir/2006/11/2/1162457267281.JPG",
							190, 190, 43, 402, 201, 560);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

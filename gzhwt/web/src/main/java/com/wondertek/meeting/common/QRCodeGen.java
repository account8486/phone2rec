package com.wondertek.meeting.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * 
 * 生成签到二维码
 * 
 * @author 金祝华
 */
public class QRCodeGen {
	private static final int BLACK = 0x000000;
	private static final int WHITE = 0xFFFFFF;

	/**
	 * @param args
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void main(String[] args) throws WriterException, IOException {

		String str = "姓名：" + "金祝华" + "\n";
		str += "会议：" + "test会议" + "\n";
		str += "会议号：" + "140" + "\n";
		str += "手机号：" + "15375296623";
		genQRCode(str);
	}

	public static BufferedImage genQRCode(String str) throws WriterException, IOException {

		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300, hints);

		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}

		// String picFormat = "jpg";
		// File file = new File("d:/test/test" + "." + picFormat);
		// ImageIO.write(image, picFormat, file);
		// MatrixToImageWriter.writeToFile(bitMatrix, picFormat, file);

		return image;
	}
}

package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileMain {

	/**
	 * @param args
	 */
public static void main(String[] args){
	FileMain f = new FileMain();
	System.out.println(f.getFileStr("e:\\aaa.txt"));
}

	public String getFileStr(String path) {
		StringBuffer buff = new StringBuffer();
		File srcFile = new File(path);
		int readByte = 0;
		InputStream ins = null;
		try {
			// 打开源文件
			ins = new FileInputStream(srcFile);
			// 打开目标文件的输出流
			byte[] buf = new byte[1024];
			// 一次读取1024个字节，当readByte为-1时表示文件已经读取完毕
			while ((readByte = ins.read(buf)) != -1) {
				// 将读取的字节流写入到输出流
				//for(byte c:buf){
					buff.append(new String(buf,"gbk"));
					ins.read();
				//}
			}
			System.out.println("读取文件成功: " );
		} catch (Exception e) {
			System.out.println("读取文件失败: " + e.getMessage());
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ine) {
					ine.printStackTrace();
				}
			}
		}
		return buff.toString();
	}

}

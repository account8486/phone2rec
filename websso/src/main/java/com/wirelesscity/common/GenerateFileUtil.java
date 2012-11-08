package com.wirelesscity.common;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author liwei
 * FTP文件生成工具类
 */
public class GenerateFileUtil 
{
	public static Log log = LogFactory.getLog(GenerateFileUtil.class);
	
	/**
	 *  生成FTP文件名
	 *  子系统缩写_ 数据类型_YYYYMMDDHHMMSS_序列号.dat
	 *  
	 */
	public static String getFileName(String dataType)
	{
		log.info("enter method getFileName(String dataType)");
		log.debug("dataType is " + dataType);
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.ftpFile.SYS_STATISTICS);
		sb.append("_");
		sb.append(dataType);
		sb.append("_");
		try {
			sb.append(DateUtil.getTimeNow());
		} catch (ParseException e) {
			log.debug("-------getFileName(String dataType) throws a ParseException");
			e.printStackTrace();
		}
		sb.append("_");
		sb.append("000001");
		sb.append(".dat");
		log.debug("filename is " + sb.toString());
		log.info("exit method getFileName(String dataType)");
		return sb.toString();
	}
	
	/**
	 *  生成FTP文件头
	 *  生成规则：头记录标记+发起方平台编码+接收方平台编码+文件序号+文件产生开始时间+文件版本号
	 */
	public static String getFileHead()
	{
		log.info("enter method getFileHead()");
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.ftpFile.FILE_HEAD);
		sb.append(Constants.ftpFile.WPID_AH);
		sb.append(Constants.ftpFile.WPID);
		sb.append("000001");
		try {
			sb.append(DateUtil.getTimeNow());
		} catch (ParseException e) {
			log.debug("-------getFileHead() throws a ParseException");
			e.printStackTrace();
		}
		sb.append(Constants.ftpFile.FILE_VERSION);
		log.debug("file head is " + sb.toString());
		log.info("exit method getFileHead()");
		return sb.toString();
	}
	

	/**
	 * 生成FTP文件头
	 * 尾记录标记+文件结束时间+汇总文件中记录条数+结果代码
	 * @param recordSize  查询记录数
	 * @return  sb
	 */
	public static String getFileFoot(int recordSize)
	{
		log.info("enter method getFileFoot(int recordSize)");
		log.debug("param recordSize is " + recordSize);

		//拼接记录数
		StringBuilder sizeString = new StringBuilder();
		if (recordSize >= 0)
		{
			for (int i=0;i<(9-String.valueOf(recordSize).length());i++)
			{
				sizeString.append("0");
			}
			sizeString.append(String.valueOf(recordSize));
		}
		log.debug("sizeString is " + sizeString);
		
		//拼接文件尾
		StringBuilder sb = new StringBuilder();
		sb.append(Constants.ftpFile.FILE_FOOT);
		try {
			sb.append(DateUtil.getTimeNow());
		} catch (ParseException e) {
			log.debug("-------getFileFoot(int recordSize) throws a ParseException");
			e.printStackTrace();
		}
		sb.append(sizeString);
		sb.append(Constants.ftpFile.FILE_RESULT);
		
		log.debug("file head is " + sb.toString());
		log.info("exit method getFileFoot(int recordSize)");
		return sb.toString();
	}
	

	/**
	 * 生成文件
	 * @param list        List<Map<String,Object>> 数据库查询记录列表
	 * @param ftpPath     ftp文件本地生成绝对路径
	 * @param dataType    文件名生成时所需数据类型
	 * @return file       ftp本地文件全路径       
	 */
	public static String generate(List<Map<String,Object>> list, String ftpPath, String dataType)
	{
		log.info("enter method generate()");
		StringBuilder sb = new StringBuilder();
		//文件头
		sb.append(getFileHead());
		sb.append("\r\n");
		//文件体
		for (int i=0;i<list.size();i++)
		{
			Map<String,Object> map = list.get(i);
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				sb.append(String.valueOf(entry.getValue())).append("|");
			}
			//去掉一个“|”,delete效率比raplace高
			sb.delete(sb.length()-1, sb.length());
			sb.append("\r\n");
		}

		//FTP文件绝对路径
		String file = ftpPath + File.separator + getFileName(dataType);
		log.debug("file content :\\n" + sb.toString() + "FTP文件绝对路径:" + file);
		
		//写文件
		FileUtil.writeFileByString(file, sb.toString(), "UTF-8");
		
		//追加到文件尾,false重新生成文件，true追加在文件最后
		FileUtil.writeFileByString(file, getFileFoot(list.size()), "UTF-8", true);
		log.info("exit method generate()");
		return file;
	}
}

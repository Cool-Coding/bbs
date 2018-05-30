package cn.yang.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.log4j.Logger;

import cn.yang.bbs.exception.UtilClassException;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-11-21
 */
public class PropertiesUtil {

	private static Properties props=new Properties();
	private static Logger log=Logger.getLogger(PropertiesUtil.class);
	private static OutputStream out;
	private PropertiesUtil(){};
	private static File file;
	static{
		InputStream in=PropertiesUtil.class.getClassLoader().getResourceAsStream("configure.properties");		
		try {
			props.load(in);
		} catch (IOException e) {
			log.error("读取配置文件'configure.properties'失败!!");
			throw new UtilClassException(e.getMessage());
		}
	}
	/**
	 * 保存指定属性
	 * @param param
	 * @param value
	 */
	public static void save(String param,String value){
		props.setProperty(param,value);
	}
	
	/**
	 * 读取指定属性
	 * @param param
	 */
	public static String get(String param){
		return props.getProperty(param);
	}
	/**
	 * 删除指定属性
	 * @param param
	 */
	public static void delete(String param){
		props.remove(param);
	}
	
	public static void close(){
		
		try {
			file=new File(PropertiesUtil.class.getClassLoader().getResource("configure.properties").toURI());
			out=new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			throw new UtilClassException(e1.getMessage());
		} catch (URISyntaxException e) {
			throw new UtilClassException(e.getMessage());
		}
		
		try {
			//file.delete();
			props.store(out, null);
		} catch (IOException e) {
			throw new UtilClassException(e.getMessage());
		}
	}
}

package cn.yang.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="mailto:riguang_2007@163.com">ygy</a>
 * 2010-12-15
 */
//采用单例模式
public class FileManager {

	private FileManager(){};
	
	
    /**
     * 复制文件
     * @param source 源文件
     * @param destination 目标文件或文件夹
     * @throws IOException 
     * @throws IOException 
     * 若源文件为文件夹，则目标文件默认为文件夹，不管目标文件是文件还是文件夹都当做文件夹来对待，若源文件为文件，则检查目标文件中是否含有.，若有则将目标文件做为文件，若没有则为文件夹
     */
	public static void copy(File source,File destination) throws IOException{
		
		//如果源文件或目标文件为空，则返回false;
		if(source==null || destination==null) return;
		if(source.exists()){
			if(source.isDirectory()){
				if(!destination.exists()) destination.mkdirs();
			}else{
				if(destination.getName().lastIndexOf(".")!=-1){
					if(!destination.getParentFile().exists()) destination.getParentFile().mkdirs();
				}else{
					if(!destination.exists()) destination.mkdirs();
				}
			}
			
        	copyFile(source,destination,"");
		}
	}
	
	private static void copyFile(File source,File destination,String relativeDir) throws IOException{
         if(source==null) return;
          //新建输入输出流
         InputStream filein=null;
         OutputStream fileout=null;
         BufferedInputStream  bufferIn=null;
         BufferedOutputStream bufferOut=null;
         if(source.isDirectory()){
        	 //得到目录的最后一个相对文件夹名
             relativeDir+=source.getName()+File.separator;
        	 File[] files=source.listFiles();
        	 for(File file:files){
        		 copyFile(file,destination,relativeDir);
        	 }
         }else{
        	    //判断destination是否为目录
 			    if(destination.isDirectory()) destination=new File(destination.getPath(),relativeDir+source.getName());
 	
 				//开始正式复制
 				filein=new FileInputStream(source);
 				fileout=new FileOutputStream(destination);
 			    bufferIn=new BufferedInputStream(filein);
 			    bufferOut=new BufferedOutputStream(fileout);
 			    byte[] bytes=new byte[1024];
 			    int length=-1;
 			    while((length=bufferIn.read(bytes))!=-1){
 			    	bufferOut.write(bytes,0,length);
 			    }
 			    //关闭输入输出流
 			    bufferIn.close();
 			    bufferOut.close();
         }
	}
	
	/**
	 * 删除文件
	 * @param source 源文件或文件夹
	 */
	public static void delete(File source){
		if(source==null) return;
		if(source.exists()) {
         deleteFile(source);
		}
	}
	
	private static void deleteFile(File file){
		if(file==null) return;
		if(file.isDirectory()){
			File[] files=file.listFiles();
			for(File f:files){
				deleteFile(f);
			}
			file.delete();//删除文件夹内的文件后删除文件夹
		}else file.delete();
	}
	
	/**
	 * 移动文件或文件夹
	 * @param source
	 * @param destination
	 * @throws IOException
	 */
	public static void move(File source,File destination) throws IOException{
		//首先复制到目的地，然后删除源文件或文件夹
		copy(source,destination);
		delete(source);
	}
	
}

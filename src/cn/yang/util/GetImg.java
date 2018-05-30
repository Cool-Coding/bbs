package cn.yang.util;
 import java.io.*;  
 import java.sql.*;  
 public class GetImg {  
   
 private static final String URL = "jdbc:mysql://localhost:3306/bbs?user=root&password=mysql&useUnicode=true&characterEncoding=utf-8";  
 private Connection conn = null;  
 private PreparedStatement pstmt = null;  
 private ResultSet rs = null;  
 private File file = null;  
   
 public void blobRead(String outfile, int picID) throws Exception {  
 FileOutputStream fos = null;  
 InputStream is = null;  
 byte[] Buffer = new byte[4096];  
 try {  
 Class.forName("org.gjt.mm.mysql.Driver").newInstance();  
 conn = DriverManager.getConnection(URL);  
 pstmt = conn.prepareStatement("select avatar from user where loginName=?");  
 pstmt.setString(1, "acde");
 rs = pstmt.executeQuery();  
 rs.next();  
 file = new File(outfile);  
 if (!file.exists()) {  
 file.createNewFile(); // 如果文件不存在，则创建  
 }  
 fos = new FileOutputStream(file);  
 is = rs.getBinaryStream("avatar");  
 int size = 0;  
System.out.println("开始");

 while ((size = is.read(Buffer)) != -1) {  
 System.out.println(size);  
 fos.write(Buffer, 0, size);  
 }
 System.out.println("结束");
 } catch (Exception e) {  
 System.out.println( e.getMessage());  
 e.printStackTrace();
 } finally {  
 // 关闭用到的资源  
 fos.close();  
 rs.close();  
 pstmt.close();  
 conn.close();  
 }  
 }  
 }  

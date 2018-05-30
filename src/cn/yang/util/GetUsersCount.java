package cn.yang.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetUsersCount {
	 private static final String URL = "jdbc:mysql://hserver1n:3306/bbs?user=yanggy&password=878963&useUnicode=true&characterEncoding=utf-8";  
	 private Connection conn = null;  
	 private PreparedStatement pstmt = null;  
	 private ResultSet rs = null;  

	 public int getUsersCount(){
		 int count=0;
		 try {  
			 Class.forName("org.gjt.mm.mysql.Driver").newInstance();  
			 conn = DriverManager.getConnection(URL);  
			 pstmt = conn.prepareStatement("select count(loginName) from User");  
			 rs=pstmt.executeQuery();
			 rs.next();
			count=rs.getInt(1);
		 }catch(Exception e){
			 throw new RuntimeException(e.getMessage());
		 }
		 return count;
	 }
}

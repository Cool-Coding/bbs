package cn.yang.util;

import java.io.*;
import java.sql.*;

public class PutImg {

public void putimg(String loginName,InputStream photoStream) {
   try {
    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    String url = "jdbc:mysql://hserver1n:3306/bbs?user=yanggy&password=878963&useUnicode=true&characterEncoding=utf-8";

    Connection conn = DriverManager.getConnection(url);

    PreparedStatement pstmt = null;
    String sql = "";
     
   sql = "update User o set o.avatar=? where o.loginName=?";
   
   pstmt = conn.prepareStatement(sql);
   pstmt.setBinaryStream(1, photoStream);
   pstmt.setString(2, loginName);

    pstmt.executeUpdate();
    pstmt.close();

    conn.close();
   } catch (Exception e) {
    e.printStackTrace();
   }
}
}


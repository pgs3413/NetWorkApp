package chapter14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class DBOperate3 {
public static void main(String[] args) throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	String url="jdbc:mysql://202.116.195.71:3306/mypeopledb?characterEncoding=utf8&useSSL=false";
	String user="student";
	String pwd="student";
	Connection conn=DriverManager.getConnection(url, user, pwd);
	String sql="insert into peoples2(NO,NAME,AGE,CLASS,IP) values(?,?,?,?,?)";
	PreparedStatement statement = conn.prepareStatement(sql);
	statement.setObject(5, "");
	statement.setObject(1, "");
	statement.setObject(2, "");
	statement.setObject(3, null);
	statement.setObject(4, "");
	int result=statement.executeUpdate();
	System.out.println(result);
	if(conn!=null) conn.close();
	System.out.println("success");
}
}
package chapter14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class DBOperate {
public static void main(String[] args) throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	String url="jdbc:mysql://202.116.195.71:3306/STUDENTDB1?characterEncoding=utf8&useSSL=false";
	String user="student";
	String pwd="student";
	Connection conn=DriverManager.getConnection(url, user, pwd);
	String sql="insert into STUDENTS(IP,NO,NAME,AGE,CLASS) values(?,?,?,?,?)";
	PreparedStatement statement = conn.prepareStatement(sql);
	statement.setObject(1, "");
	statement.setObject(2, "");
	statement.setObject(3, "");
	statement.setObject(4, 18);
	statement.setObject(5, "");
	int result=statement.executeUpdate();
	System.out.println(result);
	if(conn!=null) conn.close();
	System.out.println("success");
}
}

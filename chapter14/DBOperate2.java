package chapter14;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;



public class DBOperate2 {
public static void main(String[] args) throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	String url="jdbc:mysql://202.116.195.71:3306/mypeopledb?characterEncoding=utf8&useSSL=false";
	String user="student";
	String pwd="student";
	Connection conn=DriverManager.getConnection(url, user, pwd);
	DatabaseMetaData metaData=conn.getMetaData();
	ResultSet tableSet = metaData.getTables(null, null, null, new String[] {"TABLE"});
	ArrayList<String> tablesList=new ArrayList<String>();
	while(tableSet.next()) {
		String table=tableSet.getString("TABLE_NAME");
		tablesList.add(table);
		System.out.println(table);
	}
	PreparedStatement statement=null;
	ResultSet rs=null;
	for(String tableName:tablesList) {
		ArrayList<String> filedsList=new ArrayList<String>();
		String sql="select * from "+tableName;
		statement=conn.prepareStatement(sql);
		rs=statement.executeQuery();
		ResultSetMetaData fileds = rs.getMetaData();
		int n=fileds.getColumnCount();
		System.out.println(tableName);
		for(int i=1;i<=n;i++) {
			String filedName=fileds.getColumnName(i);
			System.out.print(filedName+" ");
		}
		System.out.println();
		if(rs.next()) {
			for(int i=1;i<=n;i++) {
				Object obj=rs.getObject(i);
				String name=null;
				if(obj!=null)
				name = obj.getClass().getName();
				System.out.print(obj+" "+name+" ");
			}
		}
		System.out.println();
	}
	if(conn!=null) conn.close();
	System.out.println("success");
}
}
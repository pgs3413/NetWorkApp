package chapter09;

public class Trans {
	
	public static int ipToInt(String ip) {
		String[] ipArray=ip.split("\\.");
		int num=0;
		for(int i=0;i<ipArray.length;i++) {
			int a=Integer.parseInt(ipArray[i]);
			num=(a<<8*(3-i))|num;
		}
		return num;
	}
	
	public static String intToIp(int i) {
		String[] ipArray=new String[4];
		int a=0xff;
		for(int j=0;j<4;j++) {
		int b=i>>8*(3-j)&a;
		ipArray[j]=String.valueOf(b);
		}
		return String.join(".", ipArray);
	}
	
public static void main(String[] args) {
String ip="192.168.234.3";
int i = ipToInt(ip);
System.out.println(i);
int j=-1062671869;
String s = intToIp(j);
System.out.println(s);
}
}

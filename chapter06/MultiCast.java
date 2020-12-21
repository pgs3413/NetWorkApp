package chapter06;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class MultiCast {

	InetAddress groupIP;
	int port=8900;
	MulticastSocket ms;
	
	
	
	public MultiCast() throws Exception {
		groupIP=InetAddress.getByName("225.0.1.1");
		ms=new MulticastSocket(port);
		ms.joinGroup(groupIP);
	}
	public void send(String msg) throws Exception {
		byte[] outBuf=new byte[1024];
		String s="来自一个帅哥的组播："+msg;
		outBuf = s.getBytes("utf-8");
		DatagramPacket packet = new DatagramPacket(outBuf,0, outBuf.length,groupIP,port);
		ms.send(packet);
	}
	public String receive() throws Exception {
		byte[] inBuf=new byte[1024];
		DatagramPacket packet = new DatagramPacket(inBuf, inBuf.length);
		ms.receive(packet);
		String msg=new String(inBuf, 0, inBuf.length, "utf-8");
		return msg;
	}
	public static void main(String[] args) throws Exception {
		MultiCast multiCast = new MultiCast();
		Scanner scanner = new Scanner(System.in);
		//String s = scanner.next();
		//multiCast.send(s);
		
		  String msg = multiCast.receive();
		   System.out.println(msg);
		 		scanner.close();
		
	}
}

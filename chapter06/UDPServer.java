package chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class UDPServer {
public static void main(String[] args) throws IOException {
	DatagramSocket socket = new DatagramSocket(8008);
	while(true) {
		byte[] in=new byte[1024];
		DatagramPacket inPacket = new DatagramPacket(in, 1024);
		socket.receive(inPacket);
		InetAddress inetAddress = inPacket.getAddress();
		int port = inPacket.getPort();
		String msg=new String(in, 0, in.length, "utf-8");
		System.out.println(msg);
		String str="20181003040&潘冠森&"+new Date().toString()+"&"+msg;
		byte[] out = str.getBytes("utf-8");
		DatagramPacket outPacket = new DatagramPacket(out, 0, out.length,inetAddress, port);
		socket.send(outPacket);
	}
}
}

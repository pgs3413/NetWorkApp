package chapter06;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javafx.scene.control.TextArea;

public class UDPClient {

	private InetAddress remoteIP;
	private int remotePort;
	private DatagramSocket socket;
	
	
	public UDPClient(String ip,String port) throws Exception {
		remoteIP=InetAddress.getByName(ip);
		remotePort=Integer.parseInt(port);
		socket=new DatagramSocket();
	}
	
	public void Send(String msg) {
		try {
			byte[] bytes = msg.getBytes("utf-8");
			DatagramPacket outPacket = new DatagramPacket(bytes, 0, bytes.length, remoteIP, remotePort);
			socket.send(outPacket);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String Receive() {
		byte[] buf=new byte[1024];
		DatagramPacket inPacket = new DatagramPacket(buf, 1024);
		String msg=null;
		try {
			socket.receive(inPacket);
			msg=new String(buf, 0, buf.length, "utf-8");
			System.out.println(msg);
			//String msg = new String(inPacket.getData(), 0, inPacket.getLength(), "utf-8");
//			taDisplay.appendText(msg+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(1);
			e.printStackTrace();
		}
		return msg;
	}
	
	public void Close() {
		if(socket!=null) socket.close();
	}
	
}

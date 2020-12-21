package chapter11;

import java.net.InetAddress;

import jpcap.JpcapSender;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

public class PacketSender {
	public static byte[] convertMacFormat(String mac) {
		String[] macList=mac.split("[-:]"); 
		byte[] bytes=new byte[6];
		for(int i=0;i<macList.length;i++) {
			bytes[i]=(byte)Integer.parseInt(macList[i], 16);
		}
		return bytes;
	}
	public static void sedTCPPacket(JpcapSender sender,int srcPort,int dstPort,String srcHost,String dstHost,
			String data,String srcMac,String dstMac) {
try {
TCPPacket tcp=new TCPPacket(srcPort, dstPort, 56, 78, false, false, false, false, true, false, true, true, 200, 10);
tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP	,
InetAddress.getByName(srcHost),InetAddress.getByName(dstHost) );
tcp.data=data.getBytes("utf-8");
EthernetPacket ether=new EthernetPacket();
ether.frametype=EthernetPacket.ETHERTYPE_IP;
tcp.datalink=ether;
ether.src_mac=convertMacFormat(srcMac);
ether.dst_mac=convertMacFormat(dstMac);
if(ether.src_mac==null||ether.dst_mac==null) {
throw new Exception("MAC地址有误");
}
sender.sendPacket(tcp);
System.out.println("发包成功");
}catch(Exception e) {
System.err.println(e.getMessage());
throw new RuntimeException(e);
}

}
	
	public static void sedTCPPacket(JpcapSender sender,int srcPort,int dstPort,String srcHost,String dstHost,
									String data,String srcMac,String dstMac,boolean syn,boolean ack,boolean rst,boolean fin
			) {
		try {
		TCPPacket tcp=new TCPPacket(srcPort, dstPort, 56, 78, false, ack, false, rst, syn, fin, true, true, 200, 10);
		tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP	,
				InetAddress.getByName(srcHost),InetAddress.getByName(dstHost) );
		tcp.data=data.getBytes("utf-8");
		EthernetPacket ether=new EthernetPacket();
		ether.frametype=EthernetPacket.ETHERTYPE_IP;
		tcp.datalink=ether;
		ether.src_mac=convertMacFormat(srcMac);
		ether.dst_mac=convertMacFormat(dstMac);
		if(ether.src_mac==null||ether.dst_mac==null) {
			throw new Exception("MAC地址有误");
		}
		sender.sendPacket(tcp);
		System.out.println("发包成功");
		}catch(Exception e) {
			System.err.println(e.getMessage());
			throw new RuntimeException(e);
		}
		
	}
	
}

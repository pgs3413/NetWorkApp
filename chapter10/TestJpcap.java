package chapter10;

import java.io.UnsupportedEncodingException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class TestJpcap {
public static void main(String[] args) throws Exception {
	NetworkInterface[] devices = JpcapCaptor.getDeviceList();
	for(int i=0;i<devices.length;i++) {
		System.out.println(i+": "+devices[i].name+" "+devices[i].description);
		String mac="";
		for(byte b:devices[i].mac_address) {
			mac=mac+Integer.toHexString(b&0xff)+":";
		}
		System.out.println("MAC address:"+mac.substring(0, mac.length()-1));
		for(NetworkInterfaceAddress addr:devices[i].addresses) {
			System.out.println("address:"+addr.address+" "+addr.subnet+" "+addr.broadcast);
		}
	}
//	JpcapCaptor jpcapCaptor = JpcapCaptor.openDevice(devices[7], 1514, true, 100);
//	jpcapCaptor.setFilter("ip and tcp", true);
//	Packet packet = jpcapCaptor.getPacket();
//	System.out.println(packet);
//	jpcapCaptor.close();
//	while(true) {
//		jpcapCaptor.processPacket(-1, new PacketHandler());
//	}
//	
//	System.out.println("end");
}
}

class PacketHandler implements PacketReceiver{

	@Override
	public void receivePacket(Packet packet) {
		System.out.println(packet);
//		String msg=null;
//		try {
////			msg = new String(packet.data,0,packet.data.length,"utf-8");
//			msg=new String(packet.data);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(msg);
//		
	}
	
}

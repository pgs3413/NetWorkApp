package chapter12.homework;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.RmiKitService;

public class RmiKitServiceImpl extends UnicastRemoteObject implements RmiKitService{

	protected RmiKitServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public long ipToLong(String ip) throws RemoteException  {
		String[] ips=ip.split("\\.");
		long ipNum=0;
		for(int i=0;i<ips.length;i++) {
			ipNum+=(Long.valueOf(ips[i])<<8*(3-i));
		}
		return ipNum;
	}

	@Override
	public String longToIp(long ipNum) throws RemoteException {
		StringBuffer sb=new StringBuffer();
		sb.append(ipNum>>24);
		sb.append(".");
		sb.append((ipNum&0xffffff)>>16);
		sb.append(".");
		sb.append((ipNum&0xffff)>>8);
		sb.append(".");
		sb.append(ipNum&0x00ff);
		return sb.toString();
	}

	@Override
	public byte[] macStringToBytes(String macStr) throws RemoteException {
		String[] macList=macStr.split("[-:]"); 
		byte[] bytes=new byte[6];
		for(int i=0;i<macList.length;i++) {
			bytes[i]=(byte)Integer.parseInt(macList[i], 16);
		}
		return bytes;
	}

	@Override
	public String bytesToMACString(byte[] macBytes) throws RemoteException {
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<macBytes.length;i++) {
		sb.append(Integer.toHexString(macBytes[i]&0xff));
		if(i<5) sb.append("-");
		}
		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		try {
//			byte[] b=new RmiKitServiceImpl().macStringToBytes("B0-FC-36-4C-7E-CD");
//			String s=new RmiKitServiceImpl().bytesToMACString(b);
//			System.out.println(s);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}

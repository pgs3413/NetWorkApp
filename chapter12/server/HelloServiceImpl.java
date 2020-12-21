package chapter12.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import rmi.HelloService;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

	private String name;
	
	protected HelloServiceImpl() throws RemoteException {
		super();
	}

	protected HelloServiceImpl(String name) throws RemoteException {
		this.name=name;
	}

	
	@Override
	public String echo(String msg) throws RemoteException {
		System.out.println("服务器完成一些echo方法相关任务。。。。");
		return "echo:"+msg+"from "+name;
	}

	@Override
	public Date getTime() throws RemoteException {
		System.out.println("服务器完成一些getTime方法相关任务。。。。");
		return new Date();
	}

}

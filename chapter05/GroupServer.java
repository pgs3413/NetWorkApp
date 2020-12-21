package chapter05;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.net.ServerSocket;
import java.io.*;

public class GroupServer {
	private int port = 8008;
	private ServerSocket serverSocket=null;
	
	public GroupServer() throws IOException {
		serverSocket=new ServerSocket(port);
	}

	public void Service() {
		ExecutorService excutor = Executors.newCachedThreadPool();
		CopyOnWriteArrayList<Socket> list = new CopyOnWriteArrayList<Socket>();
		ConcurrentHashMap<String,Socket> map = new ConcurrentHashMap<String, Socket>();
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				list.add(socket);
				GroupThread r = new GroupThread(socket,list);
//			    ChatThread r = new ChatThread(socket,map);
//				BigThread r = new BigThread(socket, map, list);
				excutor.execute(r);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws IOException {
		new GroupServer().Service();
	}
}

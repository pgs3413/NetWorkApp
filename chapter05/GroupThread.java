package chapter05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GroupThread implements Runnable {

	private Socket socket=null;
	private CopyOnWriteArrayList<Socket> list=null;
	
	public GroupThread(Socket socket,CopyOnWriteArrayList<Socket> list) {
		this.socket=socket;
		this.list=list;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
	}
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		
	}	
	
	@Override
	public void run() {
	try {
		System.out.println("New connection accepted:"+socket.getInetAddress()+":"+socket.getPort());
		BufferedReader reader = getReader(socket);
		PrintWriter writer = getWriter(socket);
		writer.println("From服务器：欢迎使用本服务！");
		String msg=null;
		while(true) {
			msg=reader.readLine();
			if(msg.equals("bye")) {
				writer.println("From服务器:服务器断开连接，结束服务！");
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+"客户端离开");
				break;
			}
			sendToAll(msg);
		}
		list.remove(socket);
		socket.close();
	}catch(Exception e) {
		e.printStackTrace();
	}
		
	}
	
	private void sendToAll(String msg) throws Exception {
		Iterator<Socket> iterator = list.iterator();
		while(iterator.hasNext()) {
			Socket s = iterator.next();
			PrintWriter writer = getWriter(s);
			writer.println(socket.getInetAddress()+":"+socket.getPort()+"发言："+msg);
			
		}
	}


}

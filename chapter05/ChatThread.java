package chapter05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ChatThread implements Runnable {

	private Socket socket;
	private ConcurrentHashMap<String,Socket> map;
	
	public ChatThread(Socket socket,ConcurrentHashMap<String,Socket> map) {
		this.socket=socket;
		this.map=map;
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
		String myName=null;
		while(true) {
			writer.println("请输入您的昵称：");
			myName = reader.readLine();
			if(map.containsKey(myName)) {
				writer.println("昵称重复！");
			}else {
				map.put(myName, socket);
				break;
			}
		}
		String yourName=null;
		while(true) {
			writer.println("请输入对方的昵称：");
			yourName = reader.readLine();
			if(!map.containsKey(yourName)) {
				writer.println("该用户不存在！");
			}else {
				writer.println("连接成功！请向对方说话！");
				break;
			}
		}
		String msg=null;
		while(true) {
			msg=reader.readLine();
			if(msg.equals("bye")) {
				writer.println("From服务器:服务器断开连接，结束服务！");
				System.out.println(socket.getInetAddress()+":"+socket.getPort()+"客户端离开");
				break;
			}
			send(msg,yourName,myName);
		}
		map.remove(myName);
		socket.close();
		
	}catch(Exception e) {
		e.printStackTrace();
	}
		
	}
	
	private void send(String msg,String yourName,String myName) throws Exception {
		if(map.containsKey(yourName)) {
			Socket s = map.get(yourName);
			PrintWriter pw = getWriter(s);
			pw.println("来自"+myName+":"+msg);
		}else {
			getWriter(socket).println("对方已断开连接！");
		}
	}

}

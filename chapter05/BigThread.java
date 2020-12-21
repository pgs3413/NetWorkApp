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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BigThread implements Runnable {

	private Socket socket;
	private ConcurrentHashMap<String,Socket> map;
	private CopyOnWriteArrayList<Socket> list;
	private PrintWriter writer;
	
	public BigThread(Socket socket,ConcurrentHashMap<String,Socket> map,CopyOnWriteArrayList<Socket> list) {
		this.socket=socket;
		this.map=map;
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
			writer = getWriter(socket);
			writer.println("From服务器：欢迎使用本服务！");
			String myName=null;
			while(true) {
				writer.println("请输入您的学号：");
				myName = reader.readLine();
				if(map.containsKey(myName)) {
					writer.println("学号重复！");
				}else {
					map.put(myName, socket);
					writer.println("登录成功！");
					sendToAll(myName+"已上线！");
					break;
				}
			}
			
			writer.println("输入list查看在线用户！");
			String msg=null;
			msg = reader.readLine();
			if(msg.equals("list")) {
					show();
			}
			else {
				writer.println("请正确输入！");
			}
			while(true) {
				writer.println("输入1进入一对一模式！输入2进入群发模式！输入end退出模式！");
				msg = reader.readLine();
				if(msg.equals("1")) {
					String yourName=null;
					while(true) {
						writer.println("请输入对方的学号：");
						yourName = reader.readLine();
						if(!map.containsKey(yourName)) {
							writer.println("该用户不存在！");
						}else {
							writer.println("连接成功！请向对方说话！");
							break;
						}
					}
					while(true) {
						msg=reader.readLine();
						if(msg.equals("end")) {
							break;
						}
						send(msg,yourName,myName);
						
					}
					
				}
				else if(msg.equals("2")) {
					while(true) {
						msg=reader.readLine();
						if(msg.equals("end")) {
							break;
						}
						sendToAll("来自"+myName+"的信息:"+msg);
					}
					
				}
				else if(msg.equals("end")) {
					continue;
				}
				else if(msg.equals("bye")) {
					writer.println("From服务器:服务器断开连接，结束服务！");
					System.out.println(socket.getInetAddress()+":"+socket.getPort()+"客户端离开");
					break;
				}
				else {
					writer.println("输入有误！");
				}
			}
			list.remove(socket);
			map.remove(myName);
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
			writer.println(msg);
			
		}
	}
	private void show() {
		for(String name:map.keySet()) {
			writer.println(name+"在线");
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

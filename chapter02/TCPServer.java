package chapter02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class TCPServer {
	
	private int port = 8008;
	private ServerSocket serverSocket=null;
	
	public TCPServer() throws IOException {
		serverSocket=new ServerSocket(port);
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		
	}
	public void Service() {
		Socket socket=null;
		while(true) {
			try {
				socket=serverSocket.accept();
				System.out.println("New connection accepted:"+socket.getInetAddress());
				BufferedReader reader = getReader(socket);
				PrintWriter writer = getWriter(socket);
				writer.println("From服务器：欢迎使用本服务！");
				String msg=null;
				while(true) {
					msg=reader.readLine();
					if(msg.equals("bye")) {
						writer.println("From服务器:服务器断开连接，结束服务！");
						System.out.println("客户端离开");
						break;
					}
					writer.println("From服务器:"+msg);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(socket!=null) socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws IOException {
		new TCPServer().Service();
	}
	
}

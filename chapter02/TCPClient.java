package chapter02;

import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.TextArea;

import java.io.*;

public class TCPClient {
	private Socket socket=null;
	private PrintWriter writer=null;
	private BufferedReader reader=null;
	

	public TCPClient(String ip,String port) throws NumberFormatException, UnknownHostException, IOException {
		socket=new Socket(ip,Integer.parseInt(port));
		writer = getWriter(socket);
		reader=getReader(socket);
	}
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
	}
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
		
	}
	public void send(String msg) {
		writer.println(msg);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void receive(TextArea taDisplay) throws IOException {
		String msg=reader.readLine();
		if(msg!=null)
		taDisplay.appendText(msg+"\n");
	}
	public String receive2() throws IOException {
		String msg=reader.readLine();
		 return msg;
	}
	public void close() throws IOException {
		if(socket!=null) socket.close();
	}
	
}

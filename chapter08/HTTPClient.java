package chapter08;

import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.TextArea;

import java.io.*;

public class HTTPClient {
	private Socket socket=null;
	private PrintWriter writer=null;
	private BufferedReader reader=null;
	private OutputStream output;
	private InputStream input;
	

	public HTTPClient(String ip,String port) throws NumberFormatException, UnknownHostException, IOException {
		socket=new Socket(ip,Integer.parseInt(port));
		writer = getWriter(socket);
		reader=getReader(socket);
//		output=socket.getOutputStream();
//		input=socket.getInputStream();
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
	}
	public String  receive() throws IOException {
		String msg=reader.readLine();
		if(msg!=null) return msg;
		else return null;
//		byte[] buf=new byte[1024];
//		int len=input.read(buf);
//		if(len!=-1) {
//		String msg=new String(buf, 0, len, "utf-8");
//		taDisplay.appendText(msg);}
	}

	public void close() throws IOException {
		if(socket!=null) socket.close();
	}
	
}
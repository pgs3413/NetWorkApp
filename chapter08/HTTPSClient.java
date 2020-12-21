package chapter08;

import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javafx.scene.control.TextArea;

import java.io.*;

public class HTTPSClient {
	private SSLSocket socket=null;
	private SSLSocketFactory factory=null; 
	private PrintWriter writer=null;
	private BufferedReader reader=null;

//	static {
//		System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
//	}
	
	public HTTPSClient(String ip,String port) throws NumberFormatException, UnknownHostException, Exception {
		X509TrustManager x509m = new X509TrustManager() {			
			public X509Certificate[] getAcceptedIssuers() {return null;}			
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}		
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		};
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, new TrustManager[] {x509m}, new SecureRandom());
		factory = context.getSocketFactory();
		socket=(SSLSocket) factory.createSocket(ip, Integer.parseInt(port));
//		factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//		socket=(SSLSocket) factory.createSocket(ip, Integer.parseInt(port));
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
	}
	
	public String receive() throws IOException {
		String msg=reader.readLine();
		return msg;
//			System.out.println(msg);
	}

	public void close() throws IOException {
		if(socket!=null) socket.close();
	}
	
}
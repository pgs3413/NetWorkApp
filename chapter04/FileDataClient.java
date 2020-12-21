package chapter04;

import java.io.BufferedInputStream;
import java.io.File;

import java.io.FileOutputStream;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class FileDataClient {
	private Socket socket;
	private FileOutputStream writeFile;
	private PrintWriter writer;
	private BufferedInputStream reader;

	public FileDataClient(String ip,String port) throws Exception{
		socket = new Socket(ip,Integer.parseInt(port));
		InputStream inputStream = socket.getInputStream();
		OutputStream outputStream = socket.getOutputStream();
		writer=new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
		reader=new BufferedInputStream(inputStream);
	}
	
	public void getFile(File file,String fileName) throws Exception {
	writer.println(fileName);
	writeFile=new FileOutputStream(file);
	byte[] buf=new byte[1024];
	int len=0;
	while((len=reader.read(buf))!=-1) {
		writeFile.write(buf, 0, len);
	}
	writeFile.flush();
	socket.close();
	}
	
}

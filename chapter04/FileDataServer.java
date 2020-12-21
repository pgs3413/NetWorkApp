package chapter04;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.util.converter.ByteStringConverter;

public class FileDataServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(2020);
			while(true) {				
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
				OutputStream outputStream = socket.getOutputStream();
				BufferedOutputStream writer = new BufferedOutputStream(outputStream);
				String fileName = reader.readLine();
				System.out.println("要下载的文件为："+fileName);
				File file = new File("D:\\temp\\"+fileName);
				if(!file.exists()) {
					socket.close();
					serverSocket.close();
					continue;
				}
				FileInputStream readFile = new FileInputStream(file);
				byte[] buf=new byte[1024];
				int len=0;
				while((len=readFile.read(buf))!=-1){
					writer.write(buf, 0, len);
				}
				writer.flush();
				socket.close();
				System.out.println(fileName+"传输完毕！");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

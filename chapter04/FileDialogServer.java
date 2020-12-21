package chapter04;


import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

public class FileDialogServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(2021);
			Socket socket = serverSocket.accept();
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter writer=new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
			File filePath=new File("D:\\temp");
			String[] list = filePath.list();
			DecimalFormat formater= new DecimalFormat();
			formater.setMaximumFractionDigits(0);
			formater.setRoundingMode(RoundingMode.CEILING);
			
			for(String fileName:list) {
				File file = new File(filePath, fileName);
				if(file.isFile()) {
					writer.println(fileName+" "+formater.format(file.length()/1024.0)+"KB");
				}
			}
			while(true) {
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

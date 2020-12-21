package chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerTest {
public static void main(String[] args) throws IOException {
	ServerSocket serverSocket = new ServerSocket(7777);
	Socket socket = serverSocket.accept();
	InputStream inputStream = socket.getInputStream();
	OutputStream outputStream = socket.getOutputStream();
	PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream,"utf-8"),true);
	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
	Scanner scanner = new Scanner(System.in);
	writer.println("憨憨");
	while(true) {
		String line = reader.readLine();
		System.out.println(line);
		System.out.print("请输入");
		String str=scanner.next();
		writer.println(str);		
	}

}
}

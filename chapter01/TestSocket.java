package chapter01;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestSocket {
 public static void main(String[] args) throws Exception {
	Socket socket = new Socket("127.0.0.1",8008);
	System.out.println(1);
}
}

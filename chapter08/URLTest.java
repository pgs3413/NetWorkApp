package chapter08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class URLTest {
public static void main(String[] args) throws IOException {
	URL url=new URL("https://www.baidu.com");
	System.out.println(url.getProtocol());
//	InputStream in = url.openStream();
//	BufferedReader reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
//	while(true) {
//		String str = reader.readLine();
//		if(str==null) break;
//		System.out.println(str);
//	}
}
}

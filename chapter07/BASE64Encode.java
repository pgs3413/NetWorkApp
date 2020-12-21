package chapter07;

import java.util.Base64;
import java.util.Base64.Encoder;

public class BASE64Encode {
public static void main(String[] args) {
	String mail="2113114379@qq.com";
	String authCode="npsqfahfhytxdbcc";//授权码
	System.out.println(encode(mail));
	System.out.println(encode(authCode));
	
}
public static String encode(String msg) {
	Encoder encoder = Base64.getEncoder();
	String newMsg=encoder.encodeToString(msg.getBytes());
	return newMsg;
}
}

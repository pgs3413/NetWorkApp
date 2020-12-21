package chapter07;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailAPIClient {

	Session session;
	Store store;
	Transport transport;
	String receiveHost = "pop.qq.com";
	String sendHost = "smtp.qq.com";
	String receiveProtocol="pop3";
	
	public void init() throws Exception {
		Properties props=new Properties();
		props.put("mail.transport.protocol", "smtp");
	    props.put("mail.store.protocol", receiveProtocol);
	    props.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");
	    props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");
	    props.put("mail.smtp.host", sendHost);
	    session=Session.getDefaultInstance(props);
	    store=session.getStore(receiveProtocol);
	    store.connect(receiveHost,"2113114379@qq.com","npsqfahfhytxdbcc");
	    
	}
	
	public void sendMail() throws Exception {
		init();
		String fromAdrr="2113114379@qq.com";
		String toAdrr="2113114379@qq.com";
		MimeMessage message=new MimeMessage(session);
		message.setFrom(new InternetAddress(fromAdrr));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAdrr));
		message.setSubject("this is a xx mail");
		message.setText("hello");
		Transport.send(message,fromAdrr,"npsqfahfhytxdbcc");
	}
	
	public  void receiveMail() throws Exception {
		init();
		Folder folder=store.getFolder("inbox");
		if(folder==null) {
			System.out.println("文件夹错误！");
			return ;
		}
		folder.open(Folder.READ_ONLY);
		System.out.println("你的邮箱有："+folder.getMessageCount()+"封邮箱");
		System.out.println("你的邮箱有："+folder.getUnreadMessageCount()+"封未读邮箱");
//		for(int i=1;i<=folder.getMessages().length;i++) {
//			System.out.println("-----第"+i+"封邮箱----");
//			System.out.println("发送者是："+((InternetAddress[])folder.getMessage(i).getFrom())[0].getAddress());
//			System.out.println("标题为："+folder.getMessage(i).getSubject());
//			System.out.println("内容为："+folder.getMessage(i).getContent().toString());
//						
//		}
		System.out.println("-----第"+1+"封邮箱----");
		System.out.println("发送者是："+((InternetAddress[])folder.getMessage(1).getFrom())[0].getAddress());
		System.out.println("标题为："+folder.getMessage(1).getSubject());
		System.out.println("内容为："+folder.getMessage(1).getContent().toString());
		System.out.println("-----第"+2+"封邮箱----");
		System.out.println("发送者是："+((InternetAddress[])folder.getMessage(2).getFrom())[0].getAddress());
		System.out.println("标题为："+folder.getMessage(2).getSubject());
		System.out.println("内容为："+folder.getMessage(2).getContent().toString());
	}
	public static void main(String[] args) throws Exception {
//		new JavaMailAPIClient().sendMail();
	new JavaMailAPIClient().receiveMail();
	}
}

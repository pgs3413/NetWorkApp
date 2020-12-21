package chapter08;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public  class URLClientFX extends Application {
	private TextField tfIP=new TextField();
	private TextField tfPort=new TextField();
	private TextArea taDisplay=new TextArea();
	private TextField tfSend=new TextField();
	private Button btnConnect=new Button("连接");
	private Button btnSend=new Button("发送");
	private Button btnExit=new Button("退出");
	private Thread t1=null;
	BufferedReader reader;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		HBox hBox2 = new HBox();
		hBox.setSpacing(10);
		hBox2.setSpacing(10);
		vBox.setSpacing(10);
		hBox.setPadding(new Insets(10,20,10,20));
		hBox2.setPadding(new Insets(10,20,10,20));
		vBox.setPadding(new Insets(10,20,10,20));
		VBox.setVgrow(taDisplay,Priority.ALWAYS );
		hBox.setAlignment(Pos.CENTER);
		hBox2.setAlignment(Pos.CENTER_RIGHT);
		taDisplay.setEditable(false); 
		taDisplay.setWrapText(true);
		vBox.getChildren().addAll(new Label("网页信息显示区:"),taDisplay,new Label("输入URL地址:"),tfSend);
		hBox2.getChildren().addAll(btnSend,btnExit);
		
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox2);
		
		
		btnSend.setOnAction(e->{
			taDisplay.clear();
			URL url=null;
			String address = tfSend.getText().trim();
			try {
				url=new URL(address);
			}catch(Exception e1) {
				taDisplay.appendText("URL地址输入不合规则！");
			}
			try {
				
				
				if(url.getProtocol().equals("https")) {
					System.out.println(1);
					X509TrustManager x509m = new X509TrustManager() {			
						public X509Certificate[] getAcceptedIssuers() {return null;}			
						public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}		
						public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
					};	
					SSLContext context = SSLContext.getInstance("SSL");
					context.init(null, new TrustManager[] {x509m}, new SecureRandom());
					SSLSocketFactory factory = context.getSocketFactory();
					HttpsURLConnection conn=(HttpsURLConnection) url.openConnection();
					conn.setSSLSocketFactory(factory);
					InputStream in=conn.getInputStream();
					reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				}else {
					InputStream in = url.openStream();
					reader=new BufferedReader(new InputStreamReader(in,"utf-8"));
				}
					
				
				Thread t1=new Thread(new Runnable() {
					
					@Override
					public void run() {
					try {
						int count=0;
						while(count<=500) {
							count++;
							String str=reader.readLine();
							if(str!=null)
							Platform.runLater(()->{
								taDisplay.appendText(str+"\n");
							});
						}
					}catch(Exception e1) {
						e1.printStackTrace();
					}
						
					}
				});
				t1.start();
			} catch (Exception e1) {
				taDisplay.appendText("url请求失败！");
				System.out.println(3);
				e1.printStackTrace();
			}
		});
		
//		btnExit.setOnAction(e->{

//				);

		Scene scene = new Scene(borderPane,1000,600);
		primaryStage.setTitle("URL请求");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}

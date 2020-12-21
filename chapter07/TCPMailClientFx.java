package chapter07;

import java.io.IOException;
import java.net.UnknownHostException;

import chapter02.TCPClient;
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

public  class TCPMailClientFx extends Application {
	private TextField tfIP=new TextField();
	private TextField tfPort=new TextField();
	private TextArea taDisplay=new TextArea();
	private TextField sendAdd=new TextField();
	private TextField receiveAdd=new TextField();
	private TextField authCode =new TextField();
	private TextField title=new TextField();
	private Button btnSend=new Button("发送");
	private Button btnExit=new Button("退出");
	private TCPClient client=null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();
		VBox vBox = new VBox();
		HBox hBox = new HBox();
		HBox hBox2 = new HBox();
		HBox hBox3 = new HBox();
		HBox hBox4 = new HBox();
		HBox hBox5 = new HBox();
		hBox.setSpacing(10);
		hBox2.setSpacing(10);
		hBox3.setSpacing(10);
		hBox4.setSpacing(10);
		hBox5.setSpacing(10);
		vBox.setSpacing(10);
		hBox.setPadding(new Insets(10,20,10,20));
		hBox2.setPadding(new Insets(10,20,10,20));
		hBox3.setPadding(new Insets(10,20,10,20));
		hBox4.setPadding(new Insets(10,20,10,20));
		hBox5.setPadding(new Insets(10,20,10,20));
		vBox.setPadding(new Insets(10,20,10,20));
		VBox.setVgrow(taDisplay,Priority.ALWAYS );
		hBox.setAlignment(Pos.CENTER);
		hBox3.setAlignment(Pos.CENTER);
		hBox4.setAlignment(Pos.CENTER);
		hBox5.setAlignment(Pos.CENTER);
		hBox2.setAlignment(Pos.CENTER_RIGHT);
		taDisplay.setWrapText(true);
		hBox.getChildren().addAll(new Label("IP地址:"),tfIP,new Label("端口:"),tfPort);
		hBox3.getChildren().addAll(new Label("邮件发送地址："),sendAdd,new Label("邮件接收地址："),receiveAdd);
		hBox4.getChildren().addAll(new Label("邮件标题："),title);
		hBox5.getChildren().addAll(new Label("授权码："),authCode);
		vBox.getChildren().addAll(hBox,hBox3,hBox4,hBox5,new Label("内容："),taDisplay);
		hBox2.getChildren().addAll(btnSend,btnExit);
		
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox2);
		

		btnSend.setOnAction(e->{
			taDisplay.appendText("\n正在发送....\n");
			String ip = tfIP.getText().trim();
			String port = tfPort.getText().trim();
			String send = sendAdd.getText();
			String receive = receiveAdd.getText();
			String head = title.getText();
			String text = taDisplay.getText();
			String code = authCode.getText();
			String encode = BASE64Encode.encode(code);
			try {				
				client=new TCPClient(ip, port);
				Thread t1 = new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							while(true) {
								String s = client.receive2();
								if(s==null) break;
								System.out.println(s);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				});
				t1.start();
				Thread.sleep(1000);
				System.out.println("HELO hello");
				client.send("HELO hello");
				System.out.println("AUTH LOGIN");
				client.send("AUTH LOGIN");
				System.out.println("MjExMzExNDM3OUBxcS5jb20=");
				client.send("MjExMzExNDM3OUBxcS5jb20=");
				System.out.println(encode);
				client.send(encode);
				System.out.println("MAIL FROM:<"+send+">");
				client.send("MAIL FROM:<"+send+">");
				System.out.println("RCPT TO:<"+receive+">");
				client.send("RCPT TO:<"+receive+">");
				System.out.println("DATA");
				client.send("DATA");
				System.out.println("From:"+send);
				client.send("From:"+send);
				System.out.println("Subject:"+head);
				client.send("Subject:"+head);
				System.out.println("To:"+receive);
				client.send("To:"+receive);
				System.out.println("\n");
				client.send("\n");
				System.out.println(text);
				client.send(text);
				System.out.println(".");
				client.send(".");
				System.out.println("QUIT");
				client.send("QUIT");
				taDisplay.appendText("邮箱发送成功！");
				client.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}

		});
		btnExit.setOnAction(e->{
			System.exit(0);
		}		
				);
		Scene scene = new Scene(borderPane,900,500);
		primaryStage.setTitle("邮箱发送");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}

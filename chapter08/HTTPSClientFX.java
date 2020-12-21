package chapter08;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
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

public  class HTTPSClientFX extends Application {
	private TextField tfIP=new TextField();
	private TextField tfPort=new TextField();
	private TextArea taDisplay=new TextArea();
	private TextField tfSend=new TextField();
	private Button btnConnect=new Button("连接");
	private Button btnSend=new Button("网页请求");
	private Button btnClear=new Button("清空");
	private Button btnExit=new Button("退出");
	private HTTPSClient client=null;
	private Thread t1=null;

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
		hBox.getChildren().addAll(new Label("网页地址:"),tfIP,new Label("端口:"),tfPort,btnConnect);
		vBox.getChildren().addAll(hBox,new Label("网页信息显示区:"),taDisplay);
		hBox2.getChildren().addAll(btnSend,btnClear,btnExit);
		
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox2);
		
		btnSend.setDisable(true);
		btnExit.setDisable(true);
		
		btnConnect.setOnAction(e->{
			String ip=tfIP.getText().trim();
			String port=tfPort.getText().trim();

			try {
				client=new HTTPSClient(ip,port);
				t1=new Thread(new Runnable() {

					@Override
					public void run() {
						int count=0;
						try {
							while(count<=400) {
								String str = client.receive();
								if(str!=null)
								Platform.runLater(()->{
									taDisplay.appendText(str+"\n");
								});
								count++;
							}
							
						} catch (IOException e) {
							
							System.out.println(1);
							e.printStackTrace();
							
						}
						
					}
					
				});
				t1.start();
				btnSend.setDisable(false);
				btnExit.setDisable(false);
				btnConnect.setDisable(true);
			}catch(Exception e1) {
				taDisplay.appendText("服务器连接失败！"+e1.getMessage()+"\n");
			}
			
		});
		
		btnSend.setOnAction(e->{
			if(client==null) {
				System.out.println("client is null");
				return;
			}
			try {
//				client.send("GET / HTTP/1.1\r\n");
//				client.send("HOST:"+tfIP.getText().trim()+"\r\n");
//				client.send("Accept:*/*\r\n");
//				client.send("Accept-Language:zh-CN\r\n");
//				client.send("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36\r\n");
				StringBuffer msg = new StringBuffer();
				msg.append("GET / HTTP/1.1\r\n");
				msg.append("HOST:").append(tfIP.getText().trim()).append("\r\n");
				msg.append("Accept:*/*\r\n");
				msg.append("Accept-Language:zh-CN\r\n");
				msg.append("Connection: keep-alive\r\n");
				msg.append("User-Agent:java/1.6.0\r\n");
				msg.append("\r\n");
				client.send(msg.toString());
			}catch(Exception e1) {
				System.out.println("send错误！");
				e1.printStackTrace();
			}
			System.out.println("成功发送头文件");
		});
		btnClear.setOnAction(e->{
			taDisplay.clear();
		});
		
		btnExit.setOnAction(e->{
			t1.stop();
			try {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}
				client.close();
				client=null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
			/* System.exit(0); */
			btnSend.setDisable(true);
			btnExit.setDisable(true);
			btnConnect.setDisable(false);
		}
		
				);
		primaryStage.setOnCloseRequest(e->{
			if(client!=null) {
				t1.stop();
			
				
				try {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					client.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}			
			}
			}
);
		Scene scene = new Scene(borderPane,1000,700);
		primaryStage.setTitle("HTTPS客户端");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}


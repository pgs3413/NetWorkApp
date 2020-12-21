package chapter06;

import java.util.Scanner;

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

public  class UDPClientFx extends Application {
	private TextField tfIP=new TextField();
	private TextField tfPort=new TextField();
	private TextArea taDisplay=new TextArea();
	private TextField tfSend=new TextField();
	private Button btnConnect=new Button("连接");
	private Button btnSend=new Button("发送");
	private Button btnExit=new Button("退出");
	private Button btnHomeWork=new Button("交作业");
	private UDPClient client=null;
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
		hBox.getChildren().addAll(new Label("IP地址:"),tfIP,new Label("端口:"),tfPort,btnConnect);
		vBox.getChildren().addAll(hBox,new Label("信息显示区:"),taDisplay,new Label("信息输入区:"),tfSend);
		hBox2.getChildren().addAll(btnHomeWork,btnSend,btnExit);
		
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox2);
		
		btnSend.setDisable(true);
		btnExit.setDisable(true);
		
		btnConnect.setOnAction(e->{
			String ip=tfIP.getText().trim();
			String port=tfPort.getText().trim();

			try {
				client=new UDPClient(ip,port);
				t1=new Thread(new Runnable() {

					@Override
					public void run() {
						while(true) {
//							 if(Thread.currentThread().isInterrupted()) break; 
							String s=client.Receive();
							Platform.runLater(()->{
								taDisplay.appendText(s);
							});
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
			String msg=tfSend.getText().trim();
			taDisplay.appendText("From客户端："+msg+"\n");
			tfSend.clear();
			client.Send(msg);
		});
		btnHomeWork.setOnAction(e->{
			Scanner input=new Scanner(System.in);
			StringBuffer str=new StringBuffer();
			
			System.out.println("输入学号&姓名：");
			String s1=input.nextLine();
			str.append(s1+"\n");
			
			System.out.println("输入第一个包：");
			String s2=input.nextLine();
			str.append(s2+"\n");
			
			System.out.println("输入第二个包");
			String s3=input.nextLine();
			str.append(s3+"\n");
			
			System.out.println("输入第三个包");
			String s4=input.nextLine();
			str.append(s4);
			
			String msg=str.toString();
			
			System.out.println(msg);
			
			client.Send(msg);
		});
		
		
		btnExit.setOnAction(e->{
			t1.stop();
			//t1.interrupt();
			/*
			 * try { Thread.sleep(100); } catch (InterruptedException e1) {
			 * 
			 * e1.printStackTrace(); }
			 */
			client.Close();
			client=null;
			btnSend.setDisable(true);
			btnExit.setDisable(true);
			btnConnect.setDisable(false);
		}
		
				);
		
		primaryStage.setOnCloseRequest(e->{
			if(client!=null) {
				/*
				 * t1.interrupt();
				 * 
				 * try { Thread.sleep(100); } catch (InterruptedException e1) { // TODO
				 * Auto-generated catch block System.out.println(6); e1.printStackTrace(); }
				 */
				t1.stop();
				client.Close();			
			}
			}
);
		
		
		Scene scene = new Scene(borderPane,700,400);
		primaryStage.setTitle("UDP网络聊天");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}

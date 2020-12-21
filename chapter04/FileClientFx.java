package chapter04;

import java.io.File;
import java.io.IOException;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import chapter02.TCPClient;

public  class FileClientFx extends Application {
	private TextField tfIP=new TextField();
	private TextField tfPort=new TextField();
	private TextArea taDisplay=new TextArea();
	private TextField tfSend=new TextField();
	private Button btnConnect=new Button("连接");
	private Button btnSend=new Button("发送");
	private Button btnExit=new Button("退出");
	private Button btnDownload=new Button("下载");
	private TCPClient client=null;
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
		hBox2.getChildren().addAll(btnSend,btnDownload,btnExit);
		
		borderPane.setCenter(vBox);
		borderPane.setBottom(hBox2);
		
		btnSend.setDisable(true);
		btnExit.setDisable(true);
		
		btnConnect.setOnAction(e->{
			String ip=tfIP.getText().trim();
			String port=tfPort.getText().trim();

			try {
				client=new TCPClient(ip,port);
				t1=new Thread(new Runnable() {

					@Override
					public void run() {
						try {
							while(true) {
								 if(Thread.currentThread().isInterrupted()) break; 
								client.receive(taDisplay);
							}
							
						} catch (IOException e) {
							
							
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
			String msg=tfSend.getText().trim();
			taDisplay.appendText("From客户端："+msg+"\n");
			tfSend.clear();
			client.send(msg);
				try {
					if(msg.equals("bye")) {
						t1.interrupt();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							
							e1.printStackTrace();
						}
						client.close();
						client=null;
						btnSend.setDisable(true);
						btnExit.setDisable(true);
						btnConnect.setDisable(false);
						
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					
					e1.printStackTrace();
				}
		});
		btnExit.setOnAction(e->{
			client.send("bye");
			t1.interrupt();
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
				t1.interrupt();
				client.send("bye");
				
				try {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						System.out.println(6);
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
		btnDownload.setOnAction(e->{
			String fileName = tfSend.getText().trim();
			if(fileName.equals("")) return;
			FileChooser chooser = new FileChooser();
			chooser.setInitialFileName(fileName);
			File file = chooser.showSaveDialog(primaryStage);
			if(file==null) return;
			try {
				String ip=tfIP.getText().trim();
				FileDataClient fileDataClient = new FileDataClient(ip, "2020");
				fileDataClient.getFile(file, fileName);
				taDisplay.appendText("下载完成!");
				client.send("客户端开启下载");
			}catch(Exception e1) {
				taDisplay.appendText("下载失败");
			}
		});
		Scene scene = new Scene(borderPane,700,400);
		primaryStage.setTitle("文件传输器");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}


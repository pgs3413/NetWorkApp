package chapter09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class HostScannerFx extends Application {
private TextArea taDisplay=new TextArea();
private Button hostScan=new Button("主机扫描");
private Button execute=new Button("执行命令");
private TextField ipStart=new TextField();
private TextField ipEnd=new TextField();
private TextField command=new TextField();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane=new BorderPane();
		VBox vbox=new VBox();
		HBox hbox1=new HBox();
		HBox hbox2=new HBox();
		hbox1.getChildren().addAll(new Label("起始地址："),ipStart,new Label("结束地址："),ipEnd,hostScan);
		hbox2.getChildren().addAll(new Label("输入命令："),command,execute);
		vbox.getChildren().addAll(new Label("扫描结果："),taDisplay,hbox1,hbox2);
		pane.setCenter(vbox);
		hbox1.setSpacing(10);
		hbox2.setSpacing(10);
		vbox.setSpacing(10);
		hbox1.setPadding(new Insets(10,20,10,20));
		hbox2.setPadding(new Insets(10,20,10,20));
		vbox.setPadding(new Insets(10,20,10,20));
		VBox.setVgrow(taDisplay,Priority.ALWAYS );
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		HBox.setHgrow(command, Priority.ALWAYS);
		Scene scene=new Scene(pane,1000,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("主机扫描");
		primaryStage.show();
		
		hostScan.setOnAction(e->{
			String start = ipStart.getText().trim();
			String end = ipEnd.getText().trim();
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					int s = Trans.ipToInt(start);
					int e = Trans.ipToInt(end);
					for(int i=s;i<=e;i++) {
						String ip = Trans.intToIp(i);
						try {
							InetAddress address = InetAddress.getByName(ip);
							boolean b = address.isReachable(200);
							if(b==true) {
								Platform.runLater(()->{
									taDisplay.appendText(ip+" is reachable.\n");
								});
							}else {
								Platform.runLater(()->{
									taDisplay.appendText(ip+" is not reachable.\n");
								});
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					Platform.runLater(()->{
						taDisplay.appendText("扫描完毕！\n");
					});
				}
			});
			t.start();
			
		});
		
		execute.setOnAction(e->{
			String c = command.getText().trim();
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						Process process = Runtime.getRuntime().exec(c);
						InputStream in = process.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(in,"gbk"));
						String msg;
						while((msg=reader.readLine())!=null) {
							String s=msg;
							Platform.runLater(()->{
								taDisplay.appendText(s+"\n");
							});
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						Platform.runLater(()->{
							taDisplay.appendText("命令执行错误\n");
						});
					}
					
				}
			});
			t.start();
			
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}



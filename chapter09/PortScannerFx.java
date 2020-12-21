package chapter09;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
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

public class PortScannerFx extends Application {

	private TextArea taDisplay=new TextArea();
	private TextField targetHost=new TextField();
	private TextField startPort =new TextField();
	private TextField endPort=new TextField();
	private Button btnScan=new Button("扫描");
	private Button btnEXit=new Button("退出");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane=new BorderPane();
		VBox vbox=new VBox();
		HBox hbox1=new HBox();
		HBox hbox2=new HBox();
		hbox1.getChildren().addAll(new Label("目标主机："),targetHost,new Label("起始端口号："),startPort,new Label("结束端口号："),endPort);
		hbox2.getChildren().addAll(btnScan,btnEXit);
		vbox.getChildren().addAll(new Label("端口扫描结果："),taDisplay,hbox1,hbox2);
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
		Scene scene=new Scene(pane,1000,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("主机扫描");
		primaryStage.show();
		
		btnScan.setOnAction(e->{
			String ip = targetHost.getText().trim();
			final int startP = Integer.parseInt(startPort.getText().trim());
			final int endP = Integer.parseInt(endPort.getText().trim());
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					ExecutorService service = Executors.newCachedThreadPool();
					for(int i=startP;i<=endP;i++) {
						service.execute(new PortScannerThread(ip, i, taDisplay));
					}
					service.shutdown();
					while(service.isTerminated()!=true) {
						
					}
					Platform.runLater(()->{
						taDisplay.appendText(" 端口扫描完毕！\n");
					});
					
				}
			});
			t.start();
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

class PortScannerThread implements Runnable{

	String ip;
	int port;
	TextArea taDisplay;
	
	public PortScannerThread(String ip,int port,TextArea taDisplay) {
		this.ip=ip;
		this.port=port;
		this.taDisplay=taDisplay;
	}
	
	@Override
	public void run() {
		Socket s = new Socket();
		try {
			s.connect(new InetSocketAddress(ip, port),200);
			s.close();
			taDisplay.appendText("端口"+port+"is open!\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}
	
}

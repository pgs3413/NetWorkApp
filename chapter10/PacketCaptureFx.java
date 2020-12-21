package chapter10;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class PacketCaptureFx extends Application {

	private TextArea taDisplay =new TextArea();
	private Button btnStart=new Button("开始抓包");
	private Button btnStop=new Button("停止抓包");
	private Button btnClear=new Button("清空");
	private Button btnOpenConfigDialog=new Button("设置");
	private Button btnExit=new Button("退出");
	private ConfigDialog config;
	private JpcapCaptor captor;
	private Thread t;
	private String data;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox vbox=new VBox();
		HBox hbox=new HBox();
		hbox.getChildren().addAll(btnStart,btnStop,btnClear,btnOpenConfigDialog,btnExit);
		vbox.getChildren().addAll(new Label("抓包信息："),taDisplay,hbox);
		VBox.setVgrow(taDisplay, Priority.ALWAYS);
		vbox.setSpacing(10);
		hbox.setSpacing(10);
		vbox.setPadding(new Insets(10,10,10,10));
		hbox.setPadding(new Insets(10,10,10,10));
		hbox.setAlignment(Pos.CENTER);
		BorderPane pane=new BorderPane();
		pane.setCenter(vbox);
		Scene scene=new Scene(pane,1000,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setTitle("网络抓包");
		
		config=new ConfigDialog(primaryStage);
		captor=config.getCaptor();
		data=config.getData();
		
		btnOpenConfigDialog.setOnAction(e->{
			config.show();
			data=config.getData();
			captor=config.getCaptor();
		});
		
		btnStart.setOnAction(e->{
		
		t=new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("线程开始");
			 while(true) {
				 if(Thread.currentThread().isInterrupted()) break;
				 captor.processPacket(-1,new PacketHander(taDisplay,data));
			 }
				System.out.println("线程结束");
			}
			
		});
			
		t.start();
				
		});
		
		btnStop.setOnAction(e->{
			t.interrupt();
		});
		
		btnClear.setOnAction(e->{
			taDisplay.clear();
		});
		
		btnExit.setOnAction(e->{
			System.exit(0);
		});
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}

class PacketHander implements PacketReceiver{

	private TextArea taDisplay;
	private String msg;
	
	
	PacketHander(TextArea taDisplay){
		this.taDisplay=taDisplay;
	}
	PacketHander(TextArea taDisplay,String msg){
		this.taDisplay=taDisplay;
		this.msg=msg;
	}
	
	@Override
	public void receivePacket(Packet packet) {
		if(msg==null||msg.equalsIgnoreCase("")) {
			Platform.runLater(()->{
				taDisplay.appendText(packet+"\n");
			});
		}else {
			try {
				String[] keyList=msg.split("\\s+");
				String packetMsg=new String(packet.data,0,packet.data.length,"utf-8");
				for(String key:keyList) {
					if(packetMsg.toLowerCase().contains(key.toLowerCase())) {
						Platform.runLater(()->{
							taDisplay.appendText(packetMsg+"\n");
						});
					}
				}
			}catch(Exception e) {
				
				System.err.println(e.getMessage());
			}
		}

		
	}
	
}

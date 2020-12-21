package chapter11;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jpcap.JpcapSender;

public class SendPacketFx extends Application {
private TextField tfSrcPort=new TextField("8000");
private TextField tfDstPort=new TextField();
private TextField tfSrcHost=new TextField("127.0.0.1");
private TextField tfDstHost=new TextField();
private TextField tfSrcMac=new TextField("B0-FC-36-4C-7E-CD");
private TextField tfDstMac=new TextField();
private TextField tfData=new TextField();
private CheckBox cbSYN=new CheckBox("SYN");
private CheckBox cbACK=new CheckBox("ACK");
private CheckBox cbRST=new CheckBox("RST");
private CheckBox cbFIN=new CheckBox("FIN");
private Button btnSend=new Button("发送TCP包");
private Button btnChoose=new Button("选择网卡");
private Button btnExit=new Button("退出");
private VBox vbox=new VBox();
private HBox hbox1=new HBox();
private HBox hbox2=new HBox();
private HBox hbox3=new HBox();
private NetworkChoiceDialog dialog;
private JpcapSender sender;
@Override
public void start(Stage primaryStage) throws Exception {
	BorderPane pane =new BorderPane();
	hbox1.getChildren().addAll(new Label("源端口:"),tfSrcPort,new Label("目的端口:"),tfDstPort);
	hbox2.getChildren().addAll(new Label("TCP标识位:"),cbSYN,cbACK,cbRST,cbFIN);
	hbox3.getChildren().addAll(btnSend,btnChoose,btnExit);
	vbox.getChildren().addAll(hbox1,hbox2,new Label("源主机地址"),tfSrcHost,new Label("目的主机地址"),tfDstHost,
			new Label("源MAC地址"),tfSrcMac,new Label("目的MAC地址"),tfDstMac,new Label("要发送的数据"),tfData,hbox3);
	pane.setCenter(vbox);
	vbox.setSpacing(10);
	hbox1.setSpacing(10);
	hbox2.setSpacing(10);
	hbox3.setSpacing(10);
	vbox.setPadding(new Insets(10,10,10,10));
	hbox1.setPadding(new Insets(10,10,10,10));
	hbox2.setPadding(new Insets(10,10,10,10));
	hbox3.setPadding(new Insets(10,10,10,10));
	hbox1.setAlignment(Pos.CENTER);
	hbox2.setAlignment(Pos.CENTER);
	hbox3.setAlignment(Pos.CENTER);
	cbSYN.setSelected(true);
	Scene scene=new Scene(pane,600,600);
	primaryStage.setScene(scene);
	primaryStage.setTitle("发送自构包");
	dialog=new NetworkChoiceDialog(primaryStage);
	dialog.show();
	sender=dialog.getSender();
	primaryStage.show();
	
	btnSend.setOnAction(e->{
		try {
			int srcPort=Integer.parseInt(tfSrcPort.getText().trim());
			int dstPort=Integer.parseInt(tfDstPort.getText().trim());
			String srcHost=tfSrcHost.getText().trim();
			String dstHost=tfDstHost.getText().trim();
			String srcMac=tfSrcMac.getText().trim();
			String dstMac=tfDstMac.getText().trim();
			String data=tfData.getText();
			PacketSender.sedTCPPacket(sender, srcPort, dstPort, srcHost, dstHost, data, srcMac, dstMac);
			new Alert(Alert.AlertType.INFORMATION,"已发送！").showAndWait();
		}catch(Exception e1) {
			new Alert(Alert.AlertType.ERROR,e1.getMessage()).showAndWait();
		}
	});
	
	btnExit.setOnAction(e->{
		System.exit(0);
	});
	
	btnChoose.setOnAction(e->{
		dialog.show();
		sender=dialog.getSender();
	});
}
public static void main(String[] args) {
	launch(args);
}
}

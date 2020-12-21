package chapter10;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

public class ConfigDialog {
	
	private JpcapCaptor captor;
	private NetworkInterface[] devices=JpcapCaptor.getDeviceList();
	private Stage stage=new Stage();
	private String data;
	
	ConfigDialog(Stage parentStage){
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.setTitle("选择网卡并设置参数");
		VBox vbox=new VBox();
		ComboBox<String> cb=new ComboBox<String>();
		for(int i=0;i<devices.length;i++) {
			cb.getItems().add(i+":"+devices[i].description);
		}
		cb.getSelectionModel().select(6);
		cb.setMaxWidth(400);
		TextField tfFilter=new TextField("ip and tcp");
		TextField tfSize=new TextField("1514");
		TextField tfData=new TextField("服务器");
		Button btnConfirm=new Button("确定");
		Button btnCancel=new Button("取消");
		HBox hbox=new HBox();
		hbox.getChildren().addAll(btnConfirm,btnCancel);
		CheckBox c=new CheckBox("是否为混杂模式");
		c.setSelected(true);
		vbox.getChildren().addAll(new Label("请选择网卡:"),cb,new Label("设置抓包过滤器(例如 ip and tcp):"),tfFilter,
				new Label("设置抓包大小:"),tfSize,new Label("包中包含的关键字，以空格隔开："),tfData,c,new Separator(),hbox);
		hbox.setSpacing(10);
		vbox.setSpacing(10);
		hbox.setPadding(new Insets(10,10,10,10));
		vbox.setPadding(new Insets(10,10,10,10));
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		Scene scene=new Scene(vbox,500,400);
		stage.setScene(scene);
		
		int index = cb.getSelectionModel().getSelectedIndex();
		NetworkInterface networkInterface = devices[index];
		int snaplen=Integer.parseInt(tfSize.getText().trim());
		boolean b = c.isSelected();
		data=tfData.getText().trim();
//		System.out.println(index);
//		System.out.println(snaplen);
//		System.out.println(b);
//		System.out.println(tfSize.getText().trim());
//		System.out.println(tfFilter.getText());
		try {
			captor=JpcapCaptor.openDevice(networkInterface, snaplen, b, 500);
			captor.setFilter(tfFilter.getText(),true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		btnConfirm.setOnAction(e->{

			try {
				data=tfData.getText().trim();
				int index1 = cb.getSelectionModel().getSelectedIndex();
				NetworkInterface networkInterface1 = devices[index1];
				int snaplen1=Integer.parseInt(tfSize.getText().trim());
				boolean b1 = c.isSelected();
				captor=JpcapCaptor.openDevice(networkInterface1, snaplen1, b1, 200);
				captor.setFilter(tfFilter.getText(),true);
				stage.hide();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				new Alert(Alert.AlertType.ERROR,e1.getMessage()).showAndWait();
			}
		});
		
		btnCancel.setOnAction(e->{
			stage.hide();
		});
	}
	
	public JpcapCaptor getCaptor() {
		return captor;
	}
	public String getData() {
		return data;
	}
	
	public void show() {
		stage.showAndWait();
	}
	
}

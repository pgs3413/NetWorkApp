package chapter11;

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
import jpcap.JpcapSender;
import jpcap.NetworkInterface;

public class NetworkChoiceDialog {
	
	private JpcapSender sender;
	private NetworkInterface[] devices=JpcapCaptor.getDeviceList();
	private Stage stage=new Stage();
	
	NetworkChoiceDialog(Stage parentStage){
		stage.initOwner(parentStage);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setResizable(false);
		stage.setTitle("选择网卡");
		VBox vbox=new VBox();
		ComboBox<String> cb=new ComboBox<String>();
		for(int i=0;i<devices.length;i++) {
			cb.getItems().add(i+":"+devices[i].description);
		}
		cb.getSelectionModel().select(6);
		cb.setMaxWidth(400);
		Button btnConfirm=new Button("确定");
		HBox hbox=new HBox();
		hbox.getChildren().addAll(btnConfirm);
		vbox.getChildren().addAll(new Label("请选择网卡:"),cb,new Separator(),hbox);
		hbox.setSpacing(10);
		vbox.setSpacing(10);
		hbox.setPadding(new Insets(10,10,10,10));
		vbox.setPadding(new Insets(10,10,10,10));
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		Scene scene=new Scene(vbox,500,150);
		stage.setScene(scene);
		
		int index = cb.getSelectionModel().getSelectedIndex();
		NetworkInterface networkInterface = devices[index];
		try {
			sender=JpcapSender.openDevice(networkInterface);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		btnConfirm.setOnAction(e->{

			try {
				int i = cb.getSelectionModel().getSelectedIndex();
				NetworkInterface networkInterface1 = devices[i];
				sender=JpcapSender.openDevice(networkInterface1);
				stage.hide();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				new Alert(Alert.AlertType.ERROR,e1.getMessage()).showAndWait();
			}
		});
	}
		
	public JpcapSender getSender() {
		return sender;
	}
	
	public void show() {
		stage.showAndWait();
	}
	
}

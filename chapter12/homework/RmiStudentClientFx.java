package chapter12.homework;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rmi.HelloService;
import rmi.RmiMsgService;

public class RmiStudentClientFx extends Application {
	private TextArea taDisplay = new TextArea();
	  private TextField tfMessage = new TextField();
	  private TextField tfNO = new TextField();
	  private TextField tfName = new TextField();
	  Button btnSendMsg = new Button("发送信息");
	  Button btnSendNoAndName = new Button("发送学号和姓名");
	  private RmiMsgService rmiMsgService;
	 public static void main(String[] args) {
		launch(args);
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {		
	    VBox vBoxMain = new VBox();
	    vBoxMain.setSpacing(10);
	    vBoxMain.setPadding(new Insets(10, 20, 10, 20));
	    HBox hBox = new HBox();
	    hBox.setSpacing(10);
	    hBox.setPadding(new Insets(10, 20, 10, 20));
	    hBox.setAlignment(Pos.CENTER);
	    hBox.getChildren().addAll(new Label("输入信息："),tfMessage,btnSendMsg,new Label("学号："),tfNO,
	        new Label("姓名："),tfName,btnSendNoAndName);
	    vBoxMain.getChildren().addAll(new Label("信息显示区："),
	        taDisplay,hBox);
	    Scene scene = new Scene(vBoxMain);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    new Thread(()->{rmiInit();}).start();
	    btnSendMsg.setOnAction(event -> {
	        try {
	          String msg = tfMessage.getText();        
	          taDisplay.appendText(rmiMsgService.send(msg) + "\n");
	        } catch (RemoteException e) {
	          e.printStackTrace();
	        }
	      });
	    btnSendNoAndName.setOnAction(event -> {
	        try {
	        	String no=tfNO.getText();
	        	String name=tfName.getText();
	        	String msg = rmiMsgService.send(no, name);
	          taDisplay.appendText(msg + "\n");
	        } catch (RemoteException e) {
	          e.printStackTrace();
	        }
	      });

		
	}
	public void rmiInit() {
		try {
			Registry registry = LocateRegistry.getRegistry("",1099);
			System.out.println("RMI远程服务别名列表：");
			for(String name:registry.list()) {
				System.out.println(name);
			}
			rmiMsgService=(RmiMsgService)registry.lookup("RmiMsgService");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

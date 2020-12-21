package chapter12.client;

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

public class HelloClientFx extends Application {
	private TextArea taDisplay=new TextArea();
	private TextField tfMessage = new TextField();
	private Button btnEcho = new Button("调用echo方法");
	private Button btnGetTime = new Button("调用getTime方法");
	private HelloService helloService; 
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
	    hBox.getChildren().addAll(new Label("输入信息："),tfMessage,
	        btnEcho,btnGetTime);
	    vBoxMain.getChildren().addAll(new Label("信息显示区："),
	        taDisplay,hBox);
	    Scene scene = new Scene(vBoxMain);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    new Thread(()->{rmiInit();}).start();
	    btnEcho.setOnAction(event -> {
	        try {
	          String msg = tfMessage.getText();        
	          taDisplay.appendText(helloService.echo(msg) + "\n");
	        } catch (RemoteException e) {
	          e.printStackTrace();
	        }
	      });
	    btnGetTime.setOnAction(event -> {
	        try {
	          String msg = helloService.getTime().toString();
	          taDisplay.appendText(msg + "\n");
	        } catch (RemoteException e) {
	          e.printStackTrace();
	        }
	      });

		
	}
	public void rmiInit() {
		try {
			Registry registry = LocateRegistry.getRegistry("127.0.0.1",1099);
			System.out.println("RMI远程服务别名列表：");
			for(String name:registry.list()) {
				System.out.println(name);
			}
			//生成一个代理类
			helloService=(HelloService)registry.lookup("helloService");			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

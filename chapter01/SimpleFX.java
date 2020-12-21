package chapter01;


import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

public class SimpleFX extends Application {
	private Button btnExit=new Button("退出");
	private Button btnSend=new Button("发送");
	private Button btnOpen=new Button("加载");
	private Button btnSave=new Button("保存");
	private Button btnClear=new Button("清空");
	private TextField tfSend=new TextField();
	private TextArea taDisplay=new TextArea();
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		TextFileIO textFileIO = new TextFileIO(primaryStage);
		BorderPane mainPane = new BorderPane();
		VBox vbox=new VBox();
		HBox hbox = new HBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10,20,10,20));
		VBox.setVgrow(taDisplay,Priority.ALWAYS );
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(10,20,10,20));
		hbox.setAlignment(Pos.CENTER_RIGHT);
		taDisplay.setEditable(false); 
		taDisplay.setWrapText(true);
		
		vbox.getChildren().addAll(new Label("信息显示区:"),taDisplay,new Label("信息输入区:"),tfSend);
		hbox.getChildren().addAll(btnClear,btnSend,btnSave,btnOpen,btnExit);
		

		mainPane.setCenter(vbox);
		mainPane.setBottom(hbox);
		
		btnClear.setOnAction(e->{
			taDisplay.clear();
		});
		btnExit.setOnAction(event -> {
			System.exit(0);
		});
		btnSend.setOnAction(event -> {
			String text = tfSend.getText();
			taDisplay.appendText(text);
			tfSend.clear();
		});
		tfSend.setOnKeyPressed(e -> {
			

			if(e.isShiftDown()) {
				if(e.getCode()==KeyCode.ENTER) {
					String text = tfSend.getText();
					taDisplay.appendText("echo:"+text);
					tfSend.clear();
				}
			}
			switch(e.getCode()){
			case ENTER:String text = tfSend.getText();taDisplay.appendText(text);tfSend.clear();break;
			default:break;
				
			}
		});
		btnSave.setOnAction(e->{
			textFileIO.append(LocalDateTime.now().withNano(0)+":"+taDisplay.getText());
		});
		btnOpen.setOnAction(e->{
			String str = textFileIO.load();
			if(str!=null) {
				taDisplay.clear();
				taDisplay.setText(str);
			}
		});
		
		Scene scene = new Scene(mainPane,700,400);
		primaryStage.setTitle("NetworkApp");
		primaryStage.setScene(scene);
		primaryStage.show();
		tfSend.requestFocus();
	}
public static void main(String[] args) {
	launch(args);
}
}

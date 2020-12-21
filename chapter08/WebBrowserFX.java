package chapter08;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebBrowserFX extends Application {
	private Button btnFlush=new Button("刷新");
	private Button btnForward=new Button("-->");
	private Button btnBack=new Button("<--");
	private Button btnFirst=new Button("首页");
	private Button btnJump=new Button("跳转");
	private TextField tfUrl=new TextField();
//	private TextArea taDisplay=new TextArea();
	WebView view=new WebView();
	WebEngine engine;
	
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane =new BorderPane();
		HBox hBox = new HBox();
		VBox vBox = new VBox();
		hBox.getChildren().addAll(btnFlush,btnBack,btnForward,tfUrl,btnJump);
		vBox.getChildren().addAll(hBox,view);
		hBox.setSpacing(10);
		vBox.setSpacing(10);
		hBox.setPadding(new Insets(10,20,10,20));
		vBox.setPadding(new Insets(10,20,10,20));
		VBox.setVgrow(view,Priority.ALWAYS );
		HBox.setHgrow(tfUrl, Priority.ALWAYS);
		pane.setCenter(vBox);
		Scene scene=new Scene(pane, 1200, 900);
		primaryStage.setTitle("WebBrowserFx");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//btnForward.setDisable(true);
		//btnBack.setDisable(true);
		
		X509TrustManager x509m = new X509TrustManager() {			
			public X509Certificate[] getAcceptedIssuers() {return null;}			
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}		
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}
		};
		SSLContext context = SSLContext.getInstance("SSL");
		context.init(null, new TrustManager[] {x509m}, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		
		
		engine = view.getEngine();
		engine.getLoadWorker().stateProperty().addListener((observable,oldValue,newValue)->{
			if(newValue==State.SUCCEEDED) {
				System.out.println("finished loading"+" "+engine.getLocation());
				tfUrl.clear();
				tfUrl.appendText(engine.getLocation());
			}
		});
		
		WebHistory history=engine.getHistory();
		
		btnJump.setOnAction(e->{
			String url=tfUrl.getText().trim();
			System.out.println(url);			
			engine.load(url);
//			engine.loadContent("<html><body>hello</body></html>");
		});
		
		btnForward.setOnAction(e->{
			int size = history.getEntries().size();
			if(history.getCurrentIndex()!=size-1)
			history.go(1);
		});
		btnBack.setOnAction(e->{
			if(history.getCurrentIndex()!=0)
			history.go(-1);
		});
		btnFlush.setOnAction(e->{
			String url=tfUrl.getText().trim();
			engine.load(url);
		});
	}
	public static void main(String[] args) {
		launch(args);
	}

}

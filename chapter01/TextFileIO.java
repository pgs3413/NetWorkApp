package chapter01;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class TextFileIO {
private Scanner reader=null; 
private PrintWriter writer=null;
private Stage stage=null;

public TextFileIO(Stage stage) {
	this.stage=stage;
}

public void append(String msg) {
	FileChooser fileChooser = new FileChooser();
	File file=fileChooser.showSaveDialog(stage);
	if(file==null) return;
	try {
		writer=new PrintWriter(new FileOutputStream(file, true));
		writer.println(msg);
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		writer.close();
	}
	
}

public String load() {
	FileChooser fileChooser = new FileChooser();
	File file=fileChooser.showOpenDialog(stage);
	if(file==null) return null;
	StringBuilder stringBuilder = new StringBuilder();
	
	try {
		reader =new Scanner(file);
		while(reader.hasNext()) {
			String str = reader.nextLine();
			stringBuilder.append(str+"\n");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		reader.close();
	}
	return stringBuilder.toString();
}
	
	
}

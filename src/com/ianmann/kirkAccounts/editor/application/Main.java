package com.ianmann.kirkAccounts.editor.application;
	
import java.io.IOException;

import javax.swing.JOptionPane;

import com.ianmann.kirkAccounts.editor.frontend.wrappers.Base;
import com.ianmann.kirkAccounts.errors.CorruptFileException;
import com.ianmann.kirkAccounts.errors.WrongFileTypeException;
import com.ianmann.kirkAccounts.files.AccountFile;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	public static final String UNLOCK_KEY = "saline54";
	public static String[] args;
	
	public static AccountFile openFile;
	public static BorderPane root;
	public static Stage primaryStage;
	
	/**
	 * @return the openFile
	 */
	public static AccountFile getOpenFile() {
		return openFile;
	}

	/**
	 * @param openFile the openFile to set
	 */
	private static void setOpenFile(AccountFile openFile) {
		Main.openFile = openFile;
	}

	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("Kirk Accounts");
		Image stageIcon = new Image(this.getClass().getResourceAsStream("/com/ianmann/kirkAccounts/editor/resources/images/kirkAccountsLogo.png"));
		Main.primaryStage.getIcons().add(stageIcon);
		
		if (Main.args.length > 0) {
			try {
				Main.openFile(args[0]);
			} catch(CorruptFileException e) {
				e.printStackTrace();
			} catch(WrongFileTypeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Main.root = new Base().value;
			Scene scene = new Scene(Main.root, Main.root.getMinWidth(), Main.root.getMinHeight());
			Main.primaryStage.setScene(scene);
			Main.primaryStage.show();
			Main.primaryStage.sizeToScene();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		validate();
		Main.args = args;
		
		launch(args);
	}
	
	public static void openFile(String _filePath) throws IOException {
		Main.setOpenFile(AccountFile.readAccountFile(_filePath));
		Main.primaryStage.setTitle(_filePath + " - Kirk Accounts");
	}
	
	/**
	 * <p>This editor cannot be accessed without knowing the
	 * key code. If the key is not correct, the program
	 * will quit.</p>
	 * 
	 * TODO:<br>
	 * - After 3 invalidations, do something like lock
	 * the program.
	 */
	public static void validate() {
		String usersPassword = JOptionPane.showInputDialog("This editor is password protected.\nPlease enter the correct password.");
		if (usersPassword.equals(Main.UNLOCK_KEY)) {
			return;
		} else {
			JOptionPane.showMessageDialog(null, "Sorry, that is not the correct password.");
			System.exit(-1);
		}
	}
}

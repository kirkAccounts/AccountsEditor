package com.ianmann.kirkAccounts.editor.frontend.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.ianmann.kirkAccounts.Account;
import com.ianmann.kirkAccounts.editor.application.Main;
import com.ianmann.kirkAccounts.editor.frontend.wrappers.FileEditor;
import com.ianmann.kirkAccounts.errors.CorruptFileException;
import com.ianmann.kirkAccounts.errors.WrongFileTypeException;
import com.ianmann.kirkAccounts.files.AccountFile;

import iansLibrary.security.Encriptions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;;

public class BaseController implements Initializable {
	
	@FXML
	private MenuItem newAccountLink;
	
	@FXML
	private MenuItem saveLink;
	
	@FXML
	private MenuItem openLink;
	
	@FXML
	private MenuItem saveAsLink;
	
	@FXML
	private AnchorPane editorView;
	
	@FXML
	private void newAccountRequested() {
		/*
		 * Open prompt to create a new Account
		 * and show file explorer to prompt user
		 * to choose where to save the file.
		 */
		
		FileChooser fileChoose = new FileChooser();
		fileChoose.setTitle("New Account File");
		ExtensionFilter acctFileFilter = new ExtensionFilter("Account Files (*.acct)", "*.acct");
		fileChoose.setSelectedExtensionFilter(acctFileFilter);
		File newFile = fileChoose.showSaveDialog(Main.primaryStage);
		if (newFile == null) {
			return;
		}
		
		/*
		 * Create the new Account file.
		 */
		AccountFile acctFile = null;
		try {
			acctFile = AccountFile.newAccount(newFile.getAbsolutePath());
			
			try {
				Main.openFile(acctFile.getFilePath());
				this.editorView.getChildren().clear();
				this.editorView.getChildren().add(new FileEditor(Main.getOpenFile()).value);
				this.saveLink.setDisable(false);
				this.saveAsLink.setDisable(false);
			} catch (IOException e) {
				/*
				 * display an error message that says you can't open this file.
				 */
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		} catch (FileAlreadyExistsException e1) {
			// TODO Auto-generated catch block
			try {
				Main.openFile(newFile.getAbsolutePath());
				Main.getOpenFile().setAccount(Account.nullAccount());
				this.editorView.getChildren().clear();
				this.editorView.getChildren().add(new FileEditor(Main.getOpenFile()).value);
				this.saveLink.setDisable(false);
				this.saveAsLink.setDisable(false);
			} catch (IOException e) {
				/*
				 * display an error message that says you can't open this file.
				 */
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
	}

	@FXML
	private void saveRequested() throws IOException {
		/*
		 * Save the account to the file.
		 */
		String decriptedUsername = ((TextField) this.editorView.getChildren()
										.get(0).lookup("#usernameField")).getText();
		String decriptedPassword = ((TextField) this.editorView.getChildren()
				.get(0).lookup("#passwordField")).getText();
		String type = ((TextField) this.editorView.getChildren()
				.get(0).lookup("#typeField")).getText();
		
		if (type.length() > 0) {
			Main.getOpenFile().getAccount().type = type;
		}
		if (decriptedUsername.length() > 0) {
			Main.getOpenFile().getAccount().usernameEncripted = Encriptions.encript(decriptedUsername);
		}
		if (decriptedPassword.length() > 0) {
			Main.getOpenFile().getAccount().encriptedPassword = Encriptions.encript(decriptedPassword);
		}
		Main.getOpenFile().save();
		
		((Label) this.editorView.getChildren().get(0).lookup("#typeCurrentValue")).setText(type);
		((Label) this.editorView.getChildren().get(0).lookup("#usernameCurrentValue")).setText(decriptedUsername);
		((Label) this.editorView.getChildren().get(0).lookup("#passwordCurrentValue")).setText(decriptedPassword);
		
		((TextField) this.editorView.getChildren()
				.get(0).lookup("#usernameField")).clear();
		((TextField) this.editorView.getChildren()
				.get(0).lookup("#passwordField")).clear();
		((TextField) this.editorView.getChildren()
				.get(0).lookup("#typeField")).clear();
		
		Main.primaryStage.setTitle(Main.primaryStage.getTitle().replace("edited", "saved"));
	}

	@FXML
	private void openRequested() {
		/*
		 * Show file explorer to prompt user
		 * to choose which file to open. Only
		 * allow .acct files.
		 */
		
		FileChooser fileChoose = new FileChooser();
		fileChoose.setTitle("Select a .acct file to open.");
		ExtensionFilter acctFileFilter = new ExtensionFilter("Account Files (*.acct)", "*.acct");
		fileChoose.setSelectedExtensionFilter(acctFileFilter);
		File newFile = fileChoose.showOpenDialog(Main.primaryStage);
		if (newFile == null) {
			return;
		}
		
		try {
			Main.openFile(newFile.getAbsolutePath());
	
			this.editorView.getChildren().clear();
			this.editorView.getChildren().add(new FileEditor(Main.getOpenFile()).value);
			this.saveLink.setDisable(false);
			this.saveAsLink.setDisable(false);
		} catch (IOException e) {
			/*
			 * Display message that explains the error
			 */
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	@FXML
	private void saveAsNewAccountRequested() {
		/*
		 * Show file explorer to prompt user
		 * to choose where to save the account
		 * as a new file and the name
		 */
		
		FileChooser fileChoose = new FileChooser();
		fileChoose.setTitle("Select a .acct file to open.");
		ExtensionFilter acctFileFilter = new ExtensionFilter("Account Files (*.acct)", "*.acct");
		fileChoose.setSelectedExtensionFilter(acctFileFilter);
		File newFile = fileChoose.showSaveDialog(Main.primaryStage);
		if (newFile == null) {
			return;
		}
		
		/*
		 * Create the new Account file.
		 */
		AccountFile acctFile = null;
		try {
			newFile.createNewFile();
			acctFile = AccountFile.paste(Main.getOpenFile().copy(), newFile.getAbsolutePath());
			
			/*
			 * saving new file
			 */
			String decriptedUsername = ((TextField) this.editorView.getChildren()
					.get(0).lookup("#usernameField")).getText();
			String decriptedPassword = ((TextField) this.editorView.getChildren()
			.get(0).lookup("#passwordField")).getText();
			String type = ((TextField) this.editorView.getChildren()
			.get(0).lookup("#typeField")).getText();
			
			if (type.length() > 0) {
				acctFile.getAccount().type = type;
			}
			if (decriptedUsername.length() > 0) {
				acctFile.getAccount().usernameEncripted = Encriptions.encript(decriptedUsername);
			}
			if (decriptedPassword.length() > 0) {
				acctFile.getAccount().encriptedPassword = Encriptions.encript(decriptedPassword);
			}
			acctFile.save();
			
			try {
				Main.openFile(acctFile.getFilePath());
				this.editorView.getChildren().clear();
				this.editorView.getChildren().add(new FileEditor(Main.getOpenFile()).value);
			} catch (IOException e) {
				/*
				 * display an error message that says you can't open this file.
				 */
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		} catch (WrongFileTypeException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Could not save this as a new file.");
		} catch (CorruptFileException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Could not save this as a new file.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if (Main.openFile == null) {
			this.saveLink.setDisable(true);
			this.saveAsLink.setDisable(true);
		} else {
			this.editorView.getChildren().add(new FileEditor(Main.openFile).value);
		}
	}
}

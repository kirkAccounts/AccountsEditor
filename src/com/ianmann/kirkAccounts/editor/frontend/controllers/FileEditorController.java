/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 */
package com.ianmann.kirkAccounts.editor.frontend.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.ianmann.kirkAccounts.editor.application.Main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 *
 */
public class FileEditorController implements Initializable {

	@FXML
	private TextField typeField;
	
	@FXML
	private TextField usernameField;

	@FXML
	private TextField passwordField;
	
	@FXML
	private Label typeCurrentValue;
	
	@FXML
	private Label usernameCurrentValue;
	
	@FXML
	private Label passwordCurrentValue;
	
	private void edited() {
		Main.primaryStage.setTitle(Main.primaryStage.getTitle().replace(" (saved)", "")
				.replace(" (edited)", "")+ " (edited)");
	}

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}

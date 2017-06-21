/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 */
package com.ianmann.accounts.fxmlEditor.frontend.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.ianmann.accounts.fxmlEditor.application.Main;
import com.ianmann.accounts.fxmlEditor.frontend.wrappers.FileEditor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
	
	@FXML
	private TabPane securityQuestionContainer;
	
	@FXML
	private Button btnAddQuestion;
	
	@FXML
	private void addQuestionRequested() {
		String question = JOptionPane.showInputDialog("Question:");
		String answer = JOptionPane.showInputDialog("Answer:");
		Main.getOpenFile().getAccount().addDecriptedSecurityQuestion(question, answer);
		Tab questionTab = FileEditor.newQuestionTab(this.securityQuestionContainer, question, answer);
		this.securityQuestionContainer.getTabs().add(questionTab);
	}
	
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

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 */
package com.ianmann.kirkAccounts.editor.frontend.wrappers;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;

import com.ianmann.kirkAccounts.editor.application.Main;
import com.ianmann.kirkAccounts.files.AccountFile;

import iansLibrary.security.Decriptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 *
 */
public class FileEditor {

	private AccountFile file;
	public VBox value;
	private URL fxmlFile = this.getClass().getResource("/com/ianmann/kirkAccounts/editor/frontend/fxml/FileEditor.fxml");
	
	public FileEditor(AccountFile _file) {
		this.file = _file;

		String password = Decriptions.decript(this.file.getAccount().encriptedPassword);
		String username = Decriptions.decript(this.file.getAccount().usernameEncripted);
		
		try {
			this.value = FXMLLoader.load(this.fxmlFile);
			((Label) this.value.lookup("#typeCurrentValue"))
						.setText(this.file.getAccount().type);
			((Label) this.value.lookup("#usernameCurrentValue"))
						.setText(username);
			((Label) this.value.lookup("#passwordCurrentValue"))
						.setText(password);
			AnchorPane.setTopAnchor(this.value, 0.0);
			AnchorPane.setRightAnchor(this.value, 0.0);
			AnchorPane.setBottomAnchor(this.value, 0.0);
			AnchorPane.setLeftAnchor(this.value, 0.0);
			
			String css = this.getClass().getResource("/com/ianmann/kirkAccounts/editor/resources/css/editor.css").toExternalForm();
			this.value.getStylesheets().add(css);

			this.setQuestions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds any existing security questions to the editor
	 */
	public void setQuestions() {
		TabPane questionContainer = ((TabPane) this.value.lookup("#securityQuestionContainer"));
		for (String question : Main.getOpenFile().getAccount().getDecriptedSecurityQuestions().keySet()) {
			String answer = Main.getOpenFile().getAccount().getDecriptedSecurityQuestions().get(question);
			questionContainer.getTabs().add(FileEditor.newQuestionTab(((TabPane) this.value.lookup("#securityQuestionContainer")),
																	question, answer));
		}
	}
	
	public static Tab newQuestionTab(TabPane parent, String question, String answer) {
		Tab questionTab = new Tab("     ");
		questionTab.setId(question);
		
		/*
		 * Create question/answer display
		 */
		VBox Q_A_Display = new VBox();
		
		/*
		 * Create question display
		 */
		AnchorPane questionDisplay = new AnchorPane();
		Label questionField = new Label();
		questionField.setText(question);
		questionDisplay.getChildren().add(questionField);
		AnchorPane.setTopAnchor(questionField, 5.0);
		AnchorPane.setRightAnchor(questionField, 5.0);
		AnchorPane.setBottomAnchor(questionField, 5.0);
		AnchorPane.setLeftAnchor(questionField, 5.0);
		
		/*
		 * Create answer display
		 */
		AnchorPane answerDisplay = new AnchorPane();
		Label answerField = new Label();
		answerField.setText(answer);
		answerDisplay.getChildren().add(answerField);
		AnchorPane.setTopAnchor(answerField, 5.0);
		AnchorPane.setRightAnchor(answerField, 5.0);
		AnchorPane.setBottomAnchor(answerField, 5.0);
		AnchorPane.setLeftAnchor(answerField, 5.0);
		
		Button removeQuestion = new Button("Delete Question");
		removeQuestion.setId("delete:=:" + question);
		removeQuestion.setOnAction(e -> {
			Main.getOpenFile().getAccount().removeQuestion(question);
			parent.getTabs().remove(questionTab);
		});
		
		Q_A_Display.getChildren().addAll(questionDisplay, answerDisplay, removeQuestion);
		questionTab.setContent(Q_A_Display);
		
		return questionTab;
	}
}

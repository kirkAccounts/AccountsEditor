/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 */
package com.ianmann.kirkAccounts.editor.frontend.wrappers;

import java.io.IOException;
import java.net.URL;

import com.ianmann.kirkAccounts.files.AccountFile;

import iansLibrary.security.Decriptions;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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
			/*
			 * Need to set static css files through compiled bin folder as a resource
			 * instead of the regular folder. the jar file can't see the static folder. 
			 */
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

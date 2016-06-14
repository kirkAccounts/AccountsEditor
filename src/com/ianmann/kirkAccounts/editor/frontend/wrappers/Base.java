/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 */
package com.ianmann.kirkAccounts.editor.frontend.wrappers;

import java.io.IOException;
import java.net.URL;

import com.ianmann.kirkAccounts.editor.application.Main;
import com.ianmann.kirkAccounts.files.AccountFile;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: May 28, 2016
 *
 */
public class Base {

	private AccountFile file;
	public BorderPane value;
	private URL fxmlFile = this.getClass().getResource("/com/ianmann/kirkAccounts/editor/frontend/fxml/Base.fxml");
	
	public Base() {
		try {
			this.value = FXMLLoader.load(this.fxmlFile);

			String css = this.getClass().getResource("/com/ianmann/kirkAccounts/editor/resources/css/base.css").toExternalForm();
			this.value.getStylesheets().add(css);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

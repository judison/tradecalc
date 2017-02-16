package org.judison.tradecalc;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

public class Main {

	@FXML
	TextField edtName;
	@FXML
	FlowPane parent;

	@FXML
	public void add() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TradeCalc.fxml"));
			Parent root = loader.load();
			TitledPane pane = new TitledPane(edtName.getText(), root);
			pane.setCollapsible(false);
			Button btnClose = new Button("X");
			btnClose.setOnAction(e -> parent.getChildren().remove(pane));
			btnClose.setFont(new Font(9));
			pane.setGraphic(btnClose);
			parent.getChildren().add(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

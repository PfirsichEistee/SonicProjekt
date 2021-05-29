package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GUI_Launcher {
	// ATTRIBUTE //
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private Label statLabel;
	
	
	private Daten dieDaten;
	
	private Start dieStartklasse;
	private Stage primaryStage;
	
	
	// KONSTRUKTOR //
	public void initialize() {
		statLabel.setText("");
		
		comboBox.getItems().add("Emerald Hill Zone");
		comboBox.getItems().add("Marble Zone");
		
		dieDaten = new Daten();
	}
	
	
	// METHODS //
	@FXML
	void btnAction(ActionEvent event) throws Exception {
		if (comboBox.getValue() == null) {
			statLabel.setText("Du musst ein Level waehlen!");
			return;
		}

		int index = comboBox.getItems().indexOf(comboBox.getValue());
		
		dieStartklasse.startGame(new Stage(), index + 1);
		primaryStage.hide();
	}
	@FXML
	void comboBoxAction(ActionEvent event) {
		if (comboBox.getValue() == null) {
			statLabel.setText("Du musst ein Level waehlen!");
			return;
		}
		
		int index = comboBox.getItems().indexOf(comboBox.getValue());
		int[] stats = dieDaten.getStats(index);
		
		statLabel.setText("-- Statistiken --\n\nZeit: " + stats[0] + "\nRinge: " + stats[1] + "\nHighscore: " + stats[2]);
	}
	
	public void show() {
		primaryStage.show();
	}
	
	public void setStart(Start pStart) {
		dieStartklasse = pStart;
	}
	public void setPrimaryStage(Stage pStage) {
		primaryStage = pStage;
	}
}

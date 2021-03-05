package Crowd_Pressure_AGH;

import Crowd_Pressure_AGH.MusicPlayer.MusicPlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/App.fxml"));
		Parent pre = fxmlLoader.load();
		Scene scene = new Scene(pre);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Crowd Pressure Simulation AGH");
		primaryStage.setMinHeight(600);
		primaryStage.setMinWidth(650);
		primaryStage.setWidth(1440);
		primaryStage.setHeight(900);
		primaryStage.setMaxWidth(1440);
		primaryStage.setMaxHeight(900);
		primaryStage.getIcons().add(new Image("logo/logo.png"));

		primaryStage.setOnCloseRequest(we -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initOwner(primaryStage);
			alert.setHeaderText("");
			alert.setTitle("Exit");
			alert.setContentText("Are you sure you want to exit?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				primaryStage.close();
				MusicPlayer.stop();
			} else {
				we.consume();
			}
		});
		primaryStage.show();
	}
}

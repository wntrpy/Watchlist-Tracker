package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	public static final ConnectionInstance local = new ConnectionInstance("PC-1\\SQLEXPRESS", "trackerEp", "sa", "12345");
	public static Stage stage;

	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Main.fxml"));
			BorderPane root = new BorderPane();
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("Haerin");
			Image icon = new Image(getClass().getResourceAsStream("/Images/haerin.jpg"));
			primaryStage.getIcons().add(icon);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}

// TODO: hindi ma move yung app kasi wala yung decoration sa taas
// TODO: search bar
// TODO: alert sa insert data

package com.example.stickhero;

import java.io.IOException;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        music();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        GameController controller = loader.getController();
        Scene scene = new Scene(root, 406, 650);
        controller.setMainWindow(primaryStage);
        primaryStage.setTitle("SUPERNOVA SAGA");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    MediaPlayer mediaPlayer;
    public void music() {
        String s = "src/main/resources/com/example/stickhero/musics/space_line-27593.mp3";
        Media h = new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

    }

    public static void main(String[] args) {
        launch();
    }
}

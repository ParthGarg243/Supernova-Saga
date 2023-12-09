package com.example.stickhero;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public static void serialize() throws IOException {
        GameEngine gameEngine=new GameEngine();
        ArrayList s=gameEngine.savedGames;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(
                    new FileOutputStream("savedGames.txt"));
            out.writeObject(s);
        } finally {
            out.close();
        }

    }
    public static void deserialize() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("savedGames.txt"));
            ArrayList s1 = (ArrayList) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
        }
    }
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

package com.example.stickhero;//package com.example.stickhero;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//import java.io.IOException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    //@Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("startscreen.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("MainScreen");
//        stage.setWidth(600);
//        stage.setHeight(518);
//        stage.setScene(scene);
//        stage.show();
//    }
    private Hero hero = new Hero(60,45,25, 400);

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Parent root = loader.load();
        HelloController controller = loader.getController();
        controller.setMainWindow(primaryStage);
        primaryStage.setTitle("stickhero");
        primaryStage.setScene(new Scene(root,406, 650));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
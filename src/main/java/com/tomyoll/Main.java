package com.tomyoll;
import com.tomyoll.Database.DatabaseHandler;
import com.tomyoll.MainScene.MainScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Головний клас програми
 */
public class Main extends Application {

    /**
     * Метод для відкриття першого вікна додатку
     * @param primaryStage - Стадія
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        DatabaseHandler db = new DatabaseHandler();
        Timer timer = new Timer();
        long minute = 1000*60;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                db.get_category();
                System.out.println("test");
            }
        }, 1000, minute);
        db.initConnection();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"));
        primaryStage.setTitle("Herb Shop");
        MainScene.currentStage = primaryStage;
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(800);
        primaryStage.setMinWidth(1000);
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> Platform.exit());
        db.getConnection();
    }

    /**
     * Головний клас програми
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

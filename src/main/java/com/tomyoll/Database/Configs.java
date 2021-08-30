package com.tomyoll.Database;

import com.tomyoll.Animation.ColorFill;
import com.tomyoll.Animation.Shake;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class Configs {

    protected static String dbHost = "localhost";
    protected static String dbPort = "3306";
    protected static String dbUser = "root";
    protected static String dbPass = "";
    protected static String dbName = "Herb_shop";
    protected static boolean ConnectStatus = false;
    public static Stage currentSatge;

    @FXML
    private StackPane ParentContainer;

    @FXML
    private AnchorPane MainPane;


    @FXML
    private TextField HostField;

    @FXML
    private TextField PortField;

    @FXML
    private TextField UserField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PasswordField;

    @FXML
    private Label InfoLabal;

    @FXML
    private Button LoginButton;

    @FXML
    private Button BackButton;

    @FXML
    void initialize() {
    }

    @FXML
    void Close() {
        currentSatge.close();
    }

    /**
     * Спроба з'єднатися за парметрами
     */
    @FXML
    void TryConnect() {

        if(!HostField.getText().isEmpty() && !NameField.getText().isEmpty() && !UserField.getText().isEmpty() && !PortField.getText().isEmpty())
        {
            dbHost = HostField.getText();
            dbName = NameField.getText();
            dbUser = UserField.getText();
            dbPort = PortField.getText();
            DatabaseHandler handler = new DatabaseHandler();
            handler.initConnection();
        }
        else {
            if(HostField.getText().isEmpty()) {Shake shake = new Shake(HostField); shake.play_animation();}
            if(NameField.getText().isEmpty()) {Shake shake = new Shake(NameField); shake.play_animation();}
            if(UserField.getText().isEmpty()) {Shake shake = new Shake(UserField); shake.play_animation();}
            if(PortField.getText().isEmpty()) {Shake shake = new Shake(PortField); shake.play_animation();}
            if(PasswordField.getText().isEmpty()) {Shake shake = new Shake(PasswordField); shake.play_animation();}
        }

        if (ConnectStatus)
        {
            InfoLabal.setText("З'єднання успішно встановлене, оновіть каталог");
            ColorFill.ConfigInfoLabel(InfoLabal, true);
        } else {
            InfoLabal.setText("Не вдалося встановити з'єднання з базою даних");
            ColorFill.ConfigInfoLabel(InfoLabal, false);
        }
    }

    /**
     * Повернутися до сцени з авторизацією
     */
    @FXML
    void SwitchToLogin() {

        try {
            Parent loader = FXMLLoader.load(getClass().getResource("/fxml/AuthorizationScene.fxml"));
            Scene scene = LoginButton.getScene();

            loader.translateXProperty().set(-(scene.getWidth()));

            StackPane parent_container = (StackPane) scene.getRoot();
            parent_container.getChildren().add(loader);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(loader.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                ParentContainer.getChildren().remove(MainPane);
            });
            timeline.play();

        } catch (IOException ex) {ex.printStackTrace();}
    }


}

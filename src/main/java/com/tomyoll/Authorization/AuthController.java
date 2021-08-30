package com.tomyoll.Authorization;

import com.tomyoll.Animation.Shake;
import com.tomyoll.Database.DatabaseHandler;
import com.tomyoll.MainScene.MainScene;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Клас авторизації
 */
public class AuthController {

    @FXML
    private StackPane parentContainer;


    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button EnterButton;

    @FXML
    private Button BackButton;

    /**
     * Повернутися до сцени з налаштуваннями
     */
    @FXML
    void BackToSettings() {
        try {
            Parent loader = FXMLLoader.load(getClass().getResource("/fxml/DBSettingsScene.fxml"));
            Scene scene = BackButton.getScene();

            loader.translateXProperty().set((scene.getWidth()));

            StackPane parent_container = (StackPane) scene.getRoot();
            parent_container.getChildren().add(loader);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(loader.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().remove(MainPane);
            });
            timeline.play();

        } catch (IOException ex) {ex.printStackTrace();}
    }

    @FXML
    void initialize(){

        EnterButton.setOnAction(actionEvent -> {
            String loginText = LoginField.getText().trim();
            String passwordText = PasswordField.getText().trim();

            if(!loginText.equals("") && !passwordText.equals(""))
            {
                    loginUser(loginText, passwordText);
            }
        });

    }

    /**
     * Авторизація
     * @param loginText логін
     * @param passwordText пароль
     */
    private void loginUser(String loginText, String passwordText) {
        DatabaseHandler db_handler = new DatabaseHandler();
        String password = CreateHash(passwordText);
        ResultSet result = db_handler.getUser(loginText, password);

        boolean such_user = false;

            try {
                while (result.next()) {
                    such_user = true;
                }

                if (such_user) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
                    Parent root = loader.load();

                    MainScene main = loader.getController();
                    main.AddButtonVisible();
                    Stage stage = MainScene.currentStage;
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                else
                {
                    Shake login_animation = new Shake(LoginField);
                    Shake password_animation = new Shake(PasswordField);
                    login_animation.play_animation();
                    password_animation.play_animation();
                }
            }
            catch (SQLException | IOException ex)
            {
                    ex.printStackTrace();
            }
    }

    /**
     * Шифрування пароля
     * @param password пароль
     * @return зашифрований пароль
     */
    public String CreateHash(String password) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }


}


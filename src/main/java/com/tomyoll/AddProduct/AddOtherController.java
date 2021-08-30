package com.tomyoll.AddProduct;

import com.tomyoll.Animation.ColorFill;
import com.tomyoll.Database.DatabaseHandler;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * В цьому класі додається параметри для продуктів
 */
public class AddOtherController {

    DatabaseHandler handler = new DatabaseHandler();
    ColorFill colorFill = new ColorFill();

    @FXML
    private StackPane ParentContainer;

    @FXML
    private HBox ColumnBox;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField AddDiseaseField;

    @FXML
    private TextField AddCategoryField;

    @FXML
    private TextField AddIngredientField;

    @FXML
    private TextField AddActionField;

    @FXML
    private Button BackButton;

    @FXML
    private Button AddActionButton;

    @FXML
    private Label ErrorField;

    @FXML
    private Label AddingredientLabel;

    @FXML
    private Button AddCategory;

    @FXML
    private MenuButton ChoseCategory;

    @FXML
    private Button AddIngredientButton;

    @FXML
    private Button AddDisease;

    /**
     * Повернутися до додавання продукта
     * @throws IOException
     */
    @FXML
    void SwitchScene() throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("/fxml/AddProductScene.fxml"));
        Scene scene = BackButton.getScene();

        loader.translateXProperty().set((scene.getWidth()));

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

    }

    /**
     * Додати категорію
     */
    @FXML
    void AddCategory() {
        handler.add_other(AddCategoryField.getText(), 3, 0, false);
    }

    /**
     * Додати інгредієнт
     */
    @FXML
    void Addingredient() {

        handler.add_other(AddIngredientField.getText(), 1, 0, false);
        colorFill.ForLabel(AddingredientLabel, false, false);
        colorFill.ForLabel(AddingredientLabel, true, false);
    }

    /**
     * Додати дію на організм
     */
    @FXML
    void AddAction() {
        handler.add_other(AddActionField.getText(), 0, 0, false);
    }

    /**
     * Ініціалізація
     */
    @FXML
    void initialize() {
        ChoseCategory.setDisable(true);
        AddDiseaseField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!AddDiseaseField.getText().isEmpty() &&
                    !AddDiseaseField.getText().equals("") &&
                    !AddDiseaseField.getText().equals(" ")) {
                ChoseCategory.disableProperty().set(false);
            }
            else
            {
                ChoseCategory.disableProperty().set(true);
            }
        });
        BuildCategoryList();

        AddCategory.setDisable(true);
        AddCategoryField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!AddCategoryField.getText().isEmpty() &&
                    !AddCategoryField.getText().equals("") &&
                    !AddCategoryField.getText().equals(" ")) {
                AddCategory.disableProperty().set(false);
            }
            else
            {
                AddCategory.disableProperty().set(true);
            }
        });

        AddIngredientButton.setDisable(true);
        AddIngredientField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!AddIngredientField.getText().isEmpty() &&
                    !AddIngredientField.getText().equals("") &&
                    !AddIngredientField.getText().equals(" ")) {
                AddIngredientButton.disableProperty().set(false);
            }
            else
            {
                AddIngredientButton.disableProperty().set(true);
            }
        });

        AddActionButton.setDisable(true);
        AddActionField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!AddActionField.getText().isEmpty() &&
                    !AddActionField.getText().equals("") &&
                    !AddActionField.getText().equals(" ")) {
                AddActionButton.disableProperty().set(false);
            }
            else
            {
                AddActionButton.disableProperty().set(true);
            }
        });
    }

    /**
     * Заповнення списку категорій
     */
   private void BuildCategoryList() {

           ResultSet categories = handler.get_category();
           try {
               while (categories.next()) {

                   Label label = new Label(categories.getString("name"));
                   label.setTextFill(Color.WHITE);
                   label.setOnMouseEntered(mouseEvent -> colorFill.ForLabel(label, false, true));
                   label.setOnMouseExited(mouseEvent -> colorFill.ForLabel(label, true, true));
                   CustomMenuItem menuItem = new CustomMenuItem(label);
                   ChoseCategory.getItems().add(menuItem);
                   label.setOnMouseClicked(mouseEvent -> {
                     ResultSet CategoryID = handler.get_category(label.getText());
                     try {

                         if (CategoryID.next()) {

                             handler.add_other(AddDiseaseField.getText(), 2, CategoryID.getInt("id"), true);
                         }
                     }catch (SQLException exception) {exception.printStackTrace();}
                   });
               }
           }catch (SQLException throwables) {
               throwables.printStackTrace();
       }
   }

}

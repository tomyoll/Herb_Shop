package com.tomyoll.AddProduct;

import com.tomyoll.Animation.ColorFill;
import com.tomyoll.Database.DatabaseHandler;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, в якому додаються препарати
 */
public class AddProductController {

    ArrayList<String> selected_diseases = new ArrayList<>();
    ArrayList<String> selected_actions = new ArrayList<>();
    ArrayList<String> selected_ingredients = new ArrayList<>();
    String SelectedFile = "no-image.png";

    ColorFill colorFill = new ColorFill();

    @FXML
    private StackPane ParrentContainer;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button BackButton;

    @FXML
    private Button DoneButton;

    @FXML
    private Button AddOtherButton;

    @FXML
    private TextField NameField;

    @FXML
    private MenuButton StructureList;

    @FXML
    private MenuButton DiseaseList;

    @FXML
    private MenuButton ActionList;

    @FXML
    private Line HorizontalLine;

    @FXML
    private Line VerticalLine;

    @FXML
    private VBox LabelColumn;

    @FXML
    private VBox ChoiceColumn;

    @FXML
    private AnchorPane PreviewPane;

    ImageView PreviewImage = new ImageView();

    @FXML
    private Pane PlusBox;

    @FXML
    private Label FileTypeLabel;

    @FXML
    private Label FileSizeLabel;

    @FXML
    private Label NameLabel;

    @FXML
    private Label DragLabel;

    @FXML
    private Label ChoseLabel;

    @FXML
    private Label ErrorField;

    @FXML
    private Label PreviewTitle;

    protected DatabaseHandler handler = new DatabaseHandler();

    public static Stage currentStage;

    /**
     * Перейти до додавання параметрів
     * @throws IOException
     */
    @FXML
    void SwithContent() throws IOException {

        Parent loader = FXMLLoader.load(getClass().getResource("/fxml/AddOtherScene.fxml"));
        Scene scene = AddOtherButton.getScene();

        loader.translateXProperty().set(-(scene.getWidth()));

        StackPane parent_container = (StackPane) scene.getRoot();
        parent_container.getChildren().add(loader);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(loader.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event1 -> {
            ParrentContainer.getChildren().remove(MainPane);
        });
        timeline.play();
    }

    /**
     * Ініціалізація
     */
    @FXML
    void initialize() {
        Platform.runLater(() -> {
            create_structure_list(StructureList);
            create_category_list(DiseaseList);
            create_actions_list(ActionList);

            AnchorPane.setTopAnchor(PreviewImage, PreviewTitle.getHeight()+10);
            AnchorPane.setRightAnchor(PreviewImage, 10.0);
            AnchorPane.setLeftAnchor(PreviewImage, 10.0);
            AnchorPane.setBottomAnchor(PreviewImage, 100.0);
            PreviewPane.getChildren().add(PreviewImage);


            LabelColumn.setSpacing(currentStage.getHeight() / 13);
            ChoiceColumn.setSpacing(currentStage.getHeight() / 13);
            AnchorPane.setRightAnchor(PreviewPane, currentStage.getWidth() / 10);

            NameField.textProperty().addListener((obs, oldVal, newVal) -> NameLabel.setText(NameField.getText()));
            currentStage.minWidthProperty().addListener((obs, oldVal, newVal) ->  PreviewPane.setLayoutX(PreviewPane.getLayoutX()-1));

            currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
                LabelColumn.setSpacing(currentStage.getHeight() / 13);
                ChoiceColumn.setSpacing(currentStage.getHeight() / 13);
            });


        });

        DoneButton.setOnAction(event -> {

            String name = NameField.getText().trim();

            if (!name.isEmpty() && !selected_diseases.isEmpty() && !selected_ingredients.isEmpty() && !selected_actions.isEmpty()) {
                    handler.add_product(name, SelectedFile);
                    InsertDisease(name );
                    InsertIngredient(name);
                    InsertAction(name);
            } else {
                colorFill.ForErrorLabel(ErrorField, false);
            }
        });

        PlusBox.setOnMouseEntered(mouseEvent -> colorFill.ForPlusShape(VerticalLine, HorizontalLine, false));
        PlusBox.setOnMouseExited(mouseEvent -> colorFill.ForPlusShape(VerticalLine, HorizontalLine, true));

    }

    /**
     * Подія кидання файлу
     * @param event
     * @throws IOException
     */
    @FXML
    void DragDroppedHandler(DragEvent event) throws IOException {

                List<File> files = event.getDragboard().getFiles();
                File file = files.get(0);
                Image image = new Image(new FileInputStream(file));

                if(!(image.getHeight()>400.0 || image.getWidth()>220)) {

                File chek = new File("Resources\\" + file.getName());
                if(!chek.exists()) {
                    Files.copy(file.toPath(), new File("Resources\\" + File.separator + file.getName()).toPath());
                    SelectedFile = file.getName();
                    PreviewImage.setImage(null);
                    PreviewImage.setImage(image);
                }else {
                    SelectedFile = file.getName();
                    PreviewImage.setImage(null);
                    PreviewImage.setImage(image);
                }
                }
                else {
                    colorFill.ForFileLabel(FileTypeLabel);
                    colorFill.ForFileLabel(FileSizeLabel);
                }
            }

    /**
     * Подія перетягування файлу
     * @param event
     */
    @FXML
    void DragOverHandler(DragEvent event) {

        colorFill.ForLabel(ChoseLabel, false, false);
        colorFill.ForLabel(DragLabel, false, false);
        colorFill.ForPlusShape(VerticalLine, HorizontalLine, false);
        List<String> validExtensions = Arrays.asList("jpg", "png");
        if (event.getDragboard().hasFiles()) {
            if (!validExtensions.containsAll(event.getDragboard().getFiles().stream()
                    .map(file -> getExtension(file.getName()))
                    .collect(Collectors.toList()))) {
                event.consume();
                return;
            }
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    /**
     * Припинення перетягуванння
     */
    @FXML
    void DragExited() {

        colorFill.ForLabel(ChoseLabel, true, false);
        colorFill.ForLabel(DragLabel, true, false);
        colorFill.ForPlusShape(VerticalLine, HorizontalLine, true);
    }


    /**
     * Перевірити перетягуваний файл
     * @param fileName ім'я файла
     * @return чи підходить файл
     */
    protected String getExtension(String fileName){
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0 && i < fileName.length() - 1) //if the name is not empty
            return fileName.substring(i + 1).toLowerCase();

        return extension;
    }

    /**
     * Додати захворюванння
     * @param name
     */
    private void InsertDisease(String name)
    {
        String id_productSTR = null;
        ResultSet id_product = handler.get_product_id(name);
        try {

            if (id_product.next()) {
                id_productSTR = id_product.getString("id");
            }
        }catch (SQLException ex) {}

        StringJoiner joiner = new StringJoiner("','", "'", "'");
        for(int i=0; i<= selected_diseases.size()-1; i++)
        {
            joiner.add(selected_diseases.get(i));
        }

        ResultSet set = handler.get_disease_id(joiner.toString());
        try {
            while (set.next()) {
                String disease_id = set.getString("id");

                String insert = "INSERT INTO diseases_product (id_disease, id_product) VALUES (" + disease_id + "," + id_productSTR + ");";
                handler.add_disease_product(insert.replaceAll("\\[|\\]", ""));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Додати інгредієнт
     * @param name
     */
    private void InsertIngredient(String name)
    {
        String id_productSTR = null;
        ResultSet id_product = handler.get_product_id(name);
        try {

            if (id_product.next()) {
                id_productSTR = id_product.getString("id");
            }
        }catch (SQLException ex) {}

        StringJoiner joiner = new StringJoiner("','", "'", "'");
        for(int i=0; i<= selected_ingredients.size()-1; i++)
        {
            joiner.add(selected_ingredients.get(i));
        }

        ResultSet set = handler.get_ingredient_id(joiner.toString());
        try {
            while (set.next()) {
                String ingredient_id = set.getString("id");

                String insert = "INSERT INTO ingredients_products (id_ingredient, id_product) VALUES (" + ingredient_id + "," + id_productSTR + ");";
                handler.add_ingredients_product((insert.replaceAll("\\[|\\]", "")));
            }
        } catch (SQLException ex) {}
    }

    /**
     * Додати дію на організм
     * @param name
     */
    private void InsertAction(String name)
    {
        String id_productSTR = null;
        ResultSet id_product = handler.get_product_id(name);
        try {

            if (id_product.next()) {
                id_productSTR = id_product.getString("id");
            }
        }catch (SQLException ex) {}

        StringJoiner joiner = new StringJoiner("','", "'", "'");
        for(int i=0; i<= selected_actions.size()-1; i++)
        {
            joiner.add(selected_actions.get(i));
        }

        ResultSet set = handler.get_action_id(joiner.toString());
        try {
            while (set.next()) {
                String action_id = set.getString("id");

                String insert = "INSERT INTO actions_product (id_action, id_product) VALUES (" + action_id + "," + id_productSTR + ");";
                handler.add_action_product((insert.replaceAll("\\[|\\]", "")));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Заповнення списку складу препарата
     * @param StructureList
     */
    protected void create_structure_list(MenuButton StructureList){
        ResultSet ingredients = handler.get_ingredients();

        try {
            while (ingredients.next()) {
                CheckBox checkBox = new CheckBox(ingredients.getString("name"));
                CustomMenuItem custom_item = new CustomMenuItem(checkBox, false);
                checkBox.setCursor(Cursor.HAND);
                checkBox.setTextFill(Color.WHITE);

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected())
                    {
                        String var = checkBox.getText();
                        if (!selected_ingredients.contains(var)) {
                            selected_ingredients.add(var);
                        }
                    }
                    else
                    {
                        if(selected_ingredients.contains(checkBox.getText()));
                        {
                            selected_ingredients.remove(checkBox.getText());
                        }
                    }
                });
                checkBox.setOnMouseEntered(mouseEvent -> colorFill.ForCheckBox(checkBox, false));
                checkBox.setOnMouseExited(mouseEvent -> colorFill.ForCheckBox(checkBox, true));
                StructureList.getItems().add(custom_item);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Заповнення писку категорій захворювань
     * @param DiseaseList
     */
    protected void create_category_list(MenuButton DiseaseList) {

        ResultSet categories = handler.get_category();
        ArrayList<MenuButton> arrlist = new ArrayList<>();
        try {
            while (categories.next()) {

                MenuButton menu_button = new MenuButton();
                menu_button.setText(categories.getString("name"));
                menu_button.setTextFill(Color.WHITE);
                menu_button.setCursor(Cursor.HAND);
                arrlist.add(menu_button);
                CustomMenuItem custom_item = new CustomMenuItem(menu_button, false);
                DiseaseList.getItems().add(custom_item);
                menu_button.setStyle("-fx-background-color: transparent;");
                menu_button.setTextFill(Color.WHITE);

                menu_button.setOnMouseEntered(mouseEvent -> { colorFill.ForButton(menu_button, false); });
                menu_button.setOnMouseExited(mouseEvent -> { colorFill.ForButton(menu_button, true); });
            }

            for (MenuButton menu : arrlist) {
                ResultSet disease = handler.get_disease_by_category(menu.getText());

                while (disease.next()) {
                    CheckBox checkBox = new CheckBox(disease.getString("name"));
                    checkBox.setTextFill(Color.WHITE);
                    CustomMenuItem custom_item = new CustomMenuItem(checkBox, false);
                    checkBox.setCursor(Cursor.HAND);

                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected())
                        {
                            String var = checkBox.getText();
                            if (!selected_diseases.contains(var)) {
                                selected_diseases.add(var);
                            }
                        }
                        else
                        {
                            if(selected_diseases.contains(checkBox.getText()));
                            {
                                selected_diseases.remove(checkBox.getText());
                            }
                        }
                    });
                    checkBox.setOnMouseEntered(mouseEvent -> colorFill.ForCheckBox(checkBox, false));
                    checkBox.setOnMouseExited(mouseEvent -> colorFill.ForCheckBox(checkBox, true));
                    menu.getItems().add(custom_item);
                }
            }
        } catch (SQLException ex) {}
    }

    /**
     * Заповнення списку дій на організм
     * @param ActionList
     */
    protected void create_actions_list(MenuButton ActionList){
        ResultSet actions = handler.get_actions();
        try {
            while (actions.next()) {
                CheckBox checkBox = new CheckBox(actions.getString("name"));
                checkBox.setTextFill(Color.WHITE);
                CustomMenuItem custom_item = new CustomMenuItem(checkBox, false);
                checkBox.setCursor(Cursor.HAND);

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected())
                    {
                        String var = checkBox.getText();
                        if (!selected_actions.contains(var)) {
                            selected_actions.add(var);
                        }
                    }
                    else
                    {
                        if(selected_actions.contains(checkBox.getText()));
                        {
                            selected_actions.remove(checkBox.getText());
                        }
                    }
                });
                checkBox.setOnMouseEntered(mouseEvent -> colorFill.ForCheckBox(checkBox, false));
                checkBox.setOnMouseExited(mouseEvent -> colorFill.ForCheckBox(checkBox, true));
                ActionList.getItems().add(custom_item);
            }
        } catch (SQLException ex) {}
    }


    /**
     * Вибір файла у провіднику
     */
    @FXML
    void ChooseFile() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Зображення", "*.png", "*.jpg"));
            File selectedFile = chooser.showOpenDialog(new Stage());
            Image image = new Image(new FileInputStream(selectedFile));

            if (!(image.getHeight() > 400.0 || image.getWidth() > 220)) {
                File check = new File("Resources\\" + selectedFile.getName());
                if (!check.exists()) {
                    Files.copy(selectedFile.toPath(), new File("Resources\\" + File.separator + selectedFile.getName()).toPath());
                    SelectedFile = selectedFile.getName();
                    PreviewImage.setImage(image);
                }
                else {
                    SelectedFile = selectedFile.getName();
                    PreviewImage.setImage(null);
                    PreviewImage.setImage(image);
                }
            }
        }catch (Exception ex) {}

    }

}

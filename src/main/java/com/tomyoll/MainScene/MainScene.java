package com.tomyoll.MainScene;

import com.tomyoll.Animation.ColorFill;
import com.tomyoll.Database.Configs;
import com.tomyoll.Database.DatabaseHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainScene {

    public static Stage currentStage;
    ColorFill colorFill = new ColorFill();
    protected DatabaseHandler db_handler = new DatabaseHandler();
    private Stage stage;


    @FXML
    private StackPane parentContainer;

    @FXML
    private MenuButton structure_list;

    @FXML
    private MenuButton category_list;

    @FXML
    private MenuButton actions_list;

    @FXML
    private FlowPane ContentGrid;

    @FXML
    public Button AddButton;

    @FXML
    private Button RefreshButton;

    @FXML
    private Button SearchButton;

    @FXML
    private Button FindButton;

    @FXML
    private TextField SearchField;

    @FXML
    private VBox ConnectErrorImage;

    @FXML
    private HBox SearchBox;

    /**
     * Метод для оновлення сітки товарів
     */
   public void Refresh() {
        initialize();
    }

    public void AddButtonVisible(){
            AddButton.setVisible(true);
        }

    /**
     * Відкриття сцени з налаштуваннями підключення до БД
     * @throws IOException
     */
    @FXML
    void OpenSettings() throws IOException {

        final Stage stage1 = new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.initOwner(currentStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/DBSettingsScene.fxml"));
        Scene SettingsScene = new Scene(root, 400, 340);
        stage1.setScene(SettingsScene);
        Configs.currentSatge = stage1;
        stage1.setResizable(false);
        stage1.show();
    }

    /**
     * Ініціалізація сцени
     */
    @FXML
    void initialize() {

        Platform.runLater(() -> {
            try {
                create_category_list();
                create_actions_list();
                create_structure_list();
                build_grid( "", 0, true);
                //AnchorPane.setLeftAnchor(SearchBox, (SearchBox.getScene().getWindow().getWidth()/2)-SearchBox.getWidth()/2);

                RefreshButton.setOnAction(event -> Refresh());
                AddButton.setOnAction(event -> {

                    Parent loader = null;
                    try {
                        loader = FXMLLoader.load(getClass().getResource("/fxml/AddProductScene.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (stage == null || !stage.isShowing()) {
                        stage = new Stage();
                        Scene scene = new Scene(loader);
                        stage.setScene(scene);
                        stage.setTitle("Додати препарат");
                        stage.setMinWidth(1000);
                        stage.setMinHeight(800);
                        stage.show();
                    }

                });


            }
            catch (Exception e) {
                e.printStackTrace();
            }

            SearchButton.setOnMouseClicked(mouseEvent -> {
                    if (!SearchBox.isVisible()) SearchBox.setVisible(true);
                    else SearchBox.setVisible(false);

            });

            FindButton.setOnAction(event -> {
                if (!SearchField.getText().isEmpty() && !SearchField.getText().equals("") && !SearchField.getText().equals(" "))
                {
                    build_grid(SearchField.getText(), 4, false);
                }
            });
        });
    }

    /**
     * Заповнення списку хвороб та їх категорій
     */
    public void create_category_list(){

        try {
        category_list.getItems().clear();
        ResultSet categories = db_handler.get_category();
        ArrayList<MenuButton> ButtonSlist = new ArrayList<>();
        while (categories.next()) {
            MenuButton menu_button = new MenuButton();
            menu_button.setText(categories.getString("name"));
            menu_button.setCursor(Cursor.HAND);


                MenuItem delete = new MenuItem("Видалити");
                ContextMenu contextMenu = new ContextMenu(delete);
                delete.setOnAction(event -> {
                    db_handler.delete_product(menu_button.getText(), 1);
                    create_category_list();
                });
                menu_button.setOnMouseClicked(mouseEvent -> {
                    if(mouseEvent.getButton().equals(MouseButton.SECONDARY))
                    {
                    contextMenu.show(menu_button, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                    }
                });


            ButtonSlist.add(menu_button);
            CustomMenuItem custom_item = new CustomMenuItem(menu_button, false);
            category_list.getItems().add(custom_item);
            menu_button.setStyle("-fx-background-color: transparent;");
            menu_button.setTextFill(Color.WHITE);
            menu_button.setOnMouseEntered(mouseEvent -> colorFill.ForButton(menu_button, false));
            menu_button.setOnMouseExited(mouseEvent -> colorFill.ForButton(menu_button, true));
        }

        for (MenuButton menu : ButtonSlist) {
            ResultSet disease = db_handler.get_disease_by_category(menu.getText());
            while (disease.next()) {
                Label label = new Label(disease.getString("name"));
                label.setTextFill(Color.WHITE);
                label.setOnMouseEntered(mouseEvent -> colorFill.ForLabel(label, false, true));
                label.setOnMouseExited(mouseEvent -> colorFill.ForLabel(label, true, true));
                CustomMenuItem custom_item = new CustomMenuItem(label, false);
                label.setCursor(Cursor.HAND);

                    MenuItem delete = new MenuItem("Видалити");
                    ContextMenu contextMenu = new ContextMenu(delete);
                    delete.setOnAction(event -> {
                            db_handler.delete_product(label.getText(), 3);
                            create_category_list();
                    });
                    label.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(label, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));

                custom_item.setOnAction(event -> {
                        build_grid(label.getText(), 2, false);
                });
                menu.getItems().add(custom_item);
            }
        }
        } catch (SQLException e)
        {
            e.printStackTrace();
            ConnectErrorImage.setVisible(true);
        }
    }

    /**
     * Заповнення списку дій на організм
     */
    public void create_actions_list() {

        try {
            actions_list.getItems().clear();
            ResultSet actions = db_handler.get_actions();
            while (actions.next()) {
                Label label = new Label(actions.getString("name"));
                label.setTextFill(Color.WHITE);
                label.setOnMouseEntered(mouseEvent -> colorFill.ForLabel(label, false, true));
                label.setOnMouseExited(mouseEvent -> colorFill.ForLabel(label, true, true));
                CustomMenuItem custom_item = new CustomMenuItem(label, false);
                label.setCursor(Cursor.HAND);

                    MenuItem delete = new MenuItem("Видалити");
                    ContextMenu contextMenu = new ContextMenu(delete);
                    delete.setOnAction(event -> {
                            db_handler.delete_product(label.getText(), 4);
                            create_actions_list();
                    });
                    label.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(label, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));

                custom_item.setOnAction(event -> {
                        build_grid(label.getText(), 3, false);
                });
                actions_list.getItems().add(custom_item);
            }
        }catch (SQLException e) {
            ConnectErrorImage.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Заповнення списку інгредієнтів
     */
    public void create_structure_list() {

        structure_list.getItems().clear();
        ResultSet ingredients = db_handler.get_ingredients();
        try {
            while (ingredients.next()) {
                Label label = new Label(ingredients.getString("name"));
                label.setTextFill(Color.WHITE);
                label.setOnMouseEntered(mouseEvent -> colorFill.ForLabel(label, false, true));
                label.setOnMouseExited(mouseEvent -> colorFill.ForLabel(label, true, true));
                CustomMenuItem custom_item = new CustomMenuItem(label, false);
                label.setCursor(Cursor.HAND);

                    MenuItem delete = new MenuItem("Видалити");
                    ContextMenu contextMenu = new ContextMenu(delete);
                    delete.setOnAction(event -> {
                            db_handler.delete_product(label.getText(), 2);
                            create_structure_list();
                    });
                    label.setOnContextMenuRequested(contextMenuEvent -> contextMenu.show(label, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));

                custom_item.setOnAction(event -> {
                        build_grid(label.getText(), 1, false);
                });
                structure_list.getItems().add(custom_item);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Заповнення списку товарів
     * @param value - значення, за яким буде працювати фільтр
     * @param filter - номер фільтра
     * @param all - чи потрібно показувати всі препарати без філтрів
     */
    private void build_grid(String value, int filter, boolean all) {

        try {
            ContentGrid.getChildren().clear();
                ResultSet products = db_handler.Filter(value, filter, all);
                while (products.next()) {
                    AnchorPane pane = new AnchorPane();
                    pane.setPrefSize(200, 300);

                    Label label = new Label();
                    label.setText(products.getString("product_name"));
                    label.setFont(Font.font("System", FontWeight.BOLD, 20));
                    label.setTextFill(Color.web("#ffffff"));

                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER);
                    hbox.setPrefHeight(label.getHeight());
                    AnchorPane.setBottomAnchor(hbox, 0.0);
                    AnchorPane.setLeftAnchor(hbox, 0.0);
                    AnchorPane.setRightAnchor(hbox, 0.0);

                    File file = new File("Resources\\" + products.getString("image_path"));
                    String localUrl = file.toURI().toURL().toString();
                    Image image = new Image(localUrl);
                    javafx.scene.image.ImageView image_view = new ImageView(image);
                    AnchorPane.setTopAnchor(image_view, 10.0);
                    AnchorPane.setRightAnchor(image_view, 10.0);
                    AnchorPane.setLeftAnchor(image_view, 10.0);
                    AnchorPane.setBottomAnchor(image_view, 100.0);


                        MenuItem delete = new MenuItem("Видалити");

                        delete.setOnAction(event -> {
                            db_handler.delete_product(label.getText(), 0);
                            build_grid("", 0, true);
                        });

                        ContextMenu delete_product = new ContextMenu(delete);

                        pane.setOnContextMenuRequested(contextMenuEvent -> delete_product.show(pane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY()));

                    pane.setOnMouseEntered(mouseEvent -> colorFill.ForLabel(label, false, true));
                    pane.setOnMouseExited(mouseEvent -> colorFill.ForLabel(label, true, true));

                    hbox.getChildren().add(label);
                    pane.getChildren().add(hbox);
                    pane.getChildren().add(image_view);
                    ContentGrid.getChildren().add(pane);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            ConnectErrorImage.setVisible(true);
        } catch ( MalformedURLException | NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}

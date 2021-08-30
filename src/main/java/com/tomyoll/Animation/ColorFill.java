package com.tomyoll.Animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;


/**
 * Кллас для анімації заповнення елементів кольором
 */
public class ColorFill {

    Color in = null;
    Color out = null;

    /**
     * Заповнення мітки кольором
     * @param label мітка
     * @param reverse
     * @param white
     */
    public void ForLabel(Label label, boolean reverse, boolean white){

        Color blue = Color.web("#7289da");
        Color gray = Color.web("#8e9297");
        if (white) gray = Color.web("#ffffff");

        if(!reverse) {

            out = gray;
            in = blue;
        }
        else {
            out = blue;
            in = gray;
        }

        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(label.textFillProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(label.textFillProperty(), in, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * Заповнення мітки червоним кольором
     * @param label мітка
     * @param reverse
     */
    public void ForErrorLabel(Label label, boolean reverse ){

        Color red = Color.web("#DC352B");
        Color transparent = Color.web("#36393f");

        if(!reverse) {
            out = transparent;
            in = red;
        }
        else {
            out = red;
            in = transparent;
        }

        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(label.textFillProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(label.textFillProperty(), in, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    /**
     * Для міток, де вибір файла
     * @param label мітка
     */
    public void ForFileLabel(Label label){

        Color red = Color.web("#DC352B");
        Color gray = Color.web("#8e9297");


        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(label.textFillProperty(), gray, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(label.textFillProperty(), red, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(event -> {
            Timeline timeline2 = new Timeline();
            KeyValue kv3 = new KeyValue(label.textFillProperty(), red, Interpolator.EASE_OUT);
            KeyValue kv4 = new KeyValue(label.textFillProperty(), gray, Interpolator.EASE_IN);
            KeyFrame kf2 = new KeyFrame(Duration.millis(1000), kv3, kv4);
            timeline2.getKeyFrames().add(kf2);
            timeline2.play();
        });
    }

    /**
     * Заповнення знака плюса кольором
     * @param line1 горизонтальна лінія
     * @param line2 вертикальна лінія
     * @param reverse
     */
    public void ForPlusShape(Line line1, Line line2, boolean reverse) {

        Color blue = Color.web("#7289da");
        Color gray = Color.web("#8e9297");

        if(!reverse) {
            out = blue;
            in = gray;
        }
        else {
            out = gray;
            in = blue;
        }

        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(line1.strokeProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(line1.strokeProperty(), in, Interpolator.EASE_IN);
        KeyValue kv3 = new KeyValue(line2.strokeProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv4 = new KeyValue(line2.strokeProperty(), in, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
        KeyFrame kf2 = new KeyFrame(Duration.millis(200), kv3, kv4);
        timeline.getKeyFrames().add(kf);
        timeline.getKeyFrames().add(kf2);
        timeline.play();

    }

    /**
     * Заповнення кнопок кольором
     * @param menu_button кнопок
     * @param reverse
     */
    public void ForButton(MenuButton menu_button, boolean reverse){

        Color blue = Color.web("#7289da");
        Color white = Color.web("#ffffff");

        if(!reverse) {
            out = white;
            in = blue;
        }
        else {
            out = blue;
            in = white;
        }

        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(menu_button.textFillProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(menu_button.textFillProperty(), in, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }

    /**
     * Мітка про статус з'єднання
     * @param label мітка
     * @param succesful
     */
    public static void ConfigInfoLabel(Label label, boolean succesful)
    {
        Color transparent = Color.web("#36393f");
        Color blue = Color.web("#7289da");
        Color red = Color.web("#DC352B");

        if (succesful){
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(label.textFillProperty(), transparent, Interpolator.EASE_OUT);
            KeyValue kv2 = new KeyValue(label.textFillProperty(), blue, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
            timeline.getKeyFrames().add(kf);
            timeline.play();

        } else {
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(label.textFillProperty(), transparent, Interpolator.EASE_OUT);
            KeyValue kv2 = new KeyValue(label.textFillProperty(), red, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
            timeline.getKeyFrames().add(kf);
            timeline.play();
        }

    }

    /**
     * Заповнення чекбоксів кольором
     * @param checkBox
     * @param reverse
     */
    public void ForCheckBox(CheckBox checkBox, boolean reverse){

        Color blue = Color.web("#7289da");
        Color white = Color.web("#ffffff");

        if(!reverse) {
            out = white;
            in = blue;
        }
        else {
            out = blue;
            in = white;
        }

        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(checkBox.textFillProperty(), out, Interpolator.EASE_OUT);
        KeyValue kv2 = new KeyValue(checkBox.textFillProperty(), in, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(200), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.play();

    }

}

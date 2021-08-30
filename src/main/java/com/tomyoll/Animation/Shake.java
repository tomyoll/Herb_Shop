package com.tomyoll.Animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Анімація трясіння
 */
public class Shake {
    private TranslateTransition tt;

    /**
     * Анімація трясіння
     * @param node елемента, який потрібно трясти
     */
    public Shake (Node node) {
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
    }

    public void play_animation() { tt.playFromStart(); }
}

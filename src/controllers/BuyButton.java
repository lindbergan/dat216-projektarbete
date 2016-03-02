package controllers;

import javafx.scene.control.Button;

/**
 * Created by Razmus on 2016-03-01.
 */
public class BuyButton extends Button {
    private int productId;

    public BuyButton(String text, int id) {
        setText(text);
        this.productId = id;
    }

    public int getProductId() {
        return this.productId;
    }
}

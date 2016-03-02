package controllers;

import javafx.scene.control.Button;

/**
 * Created by Razmus on 2016-02-28.
 */
public class DelButton extends Button {
    private int row;

    public DelButton(String s, int r) {
        setText(s);
        this.row = r;
    }

    public int getRow() {
        return this.row;
    }
}

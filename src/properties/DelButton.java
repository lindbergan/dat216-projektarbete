package properties;

import javafx.scene.control.Button;

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

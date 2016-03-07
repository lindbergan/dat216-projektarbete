package properties;

import javafx.scene.control.TextField;

public class CartTextField extends TextField {
    private int row;

    public CartTextField(int r) {
        this.row = r;
    }

    public int getRow() {
        return this.row;
    }
}

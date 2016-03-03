package properties;

import javafx.scene.control.TextField;

/**
 * Created by Razmus on 2016-02-28.
 */
public class CartTextField extends TextField {
    private int row;

    public CartTextField(int r) {
        this.row = r;
    }

    public int getRow() {
        return this.row;
    }
}

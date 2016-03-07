package properties;

import javafx.scene.control.Button;

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

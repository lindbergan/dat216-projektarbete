package controllers;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import properties.BuyButton;
import se.chalmers.ait.dat215.project.*;

import java.util.List;

public abstract class ProductView {
    List<Product> productList;
    IMatDataHandler handler = IMatDataHandler.getInstance();
    ShoppingCart cart = handler.getShoppingCart();

    public void showProducts(String category) {
        switch (category) {
            case "Baljväxter":
                productList = handler.getProducts(ProductCategory.POD);
                break;
            case "Bröd":
                productList = handler.getProducts(ProductCategory.BREAD);
                break;
            case "Frukt och grönt": {
                productList = handler.getProducts(ProductCategory.FRUIT);
                productList.addAll(handler.getProducts(ProductCategory.BERRY));
                productList.addAll(handler.getProducts(ProductCategory.CITRUS_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.EXOTIC_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.VEGETABLE_FRUIT));
                productList.addAll(handler.getProducts(ProductCategory.CABBAGE));
                productList.addAll(handler.getProducts(ProductCategory.MELONS));
            }
            break;
            case "Skafferi": {
                productList = handler.getProducts(ProductCategory.FLOUR_SUGAR_SALT);
            }
            break;
            case "Sötsaker och drycker": {
                productList = handler.getProducts(ProductCategory.COLD_DRINKS);
                productList.addAll(handler.getProducts(ProductCategory.SWEET));
                productList.addAll(handler.getProducts(ProductCategory.HOT_DRINKS));
            }
            break;
            case "Fisk": {
                productList = handler.getProducts(ProductCategory.FISH);
            }
            break;
            case "Kött": {
                productList = handler.getProducts(ProductCategory.MEAT);
            }
            break;
            case "Mejeri": {
                productList = handler.getProducts(ProductCategory.DAIRIES);
            }
            break;
            case "Nötter och frön": {
                productList = handler.getProducts(ProductCategory.NUTS_AND_SEEDS);
                productList.addAll(handler.getProducts(ProductCategory.HERB));
            }
            break;
            case "Pasta, potatis och ris": {
                productList = handler.getProducts(ProductCategory.PASTA);
                productList.addAll(handler.getProducts(ProductCategory.POTATO_RICE));
            }
            break;
            case "Rotfrukter": {
                productList = handler.getProducts(ProductCategory.ROOT_VEGETABLE);
            }
            break;
            default:
                productList = handler.getProducts();
        }
    }

    public void buyItem(ActionEvent e) {
        BuyButton bb = (BuyButton) e.getSource();
        StackPane p = (StackPane) bb.getParent();
        ShoppingItem pra = getProductInCart(handler.getProduct(bb.getProductId()));
        String aamount;
        if(pra == null){
            aamount = "1";
            incItem(bb.getProductId());
        }else {
            aamount = (int)pra.getAmount() + "";
        }

        Button posButton = new Button("+");
        Button negButton = new Button("-");
        TextField tf = new TextField(aamount);
        tf.setPrefWidth(50);
        tf.setAlignment(Pos.CENTER);

        p.getChildren().remove(bb);


        HBox hbox = new HBox(10, negButton, tf, posButton);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        p.getChildren().add(hbox);
        p.setAlignment(hbox, Pos.BOTTOM_CENTER);
        p.setMargin(hbox, new Insets(0, 0, 5, 0));

        posButton.setOnAction(ee -> {
            incItem(bb.getProductId());
            int amount = Integer.parseInt(tf.getText());
            amount++;
            tf.setText(String.valueOf(amount));
        });

        negButton.setOnAction(ee -> {
            int amount = Integer.parseInt(tf.getText());
            if (amount < 2) {
                hbox.getChildren().removeAll();
                p.getChildren().removeAll();
                hbox.setVisible(false);
                p.getChildren().add(bb);
                decItem(bb.getProductId());
            } else {
                amount--;
                tf.setText(String.valueOf(amount));
                decItem(bb.getProductId());
            }
        });


    }

    public void incItem(int idd) {
        int id = idd;
        int razzan = 0;
        if (cart.getItems().size() == 0) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        } else
            for (int i = 0; i < cart.getItems().size(); i++) {
                if (cart.getItems().get(i).getProduct().getProductId() == id) {
                    cart.getItems().get(i).setAmount(cart.getItems().get(i).getAmount() + 1);
                } else {
                    razzan = razzan + 1;
                }

            }
        if (razzan == cart.getItems().size()) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        }
    }

    public void decItem(int idd) {
        int id = idd;
        if (cart.getItems().size() == 0) {
            cart.addItem((new ShoppingItem(handler.getProduct(id))));
        } else
            for (int i = 0; i < cart.getItems().size(); i++) {
                if (cart.getItems().get(i).getProduct().getProductId() == id) {
                    if (cart.getItems().get(i).getAmount() > 1) {
                        cart.getItems().get(i).setAmount(cart.getItems().get(i).getAmount() - 1);
                    } else cart.getItems().remove(i);
                }

            }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public String getPriceText(Product p){
        String s;
        double d = p.getPrice();
        if(d == Math.floor(d)){
            int i = (int)d;
            s = i + p.getUnit();
        }else{
            s = String.format("%.2f", d);
            s += p.getUnit();
        }
        return s;
    }

    public ShoppingItem getProductInCart(Product product){
        List<ShoppingItem> inCart = cart.getItems();
        for(ShoppingItem shoppingItem : inCart){
            if(shoppingItem.getProduct().equals(product)){
                return shoppingItem;
            }
        }
        return null;
    }
}

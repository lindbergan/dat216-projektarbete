package controllers;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.util.List;

/**
 * Created by Razmus on 2016-03-03.
 */
public abstract class ProductView {
    List<Product> productList;
    IMatDataHandler handler = IMatDataHandler.getInstance();

    public void showProducts(String category){
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
    public List<Product> getPL(){
        return productList;
    }
}

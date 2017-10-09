package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Product {
    private StringProperty productName;
    private IntegerProperty productPrice;
    private StringProperty productBarcode;
    private IntegerProperty productQuantity;

    public Product(StringProperty productName, IntegerProperty productPrice, StringProperty productBarcode, IntegerProperty productQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBarcode = productBarcode;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public int getProductPrice() {
        return productPrice.get();
    }

    public IntegerProperty productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice.set(productPrice);
    }

    public String getProductBarcode() {
        return productBarcode.get();
    }

    public StringProperty productBarcodeProperty() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode.set(productBarcode);
    }

    public int getProductQuantity() {
        return productQuantity.get();
    }

    public IntegerProperty productQuantityProperty() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity.set(productQuantity);
    }
}

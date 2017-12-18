package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import java.sql.*;

public class Product {
    public StringProperty productName;
    public IntegerProperty productPrice;
    public IntegerProperty productBarcode;

    // TODO : JDBC 를 사용하자.
    public Product(StringProperty productName, IntegerProperty productPrice, IntegerProperty productBarcode) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productBarcode = productBarcode;
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

    public int getProductBarcode() {
        return productBarcode.get();
    }

    public IntegerProperty productBarcodeProperty() {
        return productBarcode;
    }

    public void setProductBarcode(int productBarcode) {
        this.productBarcode.set(productBarcode);
    }


}

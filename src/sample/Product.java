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

    public Product scanBarcode(StringProperty barcode) {
        if (this.productBarcode.equals(barcode)) {
            return this;
        }
        else {
            return null;
        }
    }

}

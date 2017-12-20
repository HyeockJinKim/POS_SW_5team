package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Sales {
    public IntegerProperty productBarcode;
    public IntegerProperty salesNumber;
    public IntegerProperty quantity;
    public StringProperty productName;
    public IntegerProperty productPrice;

    public Sales(IntegerProperty productBarcode, IntegerProperty quantity, StringProperty productName, IntegerProperty productPrice) {
        this.productBarcode = productBarcode;
        this.quantity = quantity;
        this.productName = productName;
        this.productPrice = productPrice;
        this.salesNumber = new SimpleIntegerProperty();
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

    public int getSalesNumber() {
        return salesNumber.get();
    }

    public IntegerProperty salesNumberProperty() {
        return salesNumber;
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber.set(salesNumber);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
}

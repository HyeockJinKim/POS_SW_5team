package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by JH on 2017-12-21.
 */
public class SalesInformation {
    IntegerProperty salesNumber;
    StringProperty salesTime;
    StringProperty productName;
    IntegerProperty productPrice;
    IntegerProperty productQuantity;
    IntegerProperty salesMoney;
    StringProperty isCash;

    public SalesInformation(IntegerProperty salesNumber, StringProperty salesTime, StringProperty productName, IntegerProperty productPrice,
                            IntegerProperty productQuantity, IntegerProperty salesMoney, BooleanProperty isCash) {
        this.salesNumber = salesNumber;
        this.salesTime = salesTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.salesMoney = salesMoney;
        if (isCash == null) this.isCash = null;
        else if (isCash.get() == true) this.isCash = new SimpleStringProperty("현금");
        else        this.isCash = new SimpleStringProperty("카드");
    }

    public void setSalesNumber(int salesNumber) {
        this.salesNumber.set(salesNumber);
    }
    public int getSalesNumber() {
        return this.salesNumber.get();
    }
    public IntegerProperty salesNumberProperty() {
        return this.salesNumber;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime.set(salesTime);
    }
    public String getSalesTime() {
        return this.salesTime.get();
    }
    public StringProperty salesTimeProperty() {
        return this.salesTime;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }
    public String getProductName() {
        return this.productName.get();
    }
    public StringProperty productNameProperty() {
        return this.productName;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice.set(productPrice);
    }
    public int getProductPrice() {
        return this.productPrice.get();
    }
    public IntegerProperty productPriceProperty() {
        return this.productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity.set(productQuantity);
    }
    public int getProductQuantity() {
        return this.productQuantity.get();
    }
    public IntegerProperty productQuantityProperty() {
        return this.productQuantity;
    }

    public void setSalesMoney(int salesMoney) {
        this.salesMoney.set(salesMoney);
    }
    public int getSalesMoney() {
        return this.salesMoney.get();
    }
    public IntegerProperty salesMoneyProperty() {
        return this.salesMoney;
    }

    public void setIsCash(String isCash) {
        this.isCash.set(isCash);
    }
    public String getIsCash() {
        return this.isCash.get();
    }
    public StringProperty isCashProperty() {
        return this.isCash;
    }
    public boolean getIsCashBoolean() {
        return (this.getIsCash().equals("현금")) ? true : false;
    }

}

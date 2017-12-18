package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Sales_record {
    public IntegerProperty salesNumber;
    public StringProperty salesTime;
    public IntegerProperty salesMoney;
    public BooleanProperty isCash;

    public Sales_record(IntegerProperty salesNumber, StringProperty salesTime, IntegerProperty salesMoney, BooleanProperty isCash) {
        this.salesNumber = salesNumber;
        this.salesTime = salesTime;
        this.salesMoney = salesMoney;
        this.isCash = isCash;
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

    public String getSalesTime() {
        return salesTime.get();
    }

    public StringProperty salesTimeProperty() {
        return salesTime;
    }

    public void setSalesTime(String salesTime) {
        this.salesTime.set(salesTime);
    }

    public int getSalesMoney() {
        return salesMoney.get();
    }

    public IntegerProperty salesMoneyProperty() {
        return salesMoney;
    }

    public void setSalesMoney(int salesMoney) {
        this.salesMoney.set(salesMoney);
    }

    public boolean isIsCash() {
        return isCash.get();
    }

    public BooleanProperty isCashProperty() {
        return isCash;
    }

    public void setIsCash(boolean isCash) {
        this.isCash.set(isCash);
    }
}

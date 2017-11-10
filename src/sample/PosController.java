package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class PosController implements Initializable{

    @FXML
    private TableView<Product> counterTable;
    @FXML
    private Label received;
    @FXML
    private Label price;
    @FXML
    private Label change;
    @FXML
    private TextArea barcode;
    @FXML
    private Button cash;
    @FXML
    private Button card;
    @FXML
    private Button barcodeBtn;

    private Map<Integer, Product> productMap = new HashMap<>();
    int productCount=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // 바코드 리스트를 이때 만드는게 어떨까?
        cash.setOnAction(event -> clickCashBtn());
        card.setOnAction(event -> clickCardBtn());
        barcodeBtn.setOnAction(event -> clickBarcodeBtn());
    }

    public void clickCashBtn() {
        Dialog dialog = new Dialog();
        dialog.setTitle("현금 결제");
        dialog.show();
    }

    public void clickCardBtn() {
        Dialog dialog = new Dialog();
        dialog.setTitle("카드 결제");
        dialog.show();
    }

    public void clickBarcodeBtn() {
        String barcodeNumber = barcode.getText();

    }


}

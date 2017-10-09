package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import java.net.URL;
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
    private Label barcode;
    @FXML
    private Button cash;
    @FXML
    private Button card;
    @FXML
    private Button barcodeBtn;
    int productCount=0;
    @Override
    public void initialize(URL location, ResourceBundle resources) { // 바코드 리스트를 이때 만드는게 어떨까?
        cash.setOnAction(event -> clickCashBtn());
        card.setOnAction(event -> clickCardBtn());
        barcodeBtn.setOnAction(event -> clickBarcodeBtn());
    }

    public void clickCashBtn() {
        System.out.println("1");
    }

    public void clickCardBtn() {
        System.out.println("2");
    }

    public void clickBarcodeBtn() {
        String barcodeNumber = barcode.getText();



    }


}

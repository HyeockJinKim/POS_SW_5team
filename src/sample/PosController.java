package sample;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class PosController implements Initializable{

    @FXML
    private TableView<Sales> counterTable;
    @FXML
    private TableColumn<Sales, String> calname;
    @FXML
    private TableColumn<Sales, Integer> calprice;
    @FXML
    private TableColumn<Sales, Integer> calquan;
    @FXML
    private Label received;
    @FXML
    private Label price;
    @FXML
    private Label change;
    @FXML
    private TextArea barcode;
    @FXML
    private TextArea quanti;
    @FXML
    private Button cash;
    @FXML
    private Button card;
    @FXML
    private TableView<SalesInformation> salesRecordTable;
    @FXML
    private Button refundOk;
    @FXML
    private Button barcodeBtn;
    @FXML
    private Button refund;
    @FXML
    private TableView<Product> productView;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> PriceColumn;
    @FXML
    private TableColumn<Product, Integer> barcodeColumn;


    ObservableList<Sales> posList = FXCollections.observableArrayList();
    int productCount=0;
    Connection db;

    @Override
    public void initialize(URL location, ResourceBundle resources) { // 바코드 리스트를 이때 만드는게 어떨까?
        cash.setOnAction(event -> clickCashBtn());
        card.setOnAction(event -> clickCardBtn());
        barcodeBtn.setOnAction(event -> clickBarcodeBtn());
        refund.setOnAction(event -> clickRefundBtn());

        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usr = "postgres";
            String pwd = "su6407";
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, usr, pwd);
            PreparedStatement preparedStatement = db.prepareStatement("SELECT * FROM product;");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<Product> productList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int barcode = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                productList.add(new Product(new SimpleStringProperty(name), new SimpleIntegerProperty(price), new SimpleIntegerProperty(barcode)));
            }
            productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
            PriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
            barcodeColumn.setCellValueFactory(cellData -> cellData.getValue().productBarcodeProperty().asObject());

            productView.setItems(productList);
            resultSet.close();

            //매출정보 가져오기
            preparedStatement = db.prepareStatement("SELECT salesnumber, salestime, productname, price, quantity, salesmoney, iscash FROM sales_record NATURAL JOIN sales NATURAL JOIN product");
            ResultSet salesRecordResult = preparedStatement.executeQuery();
            ObservableList<SalesInformation> recordList = FXCollections.observableArrayList();
            String prevTime = "";
            while (salesRecordResult.next()) {
                int salesnumber = salesRecordResult.getInt(1);
                String salestime = salesRecordResult.getString(2);
                String productname = salesRecordResult.getString(3);
                int price = salesRecordResult.getInt(4);
                int quantity = salesRecordResult.getInt(5);
                int salesmoney = salesRecordResult.getInt(6);
                boolean isCash = salesRecordResult.getBoolean(7);
                SalesInformation salesInfo;
                if (!prevTime.equals(salestime)) {
                    salesInfo = new SalesInformation(new SimpleIntegerProperty(salesnumber), new SimpleStringProperty(salestime), null,
                            null, null, new SimpleIntegerProperty(salesmoney), new SimpleBooleanProperty(isCash));
                    recordList.add(salesInfo);
                    prevTime = salestime;
                }
                salesInfo = new SalesInformation(new SimpleIntegerProperty(salesnumber),null, new SimpleStringProperty(productname),
                        new SimpleIntegerProperty(price), new SimpleIntegerProperty(quantity), null, null);
                recordList.add(salesInfo);
            }
            recordList.add(new SalesInformation(null,null, new SimpleStringProperty("구구콘"),
                    new SimpleIntegerProperty(1500), new SimpleIntegerProperty(-1), null, null));
            salesRecordTable.setItems(recordList);
            salesRecordResult.close();

            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // TODO : Dialog 제대로 만드셈. (안꺼지는문제 해결 및 결제 부분)
    public void clickCashBtn() {
        Stage cashStage = new Stage();
        try {
            Parent cash = FXMLLoader.load(getClass().getResource("cash.fxml"));
            cashStage.setTitle("현 금");
            cashStage.setScene(new Scene(cash, 300, 200));
            cashStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(final WindowEvent event) {
                    cashStage.close();
                }
            });

            ((Button)(cash.getChildrenUnmodifiable().get(0))).setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    String rcv = ((TextArea)(cash.getChildrenUnmodifiable().get(1))).getText();

                    cashStage.close();
                    received.setText(rcv);
                    int chg = Integer.parseInt(received.getText())-Integer.parseInt(price.getText());
                    if (chg >= 0) {
                        change.setText(String.valueOf(chg));
                    } else {
                        return ;
                    }

                    try {
                        String url = "jdbc:postgresql://localhost:5432/postgres";
                        String usr = "postgres";
                        String pwd = "su6407";
                        Class.forName("org.postgresql.Driver");
                        db = DriverManager.getConnection(url, usr, pwd);
                        String time = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초").format(new Date(System.currentTimeMillis()));
                        PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO SALES_RECORD (salesTime, salesMoney, ISCASH) " +
                                "VALUES ('"+ time +"', " + price.getText() +", TRUE )");
                        preparedStatement.executeUpdate();
                        int index = 1;
                        preparedStatement = db.prepareStatement("SELECT sales_record_salesnumber_seq.last_value FROM sales_record_salesnumber_seq;");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            index = resultSet.getInt(1);
                        } else {
                            return;
                        }

                        for (Sales sales : posList) {
                            preparedStatement = db.prepareStatement("INSERT INTO SALES (barcode, salesNumber, quantity) VALUES ("+
                                   sales.getProductBarcode() +", "+ index + ", " + sales.getQuantity() +")");
                            preparedStatement.executeUpdate();
                        }



                        resultSet.close();
                        db.close();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            received.setText("");
                            change.setText("");
                            price.setText("");
                            posList.clear();
                            counterTable.setItems(posList);
                        }
                    });
                }
            });
            cashStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickCardBtn() {
        Stage cardStage = new Stage();
        try {
            Parent card = FXMLLoader.load(getClass().getResource("card.fxml"));
            cardStage.setTitle("카 드");
            cardStage.setScene(new Scene(card, 300, 200));
            cardStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(final WindowEvent event) {
                    cardStage.close();
                }
            });

            ((Button)(card.getChildrenUnmodifiable().get(0))).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    received.setText(price.getText());
                    cardStage.close();

                    try {
                        String url = "jdbc:postgresql://localhost:5432/postgres";
                        String usr = "postgres";
                        String pwd = "su6407";
                        Class.forName("org.postgresql.Driver");
                        db = DriverManager.getConnection(url, usr, pwd);
                        String time = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초").format(new Date(System.currentTimeMillis()));
                        PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO SALES_RECORD (salesTime, salesMoney, ISCASH) " +
                                "VALUES ('"+time +"', " + price.getText() +", FALSE )");
                        preparedStatement.executeUpdate();
                        int index = 1;
                        preparedStatement = db.prepareStatement("SELECT sales_record_salesnumber_seq.last_value FROM sales_record_salesnumber_seq;");
                        ResultSet resultSet = preparedStatement.executeQuery();
                        if (resultSet.next()) {
                            index = resultSet.getInt(1);
                        } else {
                            return;
                        }

                        for (Sales sales : posList) {
                            preparedStatement = db.prepareStatement("INSERT INTO SALES (barcode, salesNumber, quantity) VALUES ("+
                                    sales.getProductBarcode() +", "+ index + ", " + sales.getQuantity() +")");
                            preparedStatement.executeUpdate();
                        }

                        resultSet.close();
                        db.close();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            received.setText("");
                            change.setText("");
                            price.setText("");
                            posList.clear();
                            counterTable.setItems(posList);
                        }
                    });
                }
            });
            cardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO : 정보를 가져온다. 가져온 정보를 스캔해서 Table View에 추가한다.
    public void clickBarcodeBtn() {

        if (barcode.getText().equals("")) {
            return ;
        }
        String barcodeNumber = barcode.getText();
        int quan;
        if (quanti.getText().equals("")) {
            quan = 1;
        } else {
            quan = Integer.parseInt(quanti.getText());
        }
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usr = "postgres";
            String pwd = "su6407";
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, usr, pwd);
            PreparedStatement preparedStatement = db.prepareStatement("SELECT * FROM product\n" +
                    "WHERE barcode = "+barcodeNumber+";");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int barcode = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int price = resultSet.getInt(3);
                if (this.price.getText().equals("")) {
                    this.price.setText("0");
                }
                this.price.setText(Integer.parseInt(this.price.getText())+price*quan+"");
                boolean isContain = false;
                for (Sales sales : posList) {
                    if (sales.getProductBarcode() == barcode) {
                        isContain = true;
                        posList.get(posList.indexOf(sales)).setQuantity(sales.getQuantity()+quan);
                    }
                }
                if (!isContain) {
                    posList.add(new Sales(new SimpleIntegerProperty(barcode), new SimpleIntegerProperty(quan), new SimpleStringProperty(name), new SimpleIntegerProperty(price)));
                }
                calname.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
                calprice.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());
                calquan.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
            } else {
                return;
            }
            counterTable.setItems(posList);
            resultSet.close();
            db.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // TODO : 환불 할 기록? 선택 후 환불 버튼 누르게 작성. 선택한 정보도 띄워주자!!
    // 환불다이얼로그에서 환불한 물품 선택해서 확인 누르면 매출기록에 환불한 기록이 추가되어야 함.
    public void clickRefundBtn() {
        SalesInformation selectedRecord = salesRecordTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedRecord.getSalesNumber());

        Stage refundStage = new Stage();
        try {
            Parent refund = FXMLLoader.load(getClass().getResource("refund.fxml"));
            refundStage.setTitle("환 불");
            refundStage.setScene(new Scene(refund, 600, 400));

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usr = "postgres";
            String pwd = "su6407";
            Class.forName("org.postgresql.Driver");
            db = DriverManager.getConnection(url, usr, pwd);
            PreparedStatement preparedStatement = db.prepareStatement("SELECT barcode, productname, price, quantity FROM sales NATURAL JOIN product ON salesnumber="+selectedRecord.getSalesNumber());
            ResultSet resultSet = preparedStatement.executeQuery();

            TableView<Sales> refundList = (TableView<Sales>)(refund.getChildrenUnmodifiable().get(0));
            ObservableList<Sales> data = FXCollections.observableArrayList();
//            Sales sales1 = new Sales(new SimpleIntegerProperty(001), new SimpleIntegerProperty(1), new SimpleStringProperty("product1"), new SimpleIntegerProperty(1000));
//            data.add(sales1);
//            Sales sales2 = new Sales(new SimpleIntegerProperty(002), new SimpleIntegerProperty(1), new SimpleStringProperty("product2"), new SimpleIntegerProperty(1500));
//            data.add(sales2);
            while (resultSet.next()) {
                int barcode = resultSet.getInt(1);
                String productname = resultSet.getString(2);
                int price = resultSet.getInt(3);
                int quantity = resultSet.getInt(4);
                data.add(new Sales(new SimpleIntegerProperty(barcode), new SimpleIntegerProperty(quantity), new SimpleStringProperty(productname), new SimpleIntegerProperty(price)));
            }
            refundList.setItems(data);


            refundStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(final WindowEvent event) {
                    refundStage.close();
                }
            });

            ((Button)(refund.getChildrenUnmodifiable().get(1))).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Sales selected = refundList.getSelectionModel().getSelectedItem();
                    System.out.println(selected.getProductName());

                    refundStage.close();
                }
            });
            refundStage.show();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

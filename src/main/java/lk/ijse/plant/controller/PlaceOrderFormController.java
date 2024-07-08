package lk.ijse.plant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.plant.bo.BOFactory;
import lk.ijse.plant.bo.Custom.PlaceOrderBO;
import lk.ijse.plant.dao.Custom.CustomerDAO;
import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.Custom.UserDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.db.DBConnection;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.dto.ItemDTO;
import lk.ijse.plant.dto.OrderDTO;
import lk.ijse.plant.entity.Order;
import lk.ijse.plant.entity.OrderItem;
import lk.ijse.plant.entity.PlaceOrder;
import lk.ijse.plant.view.OrderItemTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PlaceOrderFormController {

    @FXML
    private JFXButton btnADD;

    @FXML
    private JFXComboBox<String> combUserId;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Label lblBalance;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<OrderItemTM> tblPlaceOrder;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextArea txtAreaNetBalance;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDate;



    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtQuantity;

    private ObservableList<OrderItemTM> obList = FXCollections.observableArrayList();

    PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        generateNewOrderId();
        setNIC();
        setItemName();
        getUserId();
        setDate();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    public void   setItemName() throws SQLException {
        List<String> item = itemDAO.getItemName();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String n : item){
            obList.add(n);
        }

        TextFields.bindAutoCompletion(txtItemName, obList);
    }
    private void setNIC() throws SQLException {
        List<String> nic = customerDAO.getAllNic();
        ObservableList<String> obList = FXCollections.observableArrayList();

        for (String n : nic){
            obList.add(n);
        }

        TextFields.bindAutoCompletion(txtNIC, obList);
    }

    private void getUserId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> userList = userDAO.getIds();
            for (String id : userList) {
                obList.add(id);
            }
            combUserId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void  generateNewOrderId() throws SQLException, ClassNotFoundException {
        String nextId = placeOrderBO.generateNewOrderID();
        txtOrderId.setText(nextId);
    }
    @FXML
    void btnADDOnAction(ActionEvent event) {
        String ItemId = txtItemId.getText();
        String ItemName = txtItemName.getText();
        int Quantity = Integer.parseInt(txtQuantity.getText());
        double Price = Double.parseDouble(txtPrice.getText());
        double Total = Quantity * Price;
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(selectedIndex);

                tblPlaceOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            if (ItemId.equals(colItemId.getCellData(i))) {

                OrderItemTM tm = obList.get(i);
                Quantity += tm.getQuantity();
                Total = Quantity * Price;

                tm.setQuantity(Quantity);
                tm.setTotal(Total);

                tblPlaceOrder.refresh();

                calculateNetTotal();
                return;
            }
        }
        OrderItemTM tm = new OrderItemTM(ItemId, ItemName, Quantity, Price, Total, btnRemove);
        obList.add(tm);

        tblPlaceOrder.setItems(obList);
        calculateNetTotal();

    }

    public void clearFields(){
        txtItemName.clear();
        txtOrderId.clear();
        txtQuantity.clear();
        txtPrice.clear();
        txtContact.clear();
        txtPrice.clear();
        txtAreaNetBalance.clear();
        txtDate.clear();
        txtNIC.clear();
        txtQtyOnHand.clear();
        txtCustomerName.clear();
        txtCustomerId.clear();
        combUserId.setValue("");
        txtAddress.clear();
        txtItemId.clear();
    }
    private void calculateNetTotal() {
        int netBalance = 0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            netBalance += (double) colTotal.getCellData(i);
        }
        lblBalance.setText(String.valueOf(netBalance));
    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Main Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnNewOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Customer Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText();
        Date date = Date.valueOf(LocalDate.now());
        double Price = Double.parseDouble(txtPrice.getText());
        String cusId = txtCustomerId.getText();
        String userId = combUserId.getValue();



        var order = new Order(orderId,date,Price,cusId,userId);

        List<OrderItem>  odList = new ArrayList<>();

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++){
            OrderItemTM tm = obList.get(i);

            OrderItem od = new OrderItem(
                    orderId,
                    tm.getItemId(),
                    tm.getQuantity(),
                    tm.getPrice()
            );
            odList.add(od);
        }

        try {
            boolean isPlaced = placeOrderBO.placeOrder(new PlaceOrder(order, odList));
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
                obList.clear();
                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                makeOrderBill();
                generateNewOrderId();


            }else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        clearFields();
    }

    public void makeOrderBill() throws JRException, SQLException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/ORDERBILL.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String,Object> data = new HashMap<>();
        data.put("OrderID",txtOrderId.getText());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint, false);
    }
    @FXML
    void txtItemNameOnAction(ActionEvent event) {
        String itemName = txtItemName.getText();
        try{
            ItemDTO itemDTO = placeOrderBO.searchItem(itemName);
            txtItemId.setText(itemDTO.getItem_id());
            txtQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
            txtPrice.setText(String.valueOf(itemDTO.getPrice()));

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void txtNICOnAction(ActionEvent event) {
        String nic = txtNIC.getText();
        try {
            CustomerDTO customerDTO = placeOrderBO.searchCustomer(nic);

            txtCustomerId.setText(customerDTO.getCustomer_id());
            txtCustomerName.setText(customerDTO.getCustomer_name());
            txtAddress.setText(customerDTO.getAddress());
            txtContact.setText(String.valueOf(customerDTO.getContact()));
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
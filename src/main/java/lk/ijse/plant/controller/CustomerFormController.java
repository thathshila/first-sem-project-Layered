package lk.ijse.plant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.plant.bo.BOFactory;
import lk.ijse.plant.bo.Custom.CustomerBO;
import lk.ijse.plant.dao.Custom.CustomerDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.dto.tm.CustomerTM;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXButton btnBACK;

    @FXML
    private JFXButton btnCLEAR;

    @FXML
    private JFXButton btnDELETE;

    @FXML
    private JFXButton btnSAVE;

    @FXML
    private JFXButton btnSEARCH;

    @FXML
    private JFXButton btnUPDATE;

    @FXML
    private JFXButton btnbill;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtNICNumber;

    ObservableList<CustomerTM> observableList;
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
  //  CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    @SneakyThrows
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setDate();
        getAll();
        setCellValueFactory();
        searchFilter();
        generateNextCustomerID();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }
    private void getAll() throws SQLException, ClassNotFoundException{
        observableList = FXCollections.observableArrayList();
        List<CustomerDTO> allCustomer = customerBO.getAllCustomer();

        for (CustomerDTO c : allCustomer){
            observableList.add(new CustomerTM(c.getCustomer_id(),c.getCustomer_name(),c.getContact(),c.getAddress(),c.getNic(),c.getDate()));
        }
        tblCustomer.setItems(observableList);
    }
    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Customer_name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("Nic"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    }

    @FXML
    void btnBACKOnAction(ActionEvent event) {

    }

    @FXML
    void btnBillOnAction(ActionEvent event) {

    }

    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }
        private void clearFields () {
            txtCustomerId.setText("");
            txtCustomerName.setText("");
            txtContact.setText("");
            txtAddress.setText("");
            txtNICNumber.setText("");
            txtDate.setText("");
        }

        private void searchFilter() {
            FilteredList<CustomerTM> filterData = new FilteredList<>(observableList, e -> true);
            txtNICNumber.setOnKeyPressed(e -> {
                txtNICNumber.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                    filterData.setPredicate((Predicate<? super CustomerTM>) customer -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (customer.getCustomer_name().toLowerCase().indexOf(searchKeyword) > -1){
                            return true;
                        } else if (customer.getCustomer_id().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        } else if (customer.getNic().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        }else if(customer.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        }
                        return false;
                    });
                }));
                SortedList<CustomerTM> buyer = new SortedList<>(filterData);
                buyer.comparatorProperty().bind(tblCustomer.comparatorProperty());
                tblCustomer.setItems(buyer);
            });
        }

        private void generateNextCustomerID() throws ClassNotFoundException, SQLException {
        String nextId = customerBO.generateNewCustomerID();
        txtCustomerId.setText(nextId);
        }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes){
            if(!customerBO.deleteCustomer(txtCustomerId.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }
            clearFields();
        }
        getAll();
        generateNextCustomerID();
    }

    @FXML
    void btnSAVEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
  /*  if(!isValidated()){
        new Alert(Alert.AlertType.ERROR,"Please Check TextFields!").show();
        return;
    }
    String user = customerDAO.searchByName(lblUserName.getText());
    lblUserId.setText(user);*/

    String id = txtCustomerId.getText();
    String name = txtCustomerName.getText();
    int contact = Integer.parseInt(txtContact.getText());
    String address = txtAddress.getText();
    String nic = txtNICNumber.getText();
    Date date = Date.valueOf(txtDate.getText());

    if(customerBO.addCustomer(new CustomerDTO(id,name,contact,address,nic,date))){
        new Alert(Alert.AlertType.CONFIRMATION, "Customer Added!").show();
    }else {
        new Alert(Alert.AlertType.ERROR,"SQL Error").show();
    }
    clearFields();
    generateNextCustomerID();
    getAll();
    }

    @FXML
    void btnSEARCHOnAction(ActionEvent event) {

    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
   /* if(!isValidated()){
        new Alert(Alert.AlertType.ERROR,"Please Check TextFields!").show();
        return;
    }*/
      //  String user = customerDAO.searchByName(lblUserName.getText());
     //   lblUserId.setText(user);

        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        int contact = Integer.parseInt(txtContact.getText());
        String address = txtAddress.getText();
        String nic = txtNICNumber.getText();
        Date date = Date.valueOf(txtDate.getText());

        if(customerBO.updateCustomer(new CustomerDTO(id,name,contact,address,nic,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextCustomerID();
        getAll();
    }

    @FXML
    void tblCustomerOnMouseClicked(MouseEvent event) {
    Integer index = tblCustomer.getSelectionModel().getSelectedIndex();
    if(index <= -1){
        return;
    }
    txtCustomerId.setText(colCustomerId.getCellData(index).toString());
    txtCustomerName.setText(colName.getCellData(index).toString());
    txtContact.setText(colContact.getCellData(index).toString());
    txtAddress.setText(colAddress.getCellData(index).toString());
    txtNICNumber.setText(colNIC.getCellData(index).toString());
    txtDate.setText(colDate.getCellData(index).toString());

  //  lblError.setVisible(false);
    }

    @FXML
    void txtAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtAddressOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtCustomerNameOnAction(ActionEvent event) {

    }

    @FXML
    void txtCustomerNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtDateOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNICNumberOnAction(ActionEvent event) {

    }

    @FXML
    void txtNICNumberOnKeyReleased(KeyEvent event) {

    }
   /* public boolean isValidated() {
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME, txtCustomerName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS, txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE, txtDate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NIC, txtNICNumber)) return false;

        return true;
    }*/
}

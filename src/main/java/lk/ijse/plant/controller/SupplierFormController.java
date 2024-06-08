package lk.ijse.plant.controller;

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
import javafx.scene.layout.AnchorPane;
import lk.ijse.plant.bo.BOFactory;
import lk.ijse.plant.bo.Custom.SupplierBO;
import lk.ijse.plant.dto.CustomerDTO;
import lk.ijse.plant.dto.SupplierDTO;
import lk.ijse.plant.dto.tm.SupplierTM;
import lk.ijse.plant.util.Regex;
import lombok.SneakyThrows;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SupplierFormController implements Initializable {

    public AnchorPane rootNode;
    @FXML
    private Button btnBACK;

    @FXML
    private Button btnCLEAR;

    @FXML
    private Button btnDELETE;

    @FXML
    private Button btnSAVE;

    @FXML
    private Button btnSEARCH;

    @FXML
    private Button btnUPDATE;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtUnitPrice;

    ObservableList<SupplierTM> observableList;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        searchFilter();
        generateNextSupplierID();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }
    public void btnSAVEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
      /*  if (!isValidated()) {
            new Alert(Alert.AlertType.ERROR, "Please Check TextFields !").show();
            return;
        }*/
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String product = txtProductName.getText();
        Date date = Date.valueOf(txtDate.getText());
        String nic = txtNIC.getText();

        if (supplierBO.addSupplier(new SupplierDTO(id, name, address, contact, quantity, price, product, date, nic))) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
        clearFields();
        generateNextSupplierID();
        getAll();
    }

    private void searchFilter() {
        FilteredList<SupplierTM> filterData = new FilteredList<>(observableList, e -> true);
        txtNIC.setOnKeyReleased(e -> {
            txtNIC.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super SupplierTM>) supplier -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (supplier.getSupplier_name().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (supplier.getSupplier_id().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (supplier.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                   // } else if (supplier.getContact().toLowerCase().indexOf(searchKeyword) > -1) {
                      //  return true;
                    } else if (supplier.getProduct().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<SupplierTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblSupplier.comparatorProperty());
            tblSupplier.setItems(buyer);
        });
    }

    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields () {
        txtSupplierId.setText("");
       txtSupplierName.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtNIC.setText("");
        txtDate.setText("");
        txtProductName.setText("");
        txtQuantity.setText("");
        txtUnitPrice.setText("");
    }

    void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<SupplierDTO> allSuppliers = supplierBO.getAllSupplier();

        for (SupplierDTO s : allSuppliers) {
            observableList.add(new SupplierTM(s.getSupplier_id(), s.getSupplier_name(),s.getAddress(), s.getContact(),s.getQuantity(),s.getPrice(),s.getProduct(),s.getDate(),s.getNIC()));
        }
        tblSupplier.setItems(observableList);
    }

    void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("Supplier_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Supplier_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("Product"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));

    }

    private void generateNextSupplierID() throws ClassNotFoundException, SQLException {
        String nextId = supplierBO.generateNewSupplierID();
        txtSupplierId.setText(nextId);
    }

    @FXML
    void btnDELETEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes){
            if(!supplierBO.deleteSupplier(txtSupplierId.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }
            clearFields();
        }
        getAll();
        generateNextSupplierID();
    }

    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
    if(!isValidated()){
        new Alert(Alert.AlertType.ERROR,"Please Check TextFields!").show();
        return;
    }
        //  String user = customerDAO.searchByName(lblUserName.getText());
        //   lblUserId.setText(user);

        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtUnitPrice.getText());
        String product = txtProductName.getText();
        Date date = Date.valueOf(txtDate.getText());
        String nic = txtNIC.getText();


        if(supplierBO.updateSupplier(new SupplierDTO(id,name,address,contact,quantity,price,product,date,nic))){
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error").show();
        }
        clearFields();
        generateNextSupplierID();
        getAll();
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.ADDRESS, txtSupplierId);
    }

    public void txtProductNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtProductName);
    }

    public void txtQuantityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.QUANTITY, txtQuantity);
    }

    public void txtUnitPriceOnKeyRelaesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE, txtUnitPrice);
    }

    public void txtNICOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.NIC, txtNIC);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.CONTACT, txtContact);
    }


    public void txtSupplierNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtSupplierName);
    }


    public boolean isValidated() {

        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtSupplierName)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.ADDRESS, txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NIC, txtNIC)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtProductName)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE, txtUnitPrice)) return false;

        return true;
    }
    public void tblSupplierOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblSupplier.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtSupplierId.setText(colId.getCellData(index).toString());
        txtSupplierName.setText(colName.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtContact.setText(colContact.getCellData(index).toString());
        txtQuantity.setText(colQuantity.getCellData(index).toString());
        txtUnitPrice.setText(colUnitPrice.getCellData(index).toString());
        txtProductName.setText(colProductName.getCellData(index).toString());
        txtDate.setText(colDate.getCellData(index).toString());
        txtNIC.setText(colNIC.getCellData(index).toString());

    }
    @FXML
    void btnSEARCHOnAction(ActionEvent event){}
    @FXML
    void btnBACKOnAction(ActionEvent event){}

    @FXML
    void btnShowDetailsOnAction(ActionEvent event){}
}
package lk.ijse.plant.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.plant.bo.BOFactory;
import lk.ijse.plant.bo.Custom.ItemBO;
import lk.ijse.plant.dao.Custom.ItemDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.ItemDTO;
import lk.ijse.plant.view.CustomerTM;
import lk.ijse.plant.view.ItemTM;
import lk.ijse.plant.util.Regex;
import lombok.SneakyThrows;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemFormController implements Initializable {
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
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSearch;

    ObservableList<ItemTM> observableList;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        generateNextItemID();
        setDate();
        searchFilter();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<ItemDTO> item = itemBO.getAllItem();

        for (ItemDTO i : item) {
            observableList.add(new ItemTM(i.getItem_id(),i.getItem_name(),i.getQuantity(),i.getPrice(),i.getDescription(),i.getDate()));
        }
        tblItem.setItems(observableList);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Item_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Item_name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtItemId.setText("");
        txtQuantity.setText("");
        txtItemName.setText("");
        txtPrice.setText("");
        txtDescription.setText("");
        txtDate.setText("");
    }

    public void mouseClickOnAction(MouseEvent mouseEvent) {
        Integer index = tblItem.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtItemId.setText(colId.getCellData(index).toString());
        txtItemName.setText(colName.getCellData(index).toString());
        txtDate.setText(colDate.getCellData(index).toString());
        txtDescription.setText(colDescription.getCellData(index).toString());
        txtPrice.setText(colPrice.getCellData(index).toString());
        txtQuantity.setText(colQuantity.getCellData(index).toString());
    }


    public void txtItemNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtItemName);
    }

    public void txtQuantityOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.QUANTITY, txtQuantity);
    }

    public void txtPriceOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE, txtPrice);
    }

    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate);
    }

    public boolean isValidated() {
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtItemName)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE, txtPrice)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.QUANTITY, txtQuantity)) return false;

        return true;
    }

    public void btnUPDATEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields !").show();
            return;
        }
        String id = txtItemId.getText();
        String name = txtItemName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
        String description = txtDescription.getText();
        Date date = Date.valueOf(txtDate.getText());

        if(itemBO.updateItem(new ItemDTO(id,name,quantity,price,description,date))){
            new Alert(Alert.AlertType.CONFIRMATION, "Item Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
        clearFields();
        getAll();
        generateNextItemID();
    }

    public void btnSEARCHOnAction(ActionEvent actionEvent) {
    }

    public void btnBACKOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Main Form");
        stage.centerOnScreen();
    }

    public void btnSAVEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields !").show();
            return;
        }
        String id = txtItemId.getText();
        String name = txtItemName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());
        String description = txtDescription.getText();
        Date date = Date.valueOf(txtDate.getText());

        if (itemBO.addItem(new ItemDTO(id,name,quantity,price,description,date))){
            new Alert(Alert.AlertType.CONFIRMATION,"Item Added !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"SQL Error !!").show();
        }
        clearFields();
        getAll();
        generateNextItemID();
    }

    public void btnDELETEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            if (!itemBO.deleteItem(txtItemId.getText())) {
                new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
            }
            getAll();
            generateNextItemID();
        }
    }

        private void generateNextItemID() throws ClassNotFoundException, SQLException {
            String nextId = itemBO.generateNewItemID();
            txtItemId.setText(nextId);
        }



    private void searchFilter() {
        FilteredList<ItemTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearch.setOnKeyPressed(e -> {
            txtSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super ItemTM>) item -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (item.getItem_id().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (item.getDescription().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (item.getItem_name().toLowerCase().indexOf(searchKeyword) > -1) {
                        return  true;
                    }
                    return false;
                });
            }));
            SortedList<ItemTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblItem.comparatorProperty());
            tblItem.setItems(buyer);
        });
    }
    }

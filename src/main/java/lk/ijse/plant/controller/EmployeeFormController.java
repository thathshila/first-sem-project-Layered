package lk.ijse.plant.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.plant.bo.Custom.EmployeeBO;

import lk.ijse.plant.dao.Custom.UserDAO;
import lk.ijse.plant.dao.DAOFactory;
import lk.ijse.plant.dto.EmployeeDTO;
import lk.ijse.plant.view.CustomerTM;
import lk.ijse.plant.view.EmployeeTM;

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

public class EmployeeFormController implements Initializable {

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
    private TableColumn<?, ?> colAttendance;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colWorkHours;

    @FXML
    private JFXComboBox<String> comUserId;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private TextField txtAddress;

    @FXML
    private ChoiceBox choiceAttendance;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtWorkHours;

    ObservableList<EmployeeTM> observableList;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @SneakyThrows
    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
        generateNextEmployeeID();
        setDate();
        searchFilter();
        getUserId();

        ObservableList<String> attendanceType = FXCollections.observableArrayList("Present", "Absent");
        choiceAttendance.setItems(attendanceType);
    }

    private void getUserId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> userList = userDAO.getIds();
            for (String id : userList) {
                obList.add(id);
            }
            comUserId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void searchFilter() {
        FilteredList<EmployeeTM> filterData = new FilteredList<>(observableList, e -> true);
        txtSearch.setOnKeyPressed(e -> {
            txtSearch.textProperty().addListener(((observableValue, oldValue, newValue) -> {
                filterData.setPredicate((Predicate<? super EmployeeTM>) employee -> {
                    if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (employee.getEmployee_id().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (employee.getEmployee_name().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (employee.getAddress().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if(employee.getAttendance().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if (employee.getSalary().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    }else if (employee.getPosition().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if (employee.getWorking_hours().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }else if(employee.getUser_id().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }

                    return false;
                });
            }));
            SortedList<EmployeeTM> buyer = new SortedList<>(filterData);
            buyer.comparatorProperty().bind(tblEmployee.comparatorProperty());
            tblEmployee.setItems(buyer);
        });
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }
    public void btnSAVEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields !").show();
            return;
        }

        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());
        Date date = Date.valueOf(txtDate.getText());
        double salary = Double.parseDouble(txtSalary.getText());
        String working_hours = txtWorkHours.getText();
        String attendance = (String) choiceAttendance.getValue();
        String position = txtPosition.getText();
        String user_id = comUserId.getValue();

        if (employeeBO.addEmployee(new EmployeeDTO(id, name, address,contact ,date, salary, working_hours,attendance,position,user_id))) {
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }
        clearFields();
        generateNextEmployeeID();
        getAll();
    }

    private void getAll() throws SQLException, ClassNotFoundException {
        observableList = FXCollections.observableArrayList();
        List<EmployeeDTO> allEmployee = employeeBO.getAllEmployee();

        for (EmployeeDTO e : allEmployee) {
            observableList.add(new EmployeeTM(e.getEmployee_id(), e.getEmployee_name(),e.getAddress(),e.getContact(),e.getDate(),e.getSalary(),e.getWorking_hours(),e.getAttendance(),e.getPosition(),e.getUser_id()));
        }
        tblEmployee.setItems(observableList);
    }

    @FXML
    void btnCLEAROnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtDate.setText("");
        txtSalary.setText("");
        txtWorkHours.setText("");
        choiceAttendance.setValue("");
        txtPosition.setText("");
        comUserId.setValue("");
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Employee_name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        colWorkHours.setCellValueFactory(new PropertyValueFactory<>("Working_hours"));
        colAttendance.setCellValueFactory(new PropertyValueFactory<>("Attendance"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
    }
    @FXML
    void btnUPDATEOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValidated()){
            new Alert(Alert.AlertType.ERROR,"Please Check TextFields !").show();
            return;
        }


        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtAddress.getText();
        int contact = Integer.parseInt(txtContact.getText());
        Date date = Date.valueOf(txtDate.getText());
        double salary = Double.parseDouble(txtSalary.getText());
        String working_hours = txtWorkHours.getText();
        String attendance = (String) choiceAttendance.getValue();
        String position = txtPosition.getText();
        String user_id = comUserId.getValue();

        if(employeeBO.updateEmployee(new EmployeeDTO(id, name, address,contact ,date, salary, working_hours,attendance,position,user_id))){
            new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated !!").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "SQL Error !!").show();
        }

        clearFields();
        getAll();
        generateNextEmployeeID();
    }
    private void generateNextEmployeeID() throws ClassNotFoundException, SQLException {
        String nextId = employeeBO.generateNewEmployeeID();
        txtEmployeeId.setText(nextId);
    }

    public void btnDELETEOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ButtonType yes = new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(result.orElse(no) == yes){
            if(!employeeBO.deleteEmployee(txtEmployeeId.getText())){
                new Alert(Alert.AlertType.ERROR, "SQL Error!!").show();
            }
            clearFields();
            getAll();
            generateNextEmployeeID();
        }
    }
    public void tblEmployeeOnMouseClicked(MouseEvent mouseEvent) {
        Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtEmployeeId.setText(colId.getCellData(index).toString());
        txtEmployeeName.setText(colName.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtContact.setText(colContact.getCellData(index).toString());
        txtDate.setText(colDate.getCellData(index).toString());
        txtSalary.setText(colSalary.getCellData(index).toString());
        txtWorkHours.setText(colWorkHours.getCellData(index).toString());
        choiceAttendance.setValue(colAttendance.getCellData(index).toString());
        txtPosition.setText(colPosition.getCellData(index).toString());
    }



    public void txtEmployeeNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.NAME,txtEmployeeName);
    }


    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.DATE,txtDate);
    }

    public void txtAddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.ADDRESS,txtAddress);
    }

    public void txtSalaryOnKeyReleased(KeyEvent keyEvent){
        Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE,txtSalary);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent){
        Regex.setTextColor(lk.ijse.plant.util.TextField.CONTACT,txtContact);
    }

    public  boolean isValidated(){
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtEmployeeName)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.ADDRESS, txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.CONTACT, txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.PRICE, txtSalary)) return false;

        return true;
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
}

package lk.ijse.plant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.plant.bo.BOFactory;
import lk.ijse.plant.bo.Custom.UserBO;
import lk.ijse.plant.util.Regex;
import lombok.SneakyThrows;
import lk.ijse.plant.dto.UserDTO;


import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserRegisterFormController implements Initializable {
    @FXML
    private Button btnRegisterNow;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;

    @FXML
    private Button Back;

    @FXML
    private TextField txtUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextUserId();
        setDate();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtDate.setText(String.valueOf(now));
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.USERNAME, txtUserName);
    }

    public void txtDateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate);
    }

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.plant.util.TextField.PASSWORD, txtPassword);
    }

    public boolean isValidated() {
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.NAME, txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.DATE, txtDate)) return false;
        if (!Regex.setTextColor(lk.ijse.plant.util.TextField.PASSWORD, txtPassword)) return false;

        return true;
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
    }

    public void btnRegisterNowOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        if (!isValidated()){
            new Alert(Alert.AlertType.WARNING,"Please Check TextFields !").show();
            return;
        }
        String User_id = txtUserId.getText();
        String User_name = txtUserName.getText();
        Date date = Date.valueOf(txtDate.getText());
        String Password = txtPassword.getText();

        if (userBO.addUser(new UserDTO(User_id, User_name, date, Password))) {
            new Alert(Alert.AlertType.CONFIRMATION, "User Added !!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "This ID has been previously used!!").show();
        }
        generateNextUserId();
    }

    private void generateNextUserId() throws ClassNotFoundException {
        try {
            String nextId = userBO.generateNewUserID();
            txtUserId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

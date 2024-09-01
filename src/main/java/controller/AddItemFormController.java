package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Item;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable {

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrice;

    ItemService service = new ItemController();
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        if (service.addItem(item)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Added Successfully....");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Customer Didn't Added....");
            alert.show();
        }

    }

    public void generateID() {
        try {
            String SQL = "SELECT MAX(ItemCode) FROM item";  // Get the maximum customer ID
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();

            int newId = 1;  // Start with ID 1 if there are no customers

            if (resultSet.next()) {
                String lastId = resultSet.getString(1);  // Get the maximum ID from the result set
                if (lastId != null) {
                    // Extract the numeric part of the ID and increment by 1
                    newId = Integer.parseInt(lastId.substring(1)) + 1;
                }
            }

            // Format the new ID with prefix 'C' and 3 digits
            txtItemCode.setText(String.format("P%03d", newId));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateID();
    }

    public void clearText(){
        txtItemCode.setText(null);
        txtDescription.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQtyOnHand.setText(null);
    }
}

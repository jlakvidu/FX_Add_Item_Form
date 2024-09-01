package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateItemFormController {

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
    void btnSearchOnAction(ActionEvent event) {
        try {
            String SQl = "SELECT * FROM item WHERE ItemCode='"+txtItemCode.getText()+"' ";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQl);
            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                txtDescription.setText(resultSet.getString("Description"));
                txtPackSize.setText(resultSet.getString("PackSize"));
                txtUnitPrice.setText(resultSet.getString("UnitPrice"));
                txtQtyOnHand.setText(resultSet.getString("QtyOnHand"));
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Item Didn't Found..");
                alert.show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item = new Item(
          txtItemCode.getText(),
          txtDescription.getText(),
          txtPackSize.getText(),
          Double.parseDouble(txtUnitPrice.getText()),
          Integer.parseInt(txtQtyOnHand.getText())
        );
        if(service.updateItem(item)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item Updated Successfully..");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item Didn't Updated..");
            alert.show();
        }
    }
}

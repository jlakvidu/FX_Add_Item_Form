package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchItemFormController {

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
    void btnSearchOnAction(ActionEvent event) throws SQLException {
        ResultSet resultSet = service.searchItem(txtItemCode.getText());
        if (resultSet.next()) {
            txtDescription.setText(resultSet.getString("Description"));
            txtPackSize.setText(resultSet.getString("PackSize"));
            txtUnitPrice.setText(resultSet.getString("UnitPrice"));
            txtQtyOnHand.setText(resultSet.getString("QtyOnHand"));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Item did't Found....");
            alert.show();
        }
    }

}

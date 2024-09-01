package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService {
    @Override
    public boolean addItem(Item item) {
        try {
            String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,item.getItemCode());
            pstm.setObject(2,item.getDescription());
            pstm.setObject(3,item.getPackSize());
            pstm.setObject(4,item.getUnitPrice());
            pstm.setObject(5,item.getQtyOnHand());
            boolean isAdded = pstm.executeUpdate() > 0;
            return isAdded;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        String SQL = "UPDATE item SET Description ='"+item.getItemCode()+"', PackSize ='"+item.getPackSize()+"', UnitPrice='"+item.getUnitPrice()+"', QtyOnHand='"+item.getQtyOnHand()+"' WHERE ItemCode='"+item.getItemCode()+"' ";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            boolean isUpdated = pstm.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet searchItem(String  id) {
        try {
            String SQl = "SELECT * FROM item WHERE ItemCode='"+id+"' ";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQl);
            ResultSet resultSet = pstm.executeQuery();
            return  resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String id) {
        try {
            String SQL= "DELETE FROM item WHERE ItemCode='"+id+"' ";
            Connection connection = DBConnection.getInstance().getConnection();
            boolean isDeleted = connection.createStatement().executeUpdate(SQL) > 0;
            return isDeleted;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAll() {
        try {
            String SQL = "SELECT * FROM item ";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();
            ObservableList<Item> itemList = FXCollections.observableArrayList();
            while (resultSet.next()){
                Item item = new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")
                );
                itemList.add(item);
            }
            return  itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

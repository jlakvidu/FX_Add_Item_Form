package controller;

import javafx.collections.ObservableList;
import model.Item;

import java.sql.ResultSet;

public interface ItemService {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    ResultSet searchItem(String  id);
    boolean deleteItem(String id);
    ObservableList<Item> getAll();
}

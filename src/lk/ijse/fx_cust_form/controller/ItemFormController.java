package lk.ijse.fx_cust_form.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ItemFormController {

    private static final String URL = "jdbc:mysql://localhost:3306/customerAndItem";
    private static final Properties props = new Properties();

    static{
        props.setProperty("user","root");
        props.setProperty("password","1234");
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField itemId;

    @FXML
    private TextField itemName;

    @FXML
    private TextField price;

    @FXML
    private TextField quantity;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    void btnDeleteOnClick(ActionEvent event) throws SQLException {
        String id= itemId.getText();

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql ="DELETE FROM item WHERE itemId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,id);

            int affectedRows = pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully!");
            }

            itemId.setText("");
            itemName.setText("");
            price.setText("");
            quantity.setText("");
        }
    }

    @FXML
    void btnSaveOnClick(ActionEvent event) throws SQLException {
        String id = itemId.getText();
        String name = itemName.getText();
        String prc = price.getText();
        int qty = Integer.parseInt(quantity.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO item(itemId,itemName,price,quantity)"+
                    "VALUES(?,?,?,?)";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,prc);
            pstm.setInt(4,qty);

            int affectedRows= pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "ITEM ADDED SUCCESSFULLY!");
            }

            itemId.setText("");
            itemName.setText("");
            price.setText("");
            quantity.setText("");
        }
    }

    @FXML
    void btnUpdateOnClick(ActionEvent event) throws SQLException {
        String id = itemId.getText();
        String name = itemName.getText();
        String prc = price.getText();
        int qty = Integer.parseInt(quantity.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "UPDATE item SET itemId=?,itemName=?,price=?,quantity=? WHERE itemId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,prc);
            pstm.setInt(4,qty);
            pstm.setString(5,onActionid);

            int affectedRows= pstm.executeUpdate();

            if(affectedRows>0){
                System.out.println("Customer Details Updated Successfully!");
            }

            itemId.setText("");
            itemName.setText("");
            price.setText("");
            quantity.setText("");
        }
    }

    String onActionid;
    @FXML
    void idOnAction(ActionEvent event) throws SQLException {
        onActionid = itemId.getText();

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "SELECT * FROM item WHERE itemId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,onActionid);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString(2);
                String prc = resultSet.getString(3);
                String qty = resultSet.getString(4);

                itemName.setText(name);
                price.setText(prc);
                quantity.setText(qty);
            }
        }
    }

    @FXML
    void initialize() {
        assert itemId != null : "fx:id=\"itemId\" was not injected: check your FXML file 'itemform.fxml'.";
        assert itemName != null : "fx:id=\"itemName\" was not injected: check your FXML file 'itemform.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'itemform.fxml'.";
        assert quantity != null : "fx:id=\"quantity\" was not injected: check your FXML file 'itemform.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'itemform.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'itemform.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'itemform.fxml'.";

    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/fx_cust_form/view/customerform.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer Management Form");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

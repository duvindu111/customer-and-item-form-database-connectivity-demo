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

public class CustomerFormController{

    private static final String URL = "jdbc:mysql://localhost:3306/customerAndItem";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "1234");
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField custId;

    @FXML
    private TextField custName;

    @FXML
    private TextField address;

    @FXML
    private TextField salary;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    void btnDeleteOnClick(ActionEvent event) throws SQLException {
        String id = custId.getText();

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "DELETE FROM customer WHERE custId = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,id);

            int affectedRows= pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully!");
            }

            custId.setText("");
            custName.setText("");
            address.setText("");
            salary.setText(String.valueOf(""));
        }


    }

    @FXML
    void btnSaveOnClick(ActionEvent event) throws SQLException {
        String id = custId.getText();
        String name = custName.getText();
        String adrs = address.getText();
        double slry = Double.parseDouble(salary.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql = "INSERT INTO customer(custId,custName,address,salary)" +
                    "VALUES(?,?,?,?)";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,adrs);
            pstm.setDouble(4,slry);

            int affectedRows= pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added Successfully!");
            }

            custId.setText("");
            custName.setText("");
            address.setText("");
            salary.setText(String.valueOf(""));

        }
    }

    String OnActionId;
    public void idOnAction(ActionEvent actionEvent) throws SQLException {
         OnActionId = custId.getText();

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql ="SELECT * FROM customer WHERE custId= ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,OnActionId);

            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString(2);
                String adrs = resultSet.getString(3);
                double slry = resultSet.getDouble(4);

                custName.setText(name);
                address.setText(adrs);
                salary.setText(String.valueOf(slry));
            }
        }

    }

    @FXML
    void btnUpdateOnClick(ActionEvent event) throws SQLException {
        String id = custId.getText();
        String name = custName.getText();
        String adrs = address.getText();
        double slry = Double.parseDouble(salary.getText());

        try(Connection connection = DriverManager.getConnection(URL,props)){
            String sql ="UPDATE customer SET custId=? ,custName=?,address=?,salary=? WHERE custId=?";

            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,adrs);
            pstm.setDouble(4,slry);
            pstm.setString(5,OnActionId);

            int affectedRows= pstm.executeUpdate();

            if(affectedRows>0){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Details Updated Successfully!");
            }

            custId.setText("");
            custName.setText("");
            address.setText("");
            salary.setText(String.valueOf(""));
        }
    }

    @FXML
    void initialize() {
        assert custId != null : "fx:id=\"custId\" was not injected: check your FXML file 'customerform.fxml'.";
        assert custName != null : "fx:id=\"custName\" was not injected: check your FXML file 'customerform.fxml'.";
        assert address != null : "fx:id=\"address\" was not injected: check your FXML file 'customerform.fxml'.";
        assert salary != null : "fx:id=\"salary\" was not injected: check your FXML file 'customerform.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'customerform.fxml'.";
        assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'customerform.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'customerform.fxml'.";

    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/fx_cust_form/view/itemform.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Items Management Form");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

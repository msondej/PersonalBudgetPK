package BudgetFXView;

import BudgetFXDatabase.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.ZoneId;



public class MainModelController extends DatabaseConnection {

    private MainModel mainModel;

    @FXML
    private TextField expenseTa;
    @FXML
    private DatePicker expenseDp;
    @FXML
    private ComboBox expenseCb;

    @FXML
    private TextField incomeTa;
    @FXML
    private DatePicker incomeDp;
    @FXML
    private ComboBox incomeCb;

    @FXML
    private TextField balanceTa;
    @FXML
    private PieChart balancePc;


    ObservableList<PieChart.Data> pieChartData;


    public void loadData() throws SQLException
    {
        pieChartData = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT category, SUM(amount) as amount FROM outcome GROUP BY category");
        if(selectedValue == null)
            return;
        while (selectedValue.next())
        {
            pieChartData.add(new PieChart.Data(selectedValue.getString("category"), selectedValue.getDouble("amount")));
        }

    }


    @FXML
    public void displayBalanceChart()
    {
        try
        {
            loadData();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        balancePc.setData(pieChartData);
    }



    @FXML
    public void addExpense() throws SQLException
    {
        try
        {
            String categoryValue = expenseCb.getSelectionModel().getSelectedItem().toString();
            double amountValue = Double.parseDouble(expenseTa.getText());
            java.util.Date dateValue = java.util.Date.from(expenseDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(dateValue.getTime());
            Connection testConnection = getDbConnection();
            testConnection.createStatement().executeUpdate("INSERT INTO outcome (category, amount, dexpense) VALUES('" + categoryValue + "'," + amountValue + ",'" + sqlDate + "')");
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga");
            alert.setHeaderText(null);
            alert.setContentText("Należy uzupełnić wymagane pola!");
            alert.showAndWait();
        }
        expenseTa.clear();
        showBalance();

    }


    @FXML
    public void addIncome() throws SQLException
    {
        try
        {
            String categoryValue = incomeCb.getSelectionModel().getSelectedItem().toString();
            double amountValue = Double.parseDouble(incomeTa.getText());
            java.util.Date dateValue = java.util.Date.from(incomeDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(dateValue.getTime());
            Connection testConnection = getDbConnection();
            testConnection.createStatement().executeUpdate("INSERT INTO income (category, amount, dexpense) VALUES('" + categoryValue + "'," + amountValue + ",'" + sqlDate + "')");
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga");
            alert.setHeaderText(null);
            alert.setContentText("Należy uzupełnić wszystkie pola!");
            alert.showAndWait();
        }
        incomeTa.clear();
        showBalance();
    }



    @FXML
    public void showBalance() throws SQLException
    {
        Double income = selectValue("amount","income");
        Double expense = selectValue("amount","outcome");
        Double balance = income - expense;
        DecimalFormat df = new DecimalFormat("#.00");
        balanceTa.setText(df.format(balance).toString());
    }



    @FXML
    public void manageCategories() throws Exception
    {
        mainModel.showCategoriesModel();
    }


    @FXML
    public void manageReports () throws Exception
    {
        mainModel.showReportModel();
    }


    @FXML
    public void getChartView() throws Exception
    {
        mainModel.showChartModel();
    }


    @FXML
    public void displayCategories(Boolean isSelected, ComboBox comboName) throws SQLException
    {
        ObservableList<String> categoryItem = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT DISTINCT rubric FROM categoryname WHERE state =" + isSelected);
        if(selectedValue == null)
            return ;
        while (selectedValue.next())
        {
            categoryItem.add(selectedValue.getString("rubric"));
            comboName.setItems(categoryItem);
        }
    }

    @FXML
    public void displayExpenseCategories() throws SQLException
    {
        displayCategories(true, expenseCb);
    }


    @FXML
    public void displayIncomeCategories() throws SQLException
    {
        displayCategories(false, incomeCb);
    }


    public void setMainModel(MainModel mModel)
    {
        mainModel = mModel;
        try
        {
            showBalance();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}


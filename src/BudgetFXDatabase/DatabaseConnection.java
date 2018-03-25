package BudgetFXDatabase;

import BudgetFXView.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class DatabaseConnection {


    public Connection getDbConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/personalbudget", "root", "");
    }


    public double selectValue(String record, String table) throws SQLException
    {
        double returnedValue = 0.0;
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT " + record + " FROM " + table);
        while (selectedValue.next()) {
            returnedValue += selectedValue.getDouble("amount");

        }
        return returnedValue;
    }


    public ObservableList<Double> selectAndSumDayValue() throws SQLException
    {
        ObservableList<Double> aItem = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT SUM(amount) as amount FROM outcome WHERE dexpense= CURRENT_DATE() GROUP BY category ");
        if(selectedValue == null)
            return null;
        while (selectedValue.next()) {
            aItem.add(selectedValue.getDouble("amount"));
        }
        return aItem;
    }


    public ObservableList<String> dayCategory() throws SQLException
    {
        ObservableList<String> categoryName = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT DISTINCT category FROM outcome WHERE dexpense= CURRENT_DATE()");
        if (selectedValue == null)
            return null;
        while (selectedValue.next()) {
            categoryName.add(selectedValue.getString("category"));
        }
        return categoryName;

    }


    public ObservableList<Double> selectAndSumWeekValue() throws SQLException
    {
        ObservableList<Double> monthlyValue = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT SUM(amount) as amount FROM outcome WHERE dexpense BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY ) AND CURRENT_DATE() GROUP BY category");
        if (selectedValue == null )
            return null;
        while (selectedValue.next()){
            monthlyValue.add(selectedValue.getDouble("amount"));
        }
        return monthlyValue;
    }


    public ObservableList<String> weekCategory() throws SQLException
    {
        ObservableList<String> categoryName = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT DISTINCT category FROM outcome WHERE dexpense BETWEEN DATE_SUB(CURRENT_DATE(), INTERVAL 7 DAY ) AND CURRENT_DATE()");
        if (selectedValue == null)
            return null;
        while (selectedValue.next()) {
            categoryName.add(selectedValue.getString("category"));
        }
        return categoryName;

    }


    public ObservableList<Double> selectAndSumMonthValue() throws SQLException
    {
        ObservableList<Double> aaItem = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT SUM(amount) as amount FROM outcome WHERE dexpense >= DATE_SUB(CURRENT_DATE (), INTERVAL 1 MONTH) GROUP BY category");
        if (selectedValue == null)
            return null;
        while (selectedValue.next())
        {
            aaItem.add(selectedValue.getDouble("amount"));
        }
        return aaItem;
    }


    public ObservableList<String> monthCategory() throws SQLException
    {
        ObservableList<String> categoryName = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT DISTINCT category FROM outcome WHERE dexpense >= DATE_SUB(CURRENT_DATE (), INTERVAL 1 MONTH )");
        if (selectedValue == null)
            return null;
        while (selectedValue.next())
        {
            categoryName.add(selectedValue.getString("category"));
        }
        return categoryName;

    }


    public void setStringValue(String table, String record1, String record2, String value , Boolean isSelected) throws SQLException
    {
        try
        {
            Connection testConnection = getDbConnection();
            testConnection.createStatement().executeUpdate("INSERT INTO " + table + "(" + record1 + "," + record2 + ") VALUES ('" + value +"'," + isSelected +")");
        }
        catch (Exception ex)
        {
            System.out.println("Error " + ex);
        }
    }


    public void deleteValue (String table, String value) throws SQLException
    {
        try
        {
            Connection testConnection = getDbConnection();
            testConnection.createStatement().executeUpdate("DELETE FROM " + table + " WHERE rubric = '" + value +"'");
        }
        catch (Exception ex)
        {
            System.out.println("Error " + ex);
        }
    }


    public ObservableList<Report> getDayExpense(Date sqlDate) throws SQLException
    {
        ObservableList<Report> expenseDatabase = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();
        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT * FROM personalbudget.outcome WHERE dexpense= '" + sqlDate + "'");
        if(selectedValue == null)
            return null;
        while(selectedValue.next())
        {
            Report expense = new Report();
            expense.setCategory(selectedValue.getString("category"));
            expense.setAmount(selectedValue.getDouble("amount"));
            expense.setDate(selectedValue.getDate("dexpense"));
            expenseDatabase.add(expense);
        }
        return expenseDatabase;
    }



    public ObservableList<Report> getWeekExpense(Date sqlDate) throws SQLException
    {
        ObservableList<Report> expenseDatabase = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();

        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT category, amount, dexpense FROM outcome WHERE dexpense BETWEEN DATE_SUB('" + sqlDate +"', INTERVAL 7 DAY ) AND '" + sqlDate +"'");
        if(selectedValue == null)
            return null;
        while(selectedValue.next())
        {
            Report expense = new Report();
            expense.setCategory(selectedValue.getString("category"));
            expense.setAmount(selectedValue.getDouble("amount"));
            expense.setDate(selectedValue.getDate("dexpense"));
            expenseDatabase.add(expense);
        }
        return expenseDatabase;
    }


    public ObservableList<Report> getMonthExpense(Date sqlDate) throws SQLException
    {
        ObservableList<Report> expenseDatabase = FXCollections.observableArrayList();
        Connection testConnection = getDbConnection();

        ResultSet selectedValue = testConnection.createStatement().executeQuery("SELECT * FROM outcome WHERE dexpense BETWEEN DATE_SUB('" + sqlDate +"', INTERVAL 1 MONTH ) AND '" + sqlDate +"'");
        if(selectedValue == null)
            return null;
        while(selectedValue.next())
        {
            Report expense = new Report();
            expense.setCategory(selectedValue.getString("category"));
            expense.setAmount(selectedValue.getDouble("amount"));
            expense.setDate(selectedValue.getDate("dexpense"));
            expenseDatabase.add(expense);
        }
        return expenseDatabase;
    }

}

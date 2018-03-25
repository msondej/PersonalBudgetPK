package BudgetFXView;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;


public class CategoriesModelController extends MainModelController {


    @FXML
    private TextField incomeAddCategory;
    @FXML
    private TextField expenseAddCategory;
    @FXML
    private ComboBox incomeDeleteCategory;
    @FXML
    private ComboBox expenseDeleteCategory;



    @FXML
    public void addIncomeCategory() throws SQLException
    {
        try
        {
            String incomeItem = incomeAddCategory.getText().trim();
            if (!incomeItem.isEmpty() || incomeItem != "")
                setStringValue("categoryname", "rubric", "state", incomeItem, false);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uwaga");
            alert.setHeaderText(null);
            alert.setContentText("Należy wpisać kategorię!");
            alert.showAndWait();
        }
        incomeAddCategory.clear();
    }


    @FXML
    public void addExpenseCategory() throws SQLException {
        try
        {
            String expenseItem = expenseAddCategory.getText().trim();
            if (!expenseItem.isEmpty() || expenseItem != "")
                setStringValue("categoryname", "rubric", "state", expenseItem, true);
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Należy wpisać kategorię!");
            alert.showAndWait();
        }
        expenseAddCategory.clear();
    }


    @FXML
    public void deleteIncomeCategory() throws SQLException
    {
        try {
            String incomeItem = incomeDeleteCategory.getSelectionModel().getSelectedItem().toString();
            if (incomeItem != null)
                deleteValue("categoryname", incomeItem);
        } catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText(null);
            alert.setContentText("Należy wybrać kategorię!");
            alert.showAndWait();
        }

    }


    @FXML
    public void deleteExpenseCategory() throws SQLException {
        try
        {
            String expenseItem = expenseDeleteCategory.getSelectionModel().getSelectedItem().toString();
            if (expenseItem != null)
                deleteValue("categoryname", expenseItem);
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blad");
            alert.setHeaderText(null);
            alert.setContentText("Należy wybrać kategorię!");
            alert.showAndWait();
        }
    }


    @FXML
    public void displayIncomeItems() throws SQLException
    {
        displayCategories(false, incomeDeleteCategory);

    }


    @FXML
    public void displayExpenseItems() throws SQLException
    {
        displayCategories(true, expenseDeleteCategory);

    }
}








package BudgetFXView;

import BudgetFXDatabase.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.ZoneId;


public class ReportModelController extends DatabaseConnection {


    @FXML
    private DatePicker dateDp;
    @FXML
    private RadioButton dayRb;
    @FXML
    private RadioButton weekRb;
    @FXML
    private RadioButton monthRb;
    @FXML
    private TableView<Report> expenseTv;
    @FXML
    private TableColumn<Report, String> categoryTableColumn;
    @FXML
    private TableColumn<Report, Double> amountTableColumn;
    @FXML
    private TableColumn<Report, Date> dateTableColumn;
    @FXML
    ToggleGroup toggleGroup;

    private MainModel mainModel;


    public void setMainModel(MainModel mModel)
    {
        mainModel = mModel;
    }

    @FXML
    public void comeBack() throws SQLException
    {
        try
        {
            mainModel.showMainModel();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    public void getView() throws SQLException
    {
        try {
            java.util.Date dateValue = java.util.Date.from(dateDp.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlDate = new java.sql.Date(dateValue.getTime());

            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            amountTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            if (dayRb.isSelected()) {
                expenseTv.setItems(getDayExpense(sqlDate));
                weekRb.disabledProperty();
                monthRb.disabledProperty();

            }
            if (weekRb.isSelected()) {
                expenseTv.setItems(getWeekExpense(sqlDate));
                dayRb.disabledProperty();
                monthRb.disabledProperty();
            }
            if (monthRb.isSelected()) {
                expenseTv.setItems(getMonthExpense(sqlDate));
                dayRb.disabledProperty();
                weekRb.disabledProperty();
            }
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.setContentText("Wybierz datę i rodzaj raportu!");
            alert.showAndWait();
        }

    }
}


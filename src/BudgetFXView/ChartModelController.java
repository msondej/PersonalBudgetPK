package BudgetFXView;

import BudgetFXDatabase.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.sql.SQLException;


public class ChartModelController extends DatabaseConnection {

    @FXML
    private BarChart dailyExpenseBc;
    @FXML
    private BarChart weeklyExpenseBc;
    @FXML
    private BarChart monthlyExpenseBc;

    private MainModel mainModel;


    public void setMainModel(MainModel mModel)
    {

        mainModel = mModel;
        try {
            displayDailyChart();
            displayWeeklyChart();
            displayMonthlyChart();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goToPreviousScene()
    {
        try
        {
            mainModel.showMainModel();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    @FXML
    public void displayDailyChart() throws SQLException
    {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int i = 0; i < dayCategory().size(); i++) {
            series.getData().add(new XYChart.Data<>(dayCategory().get(i),selectAndSumDayValue().get(i)));
        }
        dailyExpenseBc.getData().add(series);

    }


    @FXML
    public void displayWeeklyChart() throws SQLException
    {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int i = 0; i < weekCategory().size(); i++) {
            series.getData().add(new XYChart.Data<>(weekCategory().get(i), selectAndSumWeekValue().get(i)));
        }
        weeklyExpenseBc.getData().add(series);

    }


    @FXML
    public void displayMonthlyChart() throws SQLException
    {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (int i = 0; i < selectAndSumMonthValue().size(); i++) {
            series.getData().add(new XYChart.Data<>(monthCategory().get(i), selectAndSumMonthValue().get(i)));
        }
        monthlyExpenseBc.getData().add(series);

    }

}

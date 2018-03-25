package BudgetFXView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MainModel extends Application
{
    public Stage mainStage;
    public Stage categoriesStage = new Stage();
    public BorderPane mainPane;


    @Override
    public void start(Stage primaryStage) throws IOException
    {
        mainStage = primaryStage;
        mainStage.setTitle("Personal Budget");
        showMainModel();
    }

    public void showMainModel() throws IOException
    {
        FXMLLoader fLoader = new FXMLLoader();
        fLoader.setLocation(MainModel.class.getResource("/BudgetFXView/MainModel.fxml"));
        mainPane = fLoader.load();
        MainModelController mController = fLoader.getController();
        mController.setMainModel(this);
        Scene mainScene = new Scene(mainPane);
        mainStage.setScene(mainScene);
        mainStage.show();
    }


    public void showCategoriesModel() throws IOException
    {
        FXMLLoader fLoader = new FXMLLoader();
        BorderPane categoriesPane = fLoader.load(MainModel.class.getResource("/BudgetFXView/CategoriesModel.fxml"));
        Scene categoriesScene = new Scene(categoriesPane);
        categoriesStage.setScene(categoriesScene);
        categoriesStage.show();

    }


    public void showReportModel() throws IOException
    {
        FXMLLoader floader = new FXMLLoader();
        floader.setLocation(MainModel.class.getResource("/BudgetFXView/ReportModel.fxml"));
        BorderPane bPane = floader.load();
        ReportModelController rc = floader.getController();
        rc.setMainModel(this);
        mainPane.setCenter(bPane);

    }


    public void showChartModel() throws IOException
    {
        FXMLLoader floader = new FXMLLoader();
        floader.setLocation(MainModel.class.getResource("/BudgetFXView/ChartModel.fxml"));
        BorderPane bPane = floader.load();
        ChartModelController cm = floader.getController();
        cm.setMainModel(this);
        mainPane.setCenter(bPane);

    }

    public static void main(String[] args)
    {
        launch(args);
    }

}


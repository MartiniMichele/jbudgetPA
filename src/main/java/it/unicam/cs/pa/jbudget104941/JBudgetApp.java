package it.unicam.cs.pa.jbudget104941;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class JBudgetApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/JBudget.fxml"));
        primaryStage.setTitle("Jbudget");
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();


    }
}

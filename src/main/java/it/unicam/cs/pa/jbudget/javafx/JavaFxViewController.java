package it.unicam.cs.pa.jbudget.javafx;

import it.unicam.cs.pa.jbudget.controller.Ledger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxViewController implements JavaFxControllerInterface {

    /**
     * Crea un ledger per gestire i dati dell'applicazione
     */
    private Ledger ledger = new Ledger();



    /**
     * Metodo per cambiare la scena in una in cui è possibile creare un conto
     */
   @FXML public void CreaContoButtonPushed(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/CreaConti.fxml"));
       Parent creaContoParent = loader.load();
       Scene creaContoScene = new Scene(creaContoParent);

       JavaFxCreaContoController controller = loader.getController();
       controller.initElements(ledger);

       Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
       window.setScene(creaContoScene);
       window.show();
   }


    /**
     * Metodo per cambiare la scena in una in cui è possibile creare un movimento
     */
   @FXML public void CreaMovimentiButtonPushed(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader();
       loader.setLocation(getClass().getResource("/CreaMovimenti.fxml"));
       Parent creaMovimentoParent = loader.load();
       Scene creaMovimentoScene = new Scene(creaMovimentoParent);

       JavaFxCreaMovimentoController controller = loader.getController();
       controller.initElements(ledger);

       Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
       window.setScene(creaMovimentoScene);
       window.show();
   }


    /**
     * Metodo per cambiare la scena in una in cui è possibile creare un tag
     */
    @FXML public void CreaTagButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CreaTag.fxml"));
        Parent creaTagParent = loader.load();
        Scene creaTagScene = new Scene(creaTagParent);

        JavaFxCreaTagController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(creaTagScene);
        window.show();
        }

    /**
     * Metodo per cambiare la scena in una in cui è possibile creare un budget
     */
    @FXML public void CreaBudgetButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/CreaBudget.fxml"));
        Parent creaBudgetParent = loader.load();
        Scene creaBudgetScene = new Scene(creaBudgetParent);

        JavaFxCreaBudgetController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(creaBudgetScene);
        window.show();
        }


    /**
     * Metodo per cambiare la scena in una in cui è possibile visualizzare i tag
     * ed effettuare alcune operazioni
     */
    @FXML public void viewTagButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewTag.fxml"));
        Parent viewTagParent = loader.load();
        Scene viewTagScene = new Scene(viewTagParent);

        JavaFxViewTagController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewTagScene);
        window.show();
    }



    /**
     * Metodo per cambiare la scena in una in cui è possibile visualizzare i conti
     * ed effettuare alcune operazioni
     */
    @FXML public void viewContiButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewConti.fxml"));
        Parent viewContiParent = loader.load();
        Scene viewContiScene = new Scene(viewContiParent);

        JavaFxViewContiController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewContiScene);
        window.show();
    }



    /**
     * Metodo per cambiare la scena in una in cui è possibile visualizzare i budget
     * ed effettuare alcune operazioni
     */
    @FXML public void viewBudgetButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewBudget.fxml"));
        Parent viewBudgetParent = loader.load();
        Scene viewBudgetScene = new Scene(viewBudgetParent);

        JavaFxViewBudgetController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewBudgetScene);
        window.show();
    }



    /**
     * Metodo per cambiare la scena in una in cui è possibile visualizzare i movimenti
     * ed effettuare alcune operazioni
     */
    @FXML public void viewMovimentiButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ViewMovimenti.fxml"));
        Parent viewMovimentiParent = loader.load();
        Scene viewMovimentiScene = new Scene(viewMovimentiParent);

        JavaFxViewMovimentiController controller = loader.getController();
        controller.initElements(ledger);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewMovimentiScene);
        window.show();
    }

    /**
     * Metodo usato per aprire una finestra di errore
     * @param str Stringa di errore
     */
    @Override
    public void launchError(String str) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setTitle("ERRORE!");
       alert.setHeaderText("Qualcosa e' andato storto");
       alert.setContentText("ERRORE: " + str);
        alert.showAndWait();
    }

}

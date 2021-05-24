package it.unicam.cs.pa.jbudget.javafx;

import it.unicam.cs.pa.jbudget.controller.Ledger;
import it.unicam.cs.pa.jbudget.tag.TagException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxCreaTagController implements JavaFxInputControllerInterface {
    Ledger ledger;

    @FXML TextField nomeTagTextField;



    /**
     * Metodo che crea un tag con i dati presi in input e lo salva nel ledger quando viene premuto il bottone
     * Nel caso in cui venga sollevata un eccezione si apre una finestra di errore.
     */
    @FXML public void createButtonPushed(){
        try {
            ledger.addTag(getNomeTag());
        }
        catch (NumberFormatException e) { launchError("Valore non numerico"); }
        catch (NullPointerException ex) { launchError(VOID_ELEMENT); }
        catch (IllegalArgumentException e) { launchError(SAME_ELEMENT); }
        catch (TagException e) { launchError(INVALID_ELEMENT); }

    }


    /**
     * Metodo per tornare al menu principale quando viene premuto il bottone
     */
    @FXML public void returnButtonPushed(ActionEvent event) throws IOException {
        Parent menuParent = FXMLLoader.load(getClass().getResource("/JBudget.fxml"));
        Scene menuScene = new Scene(menuParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    }


    /**
     * Metodo usato per prendere il nome del conto dalla UI
     * @return La stringa inserita
     * @throws NullPointerException Se è vuoto
     */
    private String getNomeTag() throws NullPointerException {
        return nomeTagTextField.getText();
    }



    /**
     * Metodo usato per inizializzare gli elementi della UI e assegnare il ledger
     * @param ledger Ledger condiviso tra le viste
     */
    @Override
    public void initElements(Ledger ledger){
        this.ledger = ledger;

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

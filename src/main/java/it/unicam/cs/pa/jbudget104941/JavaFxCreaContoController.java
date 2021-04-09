package it.unicam.cs.pa.jbudget104941;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFxCreaContoController implements JavaFxInputControllerInterface {

    private Ledger ledger = new Ledger();

    /**
     * Campi per la creazione del conto
     */
    @FXML TextField nomeContoTextField;
    @FXML TextField bilancioContoTextField;
    @FXML ChoiceBox tipoContoChoiceBox;


    /**
     * Metodo che crea un conto con i dati presi in input e lo salva nel ledger quando viene premuto il bottone
     * Nel caso in cui venga sollevata un eccezione si apre una finestra di errore.
     */
    @FXML public void createButtonPushed(){
        try {
            ledger.addConto(getNomeConto(),getBilancioConto(),getTipoConto());
        }
        catch (NumberFormatException e) { launchError("Valore non numerico"); }
        catch (NullPointerException ex) { launchError(VOID_ELEMENT); }
        catch (IllegalArgumentException e) { launchError(SAME_ELEMENT); }
        catch (AccountException e) { launchError(INVALID_ELEMENT); }

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
     * Metodo usato per prendere il valore numerico del bilancio dalla UI
     * imposta forzatamente a zero se vuota
     * @return Il valore numerico da passare al conto
     * @throws NumberFormatException Se il valore non è un numero
     */
    private double getBilancioConto() throws NumberFormatException, NullPointerException {
        if (bilancioContoTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(bilancioContoTextField.getText());
    }

    /**
     * Metodo usato per prendere il nome del conto dalla UI
     * @return La stringa inserita
     * @throws NullPointerException Se è vuoto
     */
    private String getNomeConto() throws NullPointerException {
        return nomeContoTextField.getText();
    }

    /**
     * Metodo usato per prendere il tipo del conto dalla UI
     * @return Il tipo scelto
     * @throws NullPointerException Se è vuoto
     */
    private TipoConto getTipoConto() throws NullPointerException {
        String str = tipoContoChoiceBox.getValue().toString();
        TipoConto tipo = TipoConto.valueOf(str);

        return tipo;
    }

    /**
     * Metodo usato per inizializzare gli elementi della UI e assegnare il ledger
     * @param ledger Ledger condiviso tra le viste
     */
    @Override
    public void initElements(Ledger ledger){
        tipoContoChoiceBox.getItems().addAll("CREDITO", "DEBITO");
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



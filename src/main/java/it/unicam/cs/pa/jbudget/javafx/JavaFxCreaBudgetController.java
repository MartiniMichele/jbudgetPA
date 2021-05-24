package it.unicam.cs.pa.jbudget.javafx;

import it.unicam.cs.pa.jbudget.budget.BudgetException;
import it.unicam.cs.pa.jbudget.controller.Ledger;
import it.unicam.cs.pa.jbudget.tag.TagInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class JavaFxCreaBudgetController implements JavaFxInputControllerInterface {
    Ledger ledger;

    @FXML TextField descrizioneBudgetTextField;
    @FXML TextField importoBudgetTextField;
    @FXML ListView tagBudgetListView;
    @FXML TextArea tagBudgetSelezionatiTextArea;

    /**
     * Metodo che crea un movimento con i dati presi in input e lo salva nel ledger quando viene premuto il bottone
     * Nel caso in cui venga sollevata un eccezione si apre una finestra di errore.
     */
    @FXML public void createBudgetButtonPushed() {
        try {
            ledger.addBudget(getDescrizioneBudget(), getTagBudget(), getImportoBudget());
        }
        catch (NullPointerException e) { launchError(VOID_ELEMENT); }
        catch (NumberFormatException ex) { launchError("Valore non numerico"); }
        catch (IllegalArgumentException i) { launchError(SAME_ELEMENT); }
        catch (BudgetException b) { launchError(INVALID_ELEMENT); }
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
     * Popola la Text Area della UI per un riscontro visivo degli elementi selezionati
     */
    @FXML public void selectTagButtonPushed() {
        String textAreaString = "";

        ObservableList listOfTags = tagBudgetListView.getSelectionModel().getSelectedItems();

        for (Object elem : listOfTags) {
            textAreaString += String.format("%s%n", elem.toString());
        }
        tagBudgetSelezionatiTextArea.setText(textAreaString);
    }


    /**
     * Prende il valore dalla UI e lo ritorna per creare il budget
     * @return La stringa di descrizione del budget
     * @throws NullPointerException Se è nulla
     */
    private String getDescrizioneBudget() throws NullPointerException {
        return descrizioneBudgetTextField.getText();
    }


    /**
     * Prende il valore dalla UI e lo ritorna per creare il budget
     * Imposta forzatamente a zero se vuota
     * @return Valore dell'importo del budget
     * @throws NumberFormatException Se non è un numero
     */
    private double getImportoBudget() throws NullPointerException, NumberFormatException {
        if (importoBudgetTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(importoBudgetTextField.getText());
    }


    /**
     * Prende i valori dalla UI e li ritorna per creare il budget
     * @return La lista dei tag del budget
     * @throws NullPointerException Se è nulla
     */
    private <T extends TagInterface> List<T> getTagBudget() throws NullPointerException {
        List<T> tags = tagBudgetListView.getItems();
        return tags;
    }

    /**
     * Metodo usato per inizializzare gli elementi della UI e assegnare il ledger
     * @param ledger Ledger condiviso tra le viste
     */
    @Override
    public void initElements(Ledger ledger){
        this.ledger = ledger;
        initListView();
    }


    /**
     * Popola la List View della UI con i tag già creati
     */
    private void initListView(){
        tagBudgetListView.getItems().addAll(FXCollections.observableArrayList(ledger.getTags()));
        tagBudgetListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

package it.unicam.cs.pa.jbudget104941;

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

public class JavaFxCreaMovimentoController implements JavaFxInputControllerInterface {
    private Ledger ledger = new Ledger();

    @FXML ChoiceBox tipoMovimentoChoiceBox;
    @FXML TextField descrizioneMovimentoTextField;
    @FXML TextField importoMovimentoTextField;
    @FXML TextField contoMovimentoTextField;
    @FXML ListView tagMovimentoListView;
    @FXML TextArea tagSelezionatiTextArea;



    /**
     * Metodo che crea un movimento con i dati presi in input e lo salva nel ledger quando viene premuto il bottone
     * Nel caso in cui venga sollevata un eccezione si apre una finestra di errore.
     */
    @FXML public void createButtonPushed(){
        try { ledger.addMovement(getDescrizioneMovimento(), getImportoMovimento(), getTipoMovimento(), getTagMovimento(), getContoMovimento());}
        catch (NullPointerException e) { launchError(VOID_ELEMENT); }
        catch (NumberFormatException ex) { launchError("Valore non numerico"); }
        catch (IllegalArgumentException i) { launchError(SAME_ELEMENT); }
        catch (MovementException m) { launchError(INVALID_ELEMENT); }
    }

    /**
     * Popola la Text Area della UI per un riscontro visivo degli elementi selezionati
     */
    @FXML public void selectTagButtonPushed(){
        String textAreaString = "";

        ObservableList listOfTags = tagMovimentoListView.getSelectionModel().getSelectedItems();

        for (Object elem : listOfTags) {
            textAreaString += String.format("%s%n", (String) elem);
        }
        tagSelezionatiTextArea.setText(textAreaString);
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
     * Prende il valore dalla UI e lo ritorna per creare il movimento
     * @return La stringa che descrive il movimento
     * @throws NullPointerException Se è nulla
     */
    private String getDescrizioneMovimento() throws NullPointerException {
        return descrizioneMovimentoTextField.getText();
    }

    /**
     * Prende il valore dalla UI e lo ritorna per creare il movimento
     * @return La stringa rappresentante il nome del conto
     * @throws NullPointerException Se è nulla
     */
    private AccountInterface getContoMovimento() throws NullPointerException {
        String str = contoMovimentoTextField.getText();
        return ledger.getSingleAccount(str);
    }

    /**
     * Prende il valore dalla UI e lo ritorna per creare il movimento
     * Imposta forzatamente a zero se vuota
     * @return Valore dell'importo del movimento
     * @throws NumberFormatException Se non è un numero
     */
    private double getImportoMovimento() throws NullPointerException, NumberFormatException {
        if (importoMovimentoTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(importoMovimentoTextField.getText());
    }

    /**
     * Prende il valore dalla UI e lo ritorna per creare il movimento
     * @return Il tipo rappresentante il tipo del movimento
     * @throws NullPointerException Se è nulla
     */
    private TipoMovimento getTipoMovimento() throws NullPointerException {
        String str = tipoMovimentoChoiceBox.getValue().toString();
        TipoMovimento tipo = TipoMovimento.valueOf(str);

        return tipo;
    }

    /**
     * Prende i valori dalla UI e li ritorna per creare il movimento
     * @return La lista dei tag del movimento
     * @throws NullPointerException Se è nulla
     */
    private List<TagInterface> getTagMovimento() throws NullPointerException {
        List<TagInterface> tags = tagMovimentoListView.getItems();
        return tags;
    }

    /**
     * Metodo usato per inizializzare gli elementi della UI e assegnare il ledger
     * @param ledger Ledger condiviso tra le viste
     */
    @Override
    public void initElements(Ledger ledger) {
        tipoMovimentoChoiceBox.getItems().addAll("ENTRATA", "USCITA");
        this.ledger = ledger;
        initListView();
    }

    /**
     * Popola la List View della UI con i tag già creati
     */
    private void initListView() {
        tagMovimentoListView.getItems().addAll(FXCollections.observableArrayList(ledger.getTags()));
        tagMovimentoListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

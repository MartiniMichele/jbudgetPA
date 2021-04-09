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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class JavaFxViewTagController implements JavaFxInputControllerInterface {
    Ledger ledger;

    @FXML TextField filtraTagTextField;
    @FXML TableColumn<TagInterface, String> nomeTagTableColumn;
    @FXML TableColumn<TagInterface, String> idTagTableColumn;
    @FXML TableView<TagInterface> tagTableView;


    /**
     * Metodo che usa il ledger per salvare su file un backup dei tag
     */
    @FXML public void backupButtonPushed() {
        ledger.saveTag();
    }

    /**
     * Metodo che usa il ledger per ripristinare un backup dei tag da file
     */
    @FXML public void ripristinoButtonPushed() {
        ledger.ripristinaTag();
        initTableView();
    }

    /**
     * Metodo che usa il ledger per filtrare i tag
     */
    @FXML public void filtraButtonPushed() {
        try {
            if (getNomeTag().equals("tutti")) { initTableView(); }

            else {
                Predicate<TagInterface> predicate = p -> p.getNome().equals(getNomeTag());
                List<TagInterface> tags = ledger.tagFiltratiPer(predicate);
                tagTableView.setItems(getObservableTags(tags));
            }
        } catch (NullPointerException e) { launchError(VOID_ELEMENT); }

    }

    /**
     * Metodo che usa il ledger per eliminare i tag e aggiornare la UI
     */
    @FXML public void deleteButtonPushed(){
        List<TagInterface> lista = tagTableView.getSelectionModel().getSelectedItems();
        Predicate<TagInterface> condition = p -> {
            boolean answer = false;
            for (TagInterface elemento : lista) {
                if (p.getNome().equals(elemento.getNome())) { answer = true; }
            }
            return answer;
        };

        ledger.removeTag(condition);
        initTableView();

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
     * Metodo usato per prendere il nome del tag dalla UI usato per filtrare i tag
     * @return La stringa inserita
     * @throws NullPointerException Se è vuoto
     */
    private String getNomeTag() throws NullPointerException {
        return filtraTagTextField.getText();
    }


    /**
     * Metodo usato per inizializzare gli elementi della UI e assegnare il ledger
     * @param ledger Ledger condiviso tra le viste
     */
    @Override
    public void initElements(Ledger ledger) {
        this.ledger = ledger;
        initTableView();

    }


    /**
     * Metodo per inizializzare la table view dei tag e popolarla con i tag attualmente presenti nel ledger
     */
    private void initTableView(){
        nomeTagTableColumn.setCellValueFactory(new PropertyValueFactory<TagInterface, String>("nome"));
        idTagTableColumn.setCellValueFactory(new PropertyValueFactory<TagInterface, String>("id"));

        tagTableView.setItems(getObservableTags(ledger.getTags()));
        tagTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Metodo di supporto che prende la lista di tag del ledger e la trasforma in Observable List
     * per renderla utilizzabile dalla List View
     * @return La Observable List dei tag
     */
    private ObservableList<TagInterface> getObservableTags(List<TagInterface> lista) {
        ObservableList<TagInterface> tags = FXCollections.observableArrayList(lista);
        return tags;
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

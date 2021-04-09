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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class JavaFxViewMovimentiController implements JavaFxInputControllerInterface {

    Ledger ledger;

    @FXML TextField filtraImportoMinTextField;
    @FXML TextField filtraImportoMaxTextField;
    @FXML TableColumn<MovementInterface, String> descrizioneMovimentoTableColumn;
    @FXML TableColumn<MovementInterface, String> importoMovimentoTableColumn;
    @FXML TableColumn<MovementInterface, List<TagInterface>> tagMovimentoTableColumn;
    @FXML TableColumn<MovementInterface, TipoMovimento> tipoMovimentoTableColumn;
    @FXML TableColumn<MovementInterface, String> nomeContoMovimentoTableColumn;
    @FXML TableView<MovementInterface> movimentoTableView;


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
     * Metodo che usa il ledger per eliminare i movimenti e aggiornare la UI
     */
    @FXML public void deleteButtonPushed(){
        List<MovementInterface> lista = movimentoTableView.getSelectionModel().getSelectedItems();
        Predicate<MovementInterface> condition = p -> {
            boolean answer = false;
            for (MovementInterface elemento : lista) {
                if (p.getId() == elemento.getId()) { answer = true; }
            }
            return answer;
        };

        ledger.removeMovement(condition);
        initTableView();

    }


    /**
     * Metodo che usa aggiorna la vista della tabella
     */
    @FXML public void refreshButtonPushed() {
        initTableView();
    }


    /**
     * Metodo che usa il ledger per ripristinare un backup dei movimenti da file
     */
    @FXML public void ripristinoButtonPushed() {
        ledger.ripristinaMovimenti();
        initTableView();
    }

    /**
     * Metodo che usa il ledger per salvare su file un backup dei movimenti
     */
    @FXML public void backupButtonPushed() {
        ledger.saveMovimenti();
    }


    /**
     * Metodo che usa il ledger per filtrare i movimenti con importo minore di quello inserito
     */
    @FXML public void filtraMinButtonPushed() {
        try {
            double imp = getImportoMinMovimento();
            Predicate<MovementInterface> predicate = p -> p.getImporto() < imp;
            List<MovementInterface> list = ledger.movimentiFiltratiPer(predicate);
            movimentoTableView.setItems(getObservableMovimento(list));
        }
        catch (NullPointerException e) { launchError(VOID_ELEMENT); }
        catch (NumberFormatException ex) { launchError("Valore non numerico"); }

    }


    /**
     * Metodo che usa il ledger per filtrare i movimenti con importo maggiore di quello inserito
     */
    @FXML public void filtraMaxButtonPushed() {
        try {
            double imp = getImportoMaxMovimento();
            Predicate<MovementInterface> predicate = p -> p.getImporto() > imp;
            List<MovementInterface> list = ledger.movimentiFiltratiPer(predicate);
            movimentoTableView.setItems(getObservableMovimento(list));
        }
          catch (NullPointerException e) { launchError(VOID_ELEMENT); }
          catch (NumberFormatException ex) { launchError("Valore non numerico"); }

    }


    /**
     * Prende il valore dalla UI e lo ritorna per filtrare i movimenti con importo minore di quello inserito
     * Imposta forzatamente a zero se vuota
     * @return Valore dell'importo del movimento
     * @throws NumberFormatException Se non è un numero
     */
    private double getImportoMinMovimento() throws NullPointerException, NumberFormatException {
        if (filtraImportoMinTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(filtraImportoMinTextField.getText());
    }


    /**
     * Prende il valore dalla UI e lo ritorna per filtrare i movimenti con importo maggiore di quello inserito
     * Imposta forzatamente a zero se vuota
     * @return Valore dell'importo del movimento
     * @throws NumberFormatException Se non è un numero
     */
    private double getImportoMaxMovimento() throws NullPointerException, NumberFormatException {
        if (filtraImportoMaxTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(filtraImportoMaxTextField.getText());
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
     * Metodo per inizializzare la table view dei movimenti e popolarla con quelli attualmente presenti nel ledger
     */
    private void initTableView() {
        descrizioneMovimentoTableColumn.setCellValueFactory(new PropertyValueFactory<MovementInterface, String>("descrizione"));
        importoMovimentoTableColumn.setCellValueFactory(new PropertyValueFactory<MovementInterface, String>("importo"));
        tipoMovimentoTableColumn.setCellValueFactory(new PropertyValueFactory<MovementInterface, TipoMovimento>("direzione"));
        tagMovimentoTableColumn.setCellValueFactory(new PropertyValueFactory<MovementInterface, List<TagInterface>>("tagList"));
        nomeContoMovimentoTableColumn.setCellValueFactory(new PropertyValueFactory<MovementInterface, String>("account"));

        movimentoTableView.setItems(getObservableMovimento(ledger.getMovements()));
        movimentoTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Metodo di supporto che prende la lista di movimenti del ledger e la trasforma in Observable List
     * per renderla utilizzabile dalla List View
     * @return La Observable List dei movimenti
     */
    private ObservableList<MovementInterface> getObservableMovimento(List<MovementInterface> lista) {
        ObservableList<MovementInterface> movimenti = FXCollections.observableArrayList(lista);
        return movimenti;
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

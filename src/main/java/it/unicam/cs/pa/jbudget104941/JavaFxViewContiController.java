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

public class JavaFxViewContiController implements JavaFxInputControllerInterface {
    Ledger ledger;

    @FXML TextField filtraNomeTextField;
    @FXML TextField filtraBilancioTextField;
    @FXML TableColumn<AccountInterface, String> nomeContoTableColumn;
    @FXML TableColumn<AccountInterface, String> bilancioContoTableColumn;
    @FXML TableColumn<AccountInterface, TipoConto> tipoContoTableColumn;
    @FXML TableView<AccountInterface> contiTableView;




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
     * Metodo che usa il ledger per eliminare i conti e aggiornare la UI
     */
    @FXML public void deleteButtonPushed(){
        List<AccountInterface> lista = contiTableView.getSelectionModel().getSelectedItems();
        Predicate<AccountInterface> condition = p -> {
            boolean answer = false;
            for (AccountInterface elemento : lista) {
                if (p.getNome().equals(elemento.getNome())) { answer = true; }
            }
            return answer;
        };

        ledger.removeConto(condition);
        initTableView();

    }

    /**
     * Metodo che usa il ledger per filtrare i conti in base al bilancio
     */
    @FXML public void filtraBilancioButtonPushed() {
        try {
                Predicate<AccountInterface> predicate = p -> p.getBilancio() > getBilancioConto();
                List<AccountInterface> conti = ledger.contiFiltratiPer(predicate);
                contiTableView.setItems(getObservableAccounts(conti));
            }
        catch (NullPointerException e) { launchError(VOID_ELEMENT); }
        catch (NumberFormatException n) { launchError("Valore non numerico"); }
        }


    /**
     * Metodo che usa il ledger per filtrare i conti in base al nome
     */
    @FXML public void filtraNomeButtonPushed() {
        try {
            if (getNomeConto().equals("tutti")) { initTableView(); }

            else {
                Predicate<AccountInterface> predicate = p -> p.getNome().equals(getNomeConto());
                List<AccountInterface> conti = ledger.contiFiltratiPer(predicate);
                contiTableView.setItems(getObservableAccounts(conti));
            }
        } catch (NullPointerException e) { launchError(VOID_ELEMENT); }

    }

    /**
     * Metodo che usa il ledger per ripristinare un backup dei conti da file
     */
    @FXML public void ripristinoButtonPushed() {
        ledger.ripristinaConti();
        initTableView();
    }

    /**
     * Metodo che usa il ledger per salvare su file un backup dei conti
     */
    @FXML public void backupButtonPushed() {
        ledger.saveConti();
    }

    /**
     * Metodo usato per prendere il nome del conto dalla UI
     * @return La stringa inserita
     */
    private String getNomeConto() {
        return filtraNomeTextField.getText();
    }

    /**
     * Metodo usato per prendere il valore numerico del bilancio dalla UI
     * imposta forzatamente a zero se vuota
     * @return Il valore numerico da passare al conto
     * @throws NumberFormatException Se il valore non è un numero
     */
    private double getBilancioConto() throws NumberFormatException, NullPointerException {
        if (filtraBilancioTextField.getText().equals("")) { return 0; }

        return Double.parseDouble(filtraBilancioTextField.getText());
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
     * Metodo per inizializzare la table view dei conti e popolarla con quelli attualmente presenti nel ledger
     */
    private void initTableView() {
        nomeContoTableColumn.setCellValueFactory(new PropertyValueFactory<AccountInterface, String>("nome"));
        bilancioContoTableColumn.setCellValueFactory(new PropertyValueFactory<AccountInterface, String>("bilancio"));
        tipoContoTableColumn.setCellValueFactory(new PropertyValueFactory<AccountInterface, TipoConto>("tipo"));

        contiTableView.setItems(getObservableAccounts(ledger.getConti()));
        contiTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Metodo di supporto che prende la lista di conti del ledger e la trasforma in Observable List
     * per renderla utilizzabile dalla List View
     * @return La Observable List dei conti
     */
    private ObservableList<AccountInterface> getObservableAccounts(List<AccountInterface> lista) {
        ObservableList<AccountInterface> conti = FXCollections.observableArrayList(lista);
        return conti;
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

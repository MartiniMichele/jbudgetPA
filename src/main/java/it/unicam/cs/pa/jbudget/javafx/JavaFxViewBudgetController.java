package it.unicam.cs.pa.jbudget.javafx;

import it.unicam.cs.pa.jbudget.budget.BudgetInterface;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class JavaFxViewBudgetController implements JavaFxInputControllerInterface {
    Ledger ledger;

    @FXML TextField filtraNomeTextField;
    @FXML TableColumn<BudgetInterface, String> nomeBudgetTableColumn;
    @FXML TableColumn<BudgetInterface, String> expectedBudgetTableColumn;
    @FXML TableColumn<BudgetInterface, List<TagInterface>> tagBudgetTableColumn;
    @FXML TableView<BudgetInterface> budgetTableView;


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
     * Metodo che usa il ledger per eliminare i budget e aggiornare la UI
     */
    @FXML public void deleteButtonPushed(){
        List<BudgetInterface> lista = budgetTableView.getSelectionModel().getSelectedItems();
        Predicate<BudgetInterface> condition = p -> {
            boolean answer = false;
            for (BudgetInterface elemento : lista) {
                if (p.getNome().equals(elemento.getNome())) { answer = true; }
            }
            return answer;
        };

        ledger.removeBudget(condition);
        initTableView();

    }


    /**
     * Metodo che usa il ledger per filtrare i budget in base al nome
     */
    @FXML public void filtraNomeButtonPushed() {
        try {
            if (getNomeBudget().equals("tutti")) { initTableView(); }

            else {
                Predicate<BudgetInterface> predicate = p -> p.getNome().equals(getNomeBudget());
                List<BudgetInterface> budgets = ledger.budgetFiltratiPer(predicate);
                budgetTableView.setItems(getObservableBudget(budgets));
            }
        } catch (NullPointerException e) { launchError(VOID_ELEMENT); }

    }


    /**
     * Metodo che usa il ledger per ripristinare un backup dei budget da file
     */
    @FXML public void ripristinoButtonPushed() {
        ledger.ripristinaTag();
        initTableView();
    }

    /**
     * Metodo che usa il ledger per salvare su file un backup dei budget
     */
    @FXML public void backupButtonPushed() {
        ledger.saveBudget();
    }


    /**
     * Metodo usato per prendere il nome del budget dalla UI
     * @return La stringa inserita
     */
    private String getNomeBudget() {
        return filtraNomeTextField.getText();
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
     * Metodo per inizializzare la table view dei budget e popolarla con quelli attualmente presenti nel ledger
     */
    private void initTableView() {
        nomeBudgetTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetInterface, String>("nome"));
        expectedBudgetTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetInterface, String>("expected"));
        tagBudgetTableColumn.setCellValueFactory(new PropertyValueFactory<BudgetInterface, List<TagInterface>>("tags"));

        budgetTableView.setItems(getObservableBudget(ledger.getBudgets()));
        budgetTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Metodo di supporto che prende la lista di budget del ledger e la trasforma in Observable List
     * per renderla utilizzabile dalla List View
     * @return La Observable List dei budget
     */
    private ObservableList<BudgetInterface> getObservableBudget(List<BudgetInterface> lista) {
        ObservableList<BudgetInterface> budgets = FXCollections.observableArrayList(lista);
        return budgets;
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

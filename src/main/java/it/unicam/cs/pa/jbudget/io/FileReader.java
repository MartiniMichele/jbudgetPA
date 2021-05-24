package it.unicam.cs.pa.jbudget.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.pa.jbudget.account.AccountInterface;
import it.unicam.cs.pa.jbudget.account.AccountInterfaceDeserializer;
import it.unicam.cs.pa.jbudget.budget.BudgetInterface;
import it.unicam.cs.pa.jbudget.budget.BudgetInterfaceDeserializer;
import it.unicam.cs.pa.jbudget.movement.MovementInterface;
import it.unicam.cs.pa.jbudget.movement.MovementInterfaceAdapter;
import it.unicam.cs.pa.jbudget.tag.TagInterface;
import it.unicam.cs.pa.jbudget.tag.TagInterfaceDeserializer;
import it.unicam.cs.pa.jbudget.controller.Ledger;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe adibita alla lettura dei file di backup dell'applicazione. i file sono salvati in formato Json
 * Se gli oggetti letti dal file sono già presenti il ledger non li salva in memoria
 * Implementa {@link ReaderInterface}
 */
public class FileReader implements ReaderInterface {

    /**
     * Elenco dei path dei file
     */
    private String contoPath = "src\\main\\files\\Conto.Json";
    private String movimentoPath = "src\\main\\files\\Movimento.Json";
    private String tagPath = "src\\main\\files\\Tag.Json";
    private String BudgetPath = "src\\main\\files\\Budget.Json";


    /**
     * Metodo che ha il compito di leggere i Json da file trasformarli in tag e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     * Viene utilizzato un deserializzatore personalizzato
     */
    public void readTag() {
        Ledger ledger = new Ledger();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(TagInterface.class, new TagInterfaceDeserializer<>());
        Gson gson = builder.create();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(tagPath));
            Type tagListType = new TypeToken<ArrayList<TagInterface>>(){}.getType();
            ArrayList<TagInterface> tags = gson.fromJson(reader, tagListType);
            ledger.addTagList(tags);
        }
        catch (IOException e){ e.printStackTrace(); }
    }


    /**
     * Metodo che ha il compito di leggere i Json da file trasformarli in conti e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     * Viene utilizzato un deserializzatore personalizzato per distinguere i due diversi tipi di conto
     */
    public void readConti() {
        Ledger ledger = new Ledger();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(AccountInterface.class, new AccountInterfaceDeserializer<>());
        Gson gson = builder.create();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(contoPath));
            Type contoListType = new TypeToken<ArrayList<AccountInterface>>(){}.getType();
            ArrayList<AccountInterface> accounts = gson.fromJson(reader, contoListType);
            ledger.addContiList(accounts);
        }
        catch (IOException e){ e.printStackTrace(); }
    }

    /**
     * Metodo che ha il compito di leggere i Json da file trasformarli in movimenti e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     * Viene utilizzato un deserializzatore personalizzato
     */
    public void readMovimenti() {
        Ledger ledger = new Ledger();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(MovementInterface.class, new MovementInterfaceAdapter<>());
        Gson gson = builder.create();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(movimentoPath));
            Type movimentoListType = new TypeToken<ArrayList<MovementInterface>>(){}.getType();
            ArrayList<MovementInterface> movements = gson.fromJson(reader, movimentoListType);
            ledger.addMovimentiList(movements);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che ha il compito di leggere i Json da file trasformarli in budget e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     * Viene utilizzato un deserializzatore personalizzato
     */
    public void readBudget(){
        Ledger ledger = new Ledger();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BudgetInterface.class, new BudgetInterfaceDeserializer<>());
        Gson gson = builder.create();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get(BudgetPath));
            Type budgetListType = new TypeToken<ArrayList<BudgetInterface>>(){}.getType();
            ArrayList<BudgetInterface> budgets = gson.fromJson(reader, budgetListType);
            ledger.addBudgetList(budgets);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



}

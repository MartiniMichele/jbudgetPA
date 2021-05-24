package it.unicam.cs.pa.jbudget.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.pa.jbudget.account.AccountInterface;
import it.unicam.cs.pa.jbudget.budget.BudgetInterface;
import it.unicam.cs.pa.jbudget.movement.MovementInterface;
import it.unicam.cs.pa.jbudget.tag.TagInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileSaver implements WriterInterface {

    /**
     * Percorsi su cui salvare i backup
     */
    private String contoPath = "src\\main\\files\\Conto.Json";
    private String movimentoPath = "src\\main\\files\\Movimento.Json";
    private String TagPath = "src\\main\\files\\Tag.Json";
    private String BudgetPath = "src\\main\\files\\Budget.Json";



    /**
     * Metodo che ha il compito di salvare su file un backup dei conti.
     */
    public <T extends AccountInterface> void writeConto(List<T> lista) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer;

        try {
            createFile(contoPath);
            writer = Files.newBufferedWriter(Paths.get(contoPath));
            writer.write(gson.toJson(lista));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Metodo che ha il compito di salvare su file un backup dei movimenti.
     */
    public <T extends MovementInterface> void  writeMovimento(List<T> lista) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer;
        try {
            createFile(movimentoPath);
            writer = Files.newBufferedWriter(Paths.get(movimentoPath));
            writer.write(gson.toJson(lista));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Metodo che ha il compito di salvare su file un backup dei tag.
     */
    public <T extends TagInterface> void writeTag(List<T> lista)  {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer;
        try {
            createFile(TagPath);
            writer = Files.newBufferedWriter(Paths.get(TagPath));
            writer.write(gson.toJson(lista));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Metodo che ha il compito di salvare su file un backup dei budget.
     */
    public <T extends BudgetInterface> void writeBudget(List<T> lista){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Writer writer;
        try {
            createFile(BudgetPath);
            writer = Files.newBufferedWriter(Paths.get(BudgetPath));
            writer.write(gson.toJson(lista));
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    /**
     * Metodo che si occupa di controllare se il file del backup è presente.
     * In caso contrario lo crea
     * @param path Percorso dove salvare il file
     * @throws IOException Se percorso non valido
     */
    public void createFile(String path) {

        File file = new File(path);

        if (!file.exists())  {
           try {
               file.createNewFile();
           }
           catch(IOException e) { e.printStackTrace(); }
        }
    }
}

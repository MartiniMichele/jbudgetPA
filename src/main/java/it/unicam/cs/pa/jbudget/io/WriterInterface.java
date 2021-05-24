package it.unicam.cs.pa.jbudget.io;

import it.unicam.cs.pa.jbudget.account.AccountInterface;
import it.unicam.cs.pa.jbudget.budget.BudgetInterface;
import it.unicam.cs.pa.jbudget.movement.MovementInterface;
import it.unicam.cs.pa.jbudget.tag.TagInterface;

import java.io.IOException;
import java.util.List;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di scrivere un backup su file.
 */

public interface WriterInterface {

    /**
     * Metodo che ha il compito di salvare su file un backup dei conti.
     */
    <T extends AccountInterface> void writeConto(List<T> lista);



    /**
     * Metodo che ha il compito di salvare su file un backup dei movimenti.
     */
    <T extends MovementInterface> void  writeMovimento(List<T> lista);



    /**
     * Metodo che ha il compito di salvare su file un backup dei tag.
     */
    <T extends TagInterface> void writeTag(List<T> lista);



    /**
     * Metodo che ha il compito di salvare su file un backup dei budget.
     */
    public <T extends BudgetInterface> void writeBudget(List<T> lista);



    /**
     * Metodo che si occupa di controllare se il file del backup è presente.
     * In caso contrario lo crea
     * @param path Percorso dove salvare il file
     * @throws IOException Se percorso non valido
     */
    void createFile(String path) throws IOException;
}

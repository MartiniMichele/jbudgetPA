package it.unicam.cs.pa.jbudget.io;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di leggere il backup da file.
 */

public interface ReaderInterface {
    /**
     * Metodo che ha il compito di leggere i backup dei tag e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     */
    void readTag();

    /**
     * Metodo che ha il compito di leggere i backup dei conti e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     */
    void readConti();

    /**
     * Metodo che ha il compito di leggere i backup dei movimenti e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     */
    void readMovimenti();

    /**
     * Metodo che ha il compito di leggere i backup dei budget e aggiungerli ad una lista
     * la lista sarà passata al ledger che aggiungera gli oggetti mancanti.
     */
    void readBudget();
}

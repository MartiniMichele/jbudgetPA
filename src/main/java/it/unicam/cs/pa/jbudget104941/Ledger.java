package it.unicam.cs.pa.jbudget104941;


import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Classe che gestisce tutti i dati dell'applicazione.
 * ha salvati in memoria diverse liste per movimenti, conti, tag e budget
 */
public class Ledger implements LedgerInterface {

    private static  List<AccountInterface> conti = new ArrayList<>();      //Lista dei conti presenti
    private static List<MovementInterface> movimenti = new ArrayList<>();      //Lista dei movimenti presenti
    private static List<TagInterface> categorie = new ArrayList<>();        //Lista delle categorie presenti
    private static List<BudgetInterface> budgets = new ArrayList<>();      //lista dei budget presenti


    BudgetManagerInterface budgetManager = new BudgetManager();
    WriterInterface writer = new FileSaver();
    ReaderInterface reader = new FileReader();


    /**
     * Costruttore del ledger
     */
    public Ledger() {
    }

    /**
     * Questo metodo crea un conto e lo salva nella rispettiva lista.
     * Non possono essere creati piu' conti con lo stesso nome
     * @param nome Nome del conto
     * @param denaro Quantita' di denaro presente nel conto
     * @param tipo Tipo del conto, se credito o debito
     * @throws IllegalArgumentException Quando il conto e' gia' presente nella lista
     */
    @Override
    public void addConto(String nome, double denaro, TipoConto tipo) throws  IllegalArgumentException, AccountException{
        if (!sameConto(nome)) {
            switch (tipo) {
                case CREDITO:
                    AccountCredito Credito = new AccountCredito(nome, denaro, tipo);
                    conti.add(Credito);
                    break;
                case DEBITO:
                    AccountDebito Debito = new AccountDebito(nome, denaro, tipo);
                    conti.add(Debito);
                    break;
            }
        }
        else { throw new IllegalArgumentException(); }
    }

    /**
     * Questo metodo crea un movimento e lo salva nella rispettiva lista.
     * Non possono essere creati piu' movimenti con lo stesso id.
     * @param descrizione Descrizione del movimento
     * @param importo Quantita' di denaro spostata dal movimento
     * @param direzione Direzione del movimento, se entrata o uscita
     * @param categorie Rappresenta la lista dei tag associati al movimento
     * @param conto Il conto associato al movimento
     * @throws IllegalArgumentException Quando il movimento esiste gia'
     * @throws MovementException Quando alcuni parametri inseriti non sono validi
     */
    @Override
    public void addMovement(String descrizione, double importo, TipoMovimento direzione, List<TagInterface> categorie, AccountInterface conto) throws IllegalArgumentException, IndexOutOfBoundsException, MovementException {
        if (sameConto(conto.getNome())) {
            switch (direzione) {
                case ENTRATA:
                    Movement Entrata = new Movement(descrizione, importo, direzione, categorie, conto.getNome());
                    if (!sameMovimento(Entrata.getId())) {
                        Entrata.addMoney(conto);
                        conto.update(Entrata);
                        movimenti.add(Entrata);
                        aggiornaBilancio(Entrata, conto);
                    }
                    else { throw new IllegalArgumentException(); }
                    break;
                case USCITA:
                    Movement Uscita = new Movement(descrizione, importo, direzione, categorie, conto.getNome());
                    if (!sameMovimento(Uscita.getId())) {
                        Uscita.removeMoney(conto);
                        conto.update(Uscita);
                        movimenti.add(Uscita);
                        aggiornaBilancio(Uscita, conto);
                        aggiornaBudget(Uscita);
                        Predicate<AccountInterface> predicate = p -> p.getBilancio() == 0;
                        conti.removeIf(predicate);
                    }
                    else { throw new IllegalArgumentException(); }
                    break;
            }
        }


    }

    /**
     * Questo metodo crea un tag e lo salva nella rispettiva lista.
     * Non possono essere creati piu' tag con lo stesso nome.
     * @param nome Nome del tag
     * @throws IllegalArgumentException Quando esiste gia' un tag con lo stesso nome
     */
    @Override
    public void addTag(String nome) throws IllegalArgumentException, TagException {
        if (!sameTag(nome)) {
            TagInterface tag = new Tag(nome);
            categorie.add(tag);
        }
        else { throw new IllegalArgumentException(); }
    }

    /**
     * Questo metodo crea un budget e lo salva nella rispettiva lista.
     * Non possono essere creati piu' budget con lo stesso nome.
     * @param nome Nome del budget
     * @param tag Lista dei tag associati
     * @param expected Spesa totale prevista dal budget
     * @throws IllegalArgumentException
     */
    @Override
    public void addBudget(String nome, List<TagInterface> tag, double expected) throws IllegalArgumentException, BudgetException {
        if (!sameBudget(nome)){
            BudgetInterface budget = new Budget(nome, tag, expected);
            budgets.add(budget);
        }
        else { throw new IllegalArgumentException(); }
    }


    /**
     * Metodo che presa una lista di tag aggiunge i suoi elementi alla lista presente nel ledger.
     * Usato per ripristinare i dati dal file.
     * @param lista Lista di elementi da aggiungere
     * @param <T> Parametro generico rappresentante un tag
     */
    public <T extends TagInterface> void addTagList (ArrayList<T> lista) {
        for (T elem : lista) {
            if (!sameTag(elem.getNome())){ categorie.add(elem); }
        }
    }

    /**
     * Metodo che presa una lista di conti aggiunge i suoi elementi alla lista presente nel ledger.
     * Usato per ripristinare i dati dal file.
     * @param lista Lista di elementi da aggiungere
     * @param <T> Parametro generico rappresentante un account
     */
    public <T extends AccountInterface> void addContiList (ArrayList<T> lista) {
        for (T elem : lista) {
            if (!sameConto(elem.getNome())){ conti.add(elem); }
        }
    }

    /**
     * Metodo che presa una lista di movimenti aggiunge i suoi elementi alla lista presente nel ledger.
     * Usato per ripristinare i dati dal file.
     * @param lista Lista di elementi da aggiungere
     * @param <T> Parametro generico rappresentante un movimento
     */
    public <T extends MovementInterface> void addMovimentiList (ArrayList<T> lista) {
        for (T elem : lista) {
            if (!sameMovimento(elem.getId())){ movimenti.add(elem); }
        }
    }

    /**
     * Metodo che presa una lista di budget aggiunge i suoi elementi alla lista presente nel ledger.
     * Usato per ripristinare i dati dal file.
     * @param lista Lista di elementi da aggiungere
     * @param <T> Parametro generico rappresentante un budget
     */
    public <T extends BudgetInterface> void addBudgetList (ArrayList<T> lista) {
        for (T elem : lista) {
            if (!sameBudget(elem.getNome())) { budgets.add(elem); }
        }
    }

    /**
     * Metodo usato per rimuovere un conto dal ledger se la condizione risulta vera.
     * @param condition Predicate preso in input, filtra i conti da eliminare.
     */
    @Override
    public void removeConto(Predicate<AccountInterface> condition){
        conti.removeIf(condition);
    }

    /**
     * Metodo usato per rimuovere un movimento dal ledger se la condizione risulta vera.
     * @param condition Predicate preso in input, filtra i movimenti da eliminare.
     */
    @Override
    public void removeMovement(Predicate<MovementInterface> condition){
        movimenti.removeIf(condition);
    }

    /**
     * Metodo usato per rimuovere un tag dal ledger se la condizione risulta vera.
     * @param condition Predicate preso in input, filtra i tag da eliminare.
     */
    @Override
    public void removeTag(Predicate<TagInterface> condition){
        categorie.removeIf(condition);
    }

    /**
     * Metodo usato per rimuovere un budget dal ledger se la condizione risulta vera.
     * @param condition Predicate preso in input, filtra i budget da eliminare.
     */
    @Override
    public void removeBudget(Predicate<BudgetInterface> condition) {
        budgets.removeIf(condition);
    }


    @Override
    public List<AccountInterface> getConti() {
        return conti;
    }

    @Override
    public List<MovementInterface> getMovements() {
        return movimenti;
    }

    @Override
    public  List<TagInterface> getTags() {
        return categorie;
    }

    public List<BudgetInterface> getBudgets() {
        return budgets;
    }

    /**
     * Metodo per visualizzare una lista di movimenti secondo una condizione presa in input.
     * @param predicate Predicate preso in input, filtra i movimenti da visualizzare
     * @return La lista filtrata
     */
    @Override
    public List<MovementInterface> movimentiFiltratiPer(Predicate<MovementInterface> predicate){
        List<MovementInterface> lista = new ArrayList<>();
        lista = movimenti;
        lista = lista.stream().filter(predicate).collect(Collectors.toList());
        return lista;

    }

    /**
     * Metodo per visualizzare una lista di conti secondo una condizione presa in input.
     * @param predicate Predicate preso in input, filtra i conti da visualizzare
     * @return La lista filtrata
     */
    @Override
    public List<AccountInterface> contiFiltratiPer(Predicate<AccountInterface> predicate){
        List<AccountInterface> lista = new ArrayList<>();
        lista = conti;
        lista = lista.stream().filter(predicate).collect(Collectors.toList());
        return lista;
    }

    /**
     * Metodo per visualizzare una lista di tag secondo una condizione presa in input.
     * @param predicate Predicate preso in input, filtra i tag da visualizzare
     * @return La lista filtrata
     */
    @Override
    public List<TagInterface> tagFiltratiPer(Predicate<TagInterface> predicate){
        List<TagInterface> lista = new ArrayList<>();
        lista = categorie;
        lista = lista.stream().filter(predicate).collect(Collectors.toList());
        return lista;
    }

    /**
     * Metodo per visualizzare una lista di budget secondo una condizione presa in input.
     * @param predicate Predicate preso in input, filtra i budget da visualizzare
     * @return La lista filtrata
     */
    @Override
    public List<BudgetInterface> budgetFiltratiPer(Predicate<BudgetInterface> predicate){
        List<BudgetInterface> lista = new ArrayList<>();
        lista = budgets;
        lista = lista.stream().filter(predicate).collect(Collectors.toList());
        return lista;
    }

    /**
     * Una funzione che prende in input il nome di un conto e verifica se è uguale ad uno dei conti nella lista
     * @param nome Nome con cui effettuare la verifica
     * @return booleano, vero se uguali e falso se diversi
     */
        private boolean sameConto(String nome){
            boolean answer = false;
        for (AccountInterface elem : conti) {
            if (elem.getNome().equals(nome))
            { answer = true; }
        }
        return answer;
    }

    /**
     * Una funzione che prende in input il codice di un movimento e verifica se è uguale ad uno dei movimenti nella lista
     * @param codice Codice con cui effettuare la verifica
     * @return booleano, vero se uguali e falso se diversi
     */
    private boolean sameMovimento(int codice){
        boolean answer = false;
        for (MovementInterface elem : movimenti){
            if (elem.getId() == codice)
            { answer = true; }
        }
        return answer;
    }

    /**
     * Una funzione che prende in input il nome di un tag e verifica se è uguale ad uno dei tag nella lista
     * @param nome Nome con cui effettuare la verifica
     * @return booleano, vero se guali e falso se diversi
     */
    private boolean sameTag(String nome){
        boolean answer = false;
        for (TagInterface elem : categorie) {
            if (elem.getNome().equals(nome)){ answer = true; }
        }
        return answer;
    }

    /**
     * Una funzione che prende in input il nome di un budget e verifica se è uguale ad uno dei budget nella lista
     * @param nome Nome con cui effettuare la verifica
     * @return booleano, vero se guali e falso se diversi
     */
    private boolean sameBudget(String nome){
        boolean answer = false;
        for (BudgetInterface elem : budgets) {
            if (elem.getNome().equals(nome)) { answer = true; }
        }
        return answer;
    }


    /**
     * Una funzione che prende in input un movimento e verifica se il bilancio del conto associato
     * è uguale o diverso al suo omonimo. In caso negativo aggiorna il bilancio del conto nella lista
     * @param move Movimento che effettua lo spostamento di denaro
     * @param conto Conto associato al movimento
     */
    private void aggiornaBilancio(MovementInterface move, AccountInterface conto){
        final double threshold = .0001;
        for (AccountInterface elem : conti) {
            if (elem.getNome().equals(conto.getNome())) {
                double bilancio = elem.getBilancio();
                if (Math.abs(bilancio - move.getBilancioConto(conto)) < threshold) {  } else elem.setBilancio(move.getBilancioConto(conto));
            }
        }
    }

    /**
     * Metodo per aggiornare le variabili "expected" e "disponibile" del budget con gli stessi tag del movimento
     * @param movement Lista dei tag del movimento per usati per identificare il budget
     */
    private void aggiornaBudget(MovementInterface movement) throws IndexOutOfBoundsException {
        if (budgets.isEmpty()) { throw new IndexOutOfBoundsException(); }
        else {
            BudgetInterface budget = extractBudget(movement.getTagList());
            budgetManager.createReporter(budget);
            budgetManager.createreport(movement.getImporto());
        }
    }

    /**
     * Metodo di supporto per trovare un Budget nella lista dei budget del ledger
     * @param lista Lista di tag per identificare il budget
     * @return Il budget che rispetta i criteri di ricerca
     */
    private <T extends TagInterface> BudgetInterface extractBudget(List<T> lista){
        Predicate<BudgetInterface> predicate = p -> lista.containsAll(p.getTags());
        List<BudgetInterface> budget = new ArrayList<>();
        budget = budgetFiltratiPer(predicate);
        return budget.get(0);
    }

    /**
     * Metodo di supporto per i controller della UI. Permette di ottenere il conto associato ad un nome
     * @param nome Nome inserito dall'utente
     * @return Il conto associato
     * @throws NullPointerException Se la lista è vuota e il conto non esiste
     */
    public AccountInterface getSingleAccount(String nome) throws NullPointerException {
        Predicate<AccountInterface> predicate = p -> p.getNome().equals(nome);
        List<AccountInterface> list = contiFiltratiPer(predicate);
        return list.get(0);
    }

    /**
     * Metodo che usa un writer per effettuare il backup dei tag
     */
    public void saveTag() {
        writer.writeTag(categorie);
    }

    /**
     * Metodo che usa un writer per effettuare il backup dei conti
     */
    public void saveConti() {
        writer.writeConto(conti);
    }

    /**
     * Metodo che usa un writer per effettuare il backup dei Movimenti
     */
    public void saveMovimenti() {
        writer.writeMovimento(movimenti);
    }

    /**
     * Metodo che usa un writer per effettuare il backup dei budget
     */
    public void saveBudget() {
        writer.writeBudget(budgets);
    }

    /**
     * Metodo che usa un reader per ripristinare i tag
     */
    public void ripristinaTag() {
        reader.readTag();
    }

    /**
     * Metodo che usa un reader per ripristinare i conti
     */
    public void ripristinaConti() {
        reader.readConti();
    }

    /**
     * Metodo che usa un reader per ripristinare i movimenti
     */
    public void ripristinaMovimenti() {
        reader.readMovimenti();
    }

    /**
     * Metodo che usa un reader per ripristinare i budget
     */
    public void ripristinaBudget() {
        reader.readBudget();
    }

    }


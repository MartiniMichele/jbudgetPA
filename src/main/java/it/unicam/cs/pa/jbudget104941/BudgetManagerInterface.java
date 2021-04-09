package it.unicam.cs.pa.jbudget104941;


/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link BudgetManager}.
 */
public interface BudgetManagerInterface {
    /**
     * Metodo usato per creare un BudgetReport
     * @param budget Budget associato al BudgetReport
     */
    void createReporter(BudgetInterface budget);
    /**
     * Metodo usato per creare un report ed aggiornare il denaro disponibile del budget
     * @param importo Importo del movimento
     * @return La spesa effettuata
     */
    double createreport(double importo);


    BudgetReport getBudgetReport();
}

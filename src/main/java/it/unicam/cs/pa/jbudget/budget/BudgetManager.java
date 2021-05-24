package it.unicam.cs.pa.jbudget.budget;

/**
 * Classe addetta alla creazione di BudgetReport associati ad un ledger e un budget
 * l'accesso e la modifica delle informazioni associate avviene tramite i rispettivi getter e setter
 * Implementa {@link BudgetManagerInterface}
 */
public class BudgetManager implements BudgetManagerInterface {
    BudgetReport budgetReport;

    /**
     * Costruttore del budget manager
     */
    public BudgetManager(){}


    public BudgetReport getBudgetReport() {
        return budgetReport;
    }
    public void setBudgetReport(BudgetReport budgetReport) {
        this.budgetReport = budgetReport;
    }


    /**
     * Metodo usato per creare un BudgetReport
     * @param budget Budget associato al BudgetReport
     */
    @Override
    public void createReporter(BudgetInterface budget) {
        BudgetReport report = new BudgetReport(budget);
        setBudgetReport(report);
    }

    /**
     * Metodo usato per creare un report ed aggiornare il denaro disponibile del budget
     * @param importo Importo del movimento
     * @return La spesa effettuata
     */
    @Override
    public double createreport(double importo) {
        return budgetReport.report(importo);
    }


}

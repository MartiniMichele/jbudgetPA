package it.unicam.cs.pa.jbudget.account;

import it.unicam.cs.pa.jbudget.movement.MovementInterface;

/**
 * Classe rappresentante un conto di tipo credito. Estende {@link Account}
 * @param <T> Parametro generico rappresentante un movimento
 */
public class AccountCredito<T extends MovementInterface> extends Account {

    /**
     * Costruttore per un account di tipo credito
     */
    public AccountCredito(String nome, double bilancio, TipoConto tipo) throws AccountException {
        super(nome, bilancio, tipo);
    }


    /**
     * Aggiorna la lista dei movimenti del conto aggiungendono il movimento attuale preso in input
     * @param movimento Movimento da aggiungere alla lista
     */
    @Override
    public void update(MovementInterface movimento) {
        movementlist.add(movimento);
    }



    @Override
    public String toString() {
        return "ContoCredito{" +
                "nome='" + nome + '\'' +
                ", bilancio=" + bilancio +
                ", tipo=" + tipo +
                ", movementlist=" + movementlist +
                '}';
    }
}



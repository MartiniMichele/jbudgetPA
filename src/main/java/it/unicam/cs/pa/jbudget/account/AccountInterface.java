package it.unicam.cs.pa.jbudget.account;

import it.unicam.cs.pa.jbudget.movement.MovementInterface;

import java.util.*;

/**
 * Questa interfaccia e' implementata dalle classi che hanno la responsabilita' di rappresentare un {@link Account}.
 */
public interface AccountInterface<T extends MovementInterface> {


    double getBilancio();
    String getNome();
    TipoConto getTipo();
    int getId();
    List<Integer> getIntegerMovimenti();
    List<T> getMovementlist();
    void setBilancio(double bilancio);
    void setNome(String nome);

    /**
     * Aggiorna alcune informazioni del conto, cambia da credito a debito
     * @param movimento Da cui prelevare le informazioni
     */
    void update(T movimento);


}

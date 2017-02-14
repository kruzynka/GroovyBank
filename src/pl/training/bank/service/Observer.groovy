package pl.training.bank.service

/**
 * Obserwator uzależniony od liczby eventów
 */
interface Observer<T> {

    //metoda do aktualizacji obserwatorów
    void update(T event)

}

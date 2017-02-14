package pl.training.bank.utils

/**
 * Obserwator uzależniony od liczby eventów
 */
interface Observer<T> {

    //metoda do aktualizacji obserwatorów
    void update(T event)

}

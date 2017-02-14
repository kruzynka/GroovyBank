package pl.training.bank.utils

/**
 * Tworzymy trade ze zbiorem obserwatorów, których można... dodawać, usuwać i powiadamiać observatorów
 */
trait Observable<T> {

    private Set<Observer> observers = []

    void addObserver(Observer observer) {
        observers << observer
    }

    void removeObserver(Observer observer) {
        observers.remove(observers)
    }

    void notifyObservers(T event) {
        observers.each { it.update(event) }
    }
}

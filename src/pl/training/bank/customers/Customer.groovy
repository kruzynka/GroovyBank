package pl.training.bank.customers

import groovy.transform.Canonical

/**
 * Gdy mamy np. Long id bez ustawienia uprawnień
 * nie trzymamy się zasady ukrywania informacji,
 * każdy będzie mieć dostęp do pól z klasy, ale w ramach zdefiniowanych zmiennych
 * Gdybyśmy nadali ustawienie final, nie moglibyśmy ustawić wartości dla zmiennej
 */
@Canonical
class Customer {//definiujemy nową klasę

    private Long id
    private String firstName
    String lastName //można ustawiać i pobierać wartość (w przeciwieństwie do dwóch pozostałych)

    Long getId() {
        id
    }

    String getFirstName() {
        firstName
    }

}


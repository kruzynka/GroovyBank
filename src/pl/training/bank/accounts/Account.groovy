package pl.training.bank.accounts

import pl.training.bank.customers.Customer

import static java.util.Collections.unmodifiableList

class Account {

    private Long id
    private String number = ""
    private BigDecimal balance = 0
    private Set<Customer> customers = [] //zmienilismy List na Set - zbiór nie pozwala na duplikaty

    /**
     * mozna zadeklarowac jako def, ale dla podkreslenia ze nic nie zwraca uzyjemy void
     * found jest typu BigDecimal
     * @param founds
     */
    void deposit(founds) {
        balance += founds

    }

    void withdraw(founds) {
        balance -= founds
    }

    /**
     * Tworzymy get-er dlatego, że chcemy by miano do niego tylko dostęp do odczytu
     */
    String getNumber() {
        number
    }

    /**
     * Oczekujemy zwrócenia balansu
     * @return
     */
    BigDecimal getBalance() {
        balance
    }

    /**
     * moglibyśmy użyć add, ale użyjemy bitowego przesunięcia, dodajemy na koniec
     * @param customer
     */
    void addCustomer(Customer customer) {
        customers << customer
    }

    /**
     * Najlepiej byłoby zwrócić listę, która jest niemodyfikowalna (to jest statyczna metody z typu Collections)
     * to samo co Collections.unmodifiableList([*customers])
     * @return
     */
    List<Customer> getCustomers() {
        unmodifiableList(customers as List<Customer>)

    }

    /**
     * Zwracamy id konta
     * @return
     */
    Long getId(){
        id
    }

}

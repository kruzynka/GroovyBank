package pl.training.bank.accounts

import groovy.sql.Sql

import javax.sql.DataSource


class MySqlAccountsRepository implements AccountsRepository {

    private static final String INSERT_SQL = "insert into accounts values(null,?,?)"
    private static final String SELECT_BY_NUMBER_SQL = "select * from accounts a where a.number = ?"
    private static final String UPDATE_SQL = "update accounts set balance = ? where id = ?"

    private Sql sql

    MySqlAccountsRepository(DataSource dataSource) {
        sql = new Sql(dataSource)

    }


    @Override
    Long save(Account account) {
        def keys = sql.executeInsert INSERT_SQL, [account.number, account.balance]
        account.id = keys[0][0]
        return account.id
    }


    @Override
    Account getBy(String accountNumber) {
        Account account
        sql.eachRow(SELECT_BY_NUMBER_SQL, [accountNumber]) { row ->
            account = new Account(id: row.id, number: row.number, balance: row.balance)
        }
        return account
    }


    @Override
    void update(Account account) {
        sql.execute UPDATE_SQL, [account.balance, account.id]
    }


    @Override
    List<Account> getAll() {
        return null
    }

}

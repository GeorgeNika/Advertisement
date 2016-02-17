/**
 * Интерфейс служит для работы с данными таблицы,
 * связанными с классом Account.
 * Использует framework Spring Data.
 * Created by george on 12.02.2016.
 */

package ua.george_nika.advertisement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.george_nika.advertisement.model.Account;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select count(a) from Account a where a.login = :login")
    int getCountAccountsByLogin(@Param("login") String login);

    @Query("select u from Account u where u.login = :login")
    Account getAccountByLogin(@Param("login") String login);
}

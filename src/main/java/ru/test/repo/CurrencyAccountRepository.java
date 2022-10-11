package ru.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.model.CurrencyAccount;
import ru.test.model.User;

import java.util.List;

@Repository
public interface CurrencyAccountRepository extends JpaRepository<CurrencyAccount, Long> {

    List<CurrencyAccount> findCurrencyAccountsByUser(User user);
}

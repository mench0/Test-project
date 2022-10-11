package ru.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.model.CurrencyType;
import ru.test.model.RateExchange;

@Repository
public interface RateExchangeRepository extends JpaRepository<RateExchange, Long> {

    RateExchange findRateExchangeByFirstCurrency(CurrencyType currencyType);


}

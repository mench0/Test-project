package ru.test.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.test.model.RateExchange;
import ru.test.repo.RateExchangeRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BGCurrency {

    private final RateExchangeRepository rateExchangeRepository;

    @Scheduled(cron = "#{'${currency.scheduler.cron}'}")
    public void fixCurrencyValue() {
        List<RateExchange> exchangeCurrency = rateExchangeRepository.findAll();
        if(getRandomBoolean()) {
            List<RateExchange> exchanges = exchangeCurrency.stream().map(c -> {
                c.setSecondValue(c.getSecondValue().multiply(BigDecimal.valueOf(1.005)));
                return c;
            }).collect(Collectors.toList());
            rateExchangeRepository.saveAll(exchanges);
        }
        List<RateExchange> exchanges = exchangeCurrency.stream().map(c -> {
            c.setSecondValue(c.getSecondValue().divide(BigDecimal.valueOf(1.005), MathContext.DECIMAL32));
            return c;
        }).collect(Collectors.toList());
        rateExchangeRepository.saveAll(exchanges);
    }

    private boolean getRandomBoolean() {
        return Math.random() > 0.5;
    }

}

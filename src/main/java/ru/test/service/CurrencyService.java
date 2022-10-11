package ru.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.model.RateExchange;
import ru.test.repo.RateExchangeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final RateExchangeRepository rateExchangeRepository;

    @Transactional
    public List<RateExchange> getAllCurrency(){
        return rateExchangeRepository.findAll();
    }


}

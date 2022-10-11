package ru.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.repo.RateExchangeRepository;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private RateExchangeRepository rateExchangeRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getAllCurrency() {
        when(rateExchangeRepository.findAll())
                .thenReturn(new ArrayList<>());
        currencyService.getAllCurrency();
        verify(rateExchangeRepository, times(1)).findAll();
    }
}
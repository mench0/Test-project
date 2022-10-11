package ru.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.repo.RateExchangeRepository;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BGCurrencyTest {

    @Mock
    private RateExchangeRepository rateExchangeRepository;
    @InjectMocks
    private BGCurrency bgCurrency;

    @Test
    void fixCurrencyValue() {
        when(rateExchangeRepository.findAll()).thenReturn(new ArrayList<>());
        when(rateExchangeRepository.saveAll(any())).thenReturn(new ArrayList<>());
        bgCurrency.fixCurrencyValue();
        verify(rateExchangeRepository, times(1)).findAll();
        verify(rateExchangeRepository, times(1)).saveAll(any());

    }
}
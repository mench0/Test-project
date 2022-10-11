package ru.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test.dto.RequestDto;
import ru.test.model.CurrencyType;
import ru.test.model.RateExchange;
import ru.test.repo.RateExchangeRepository;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.test.model.CurrencyType.AMD;
import static ru.test.model.CurrencyType.EUR;
import static ru.test.model.CurrencyType.USD;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private RateExchangeRepository exchangeRepository;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    void when_AMD_exchange_to_AMD() {
        RequestDto request = createRequestDto();
        BigDecimal result = exchangeService.exchange(request);
        assertEquals(request.getValue(), result);
    }

    @Test
    void when_USD_exchange_to_USD() {
        RequestDto request = createRequestDto();
        request.setTo(USD);
        request.setFrom(USD);
        BigDecimal result = exchangeService.exchange(request);
        assertEquals(request.getValue(), result);
    }

    @Test
    void when_EUR_exchange_to_USD() {
        RequestDto request = createRequestDto();
        request.setFrom(EUR);
        request.setTo(USD);
        when(exchangeRepository.findRateExchangeByFirstCurrency(any())).thenReturn(createRateExchange());
        exchangeService.exchange(request);
        verify(exchangeRepository, times(2)).findRateExchangeByFirstCurrency(any());
    }

    @Test
    void when_USD_exchange_to_AMD() {
        RequestDto request = createRequestDto();
        request.setFrom(USD);
        when(exchangeRepository.findRateExchangeByFirstCurrency(any())).thenReturn(createRateExchange());
        exchangeService.exchange(request);
        verify(exchangeRepository, times(1)).findRateExchangeByFirstCurrency(any());

    }

    @Test
    void when_AMD_exchange_to_USD() {
        RequestDto request = createRequestDto();
        BigDecimal result = exchangeService.exchange(request);
        assertEquals(request.getValue(), result);
    }

    private RequestDto createRequestDto(){
        return RequestDto.builder().email("123").from(AMD).to(AMD).value(new BigDecimal("100.1", MathContext.DECIMAL32)).build();
    }

    private RateExchange createRateExchange(){
        return RateExchange.builder().id(1L).firstCurrency(EUR).firstValue(BigDecimal.valueOf(1)).secondCurrency(USD).secondValue(BigDecimal.valueOf(2)).build();
    }
}
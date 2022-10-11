package ru.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test.dto.RequestDto;
import ru.test.model.RateExchange;
import ru.test.repo.RateExchangeRepository;

import java.math.BigDecimal;
import java.math.MathContext;

import static ru.test.model.CurrencyType.AMD;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final RateExchangeRepository exchangeRepository;

    public BigDecimal exchange(RequestDto requestDto) {
        if (requestDto.getFrom().equals(requestDto.getTo())){
            return requestDto.getValue();
        } else if (!AMD.equals(requestDto.getFrom()) && !AMD.equals(requestDto.getTo())) {
            return fromAnyCurrencyToAnyCurrency(requestDto);
        } else if (AMD.equals(requestDto.getFrom()) && AMD.equals(requestDto.getTo())) {
            return requestDto.getValue();
        } else if (AMD.equals(requestDto.getFrom())) {
            return fromAmdCurrency(requestDto);
        }
        return toAmdCurrency(requestDto);
    }

    private BigDecimal fromAmdCurrency(RequestDto dto) {
        RateExchange rateExchange = exchangeRepository.findRateExchangeByFirstCurrency(dto.getTo());
        return dto.getValue().divide(rateExchange.getSecondValue(), MathContext.DECIMAL32);
    }

    private BigDecimal toAmdCurrency(RequestDto dto) {
        RateExchange rateExchange = exchangeRepository.findRateExchangeByFirstCurrency(dto.getFrom());
        return rateExchange.getSecondValue().multiply(dto.getValue(), MathContext.DECIMAL32);
    }

    private BigDecimal fromAnyCurrencyToAnyCurrency(RequestDto dto) {
        BigDecimal toAmd = toAmdCurrency(dto);
        dto.setValue(toAmd);
        return fromAmdCurrency(dto);
    }
}

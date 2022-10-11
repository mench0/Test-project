package ru.test.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.test.model.CurrencyType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class RequestDto {

    private String email;
    private CurrencyType from;
    private CurrencyType to;
    private BigDecimal value;
}

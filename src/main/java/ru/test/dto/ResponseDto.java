package ru.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.test.model.CurrencyType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseDto {
    private BigDecimal newValue;
    private CurrencyType fromType;
    private CurrencyType toType;
}

package ru.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.test.dto.RequestDto;
import ru.test.dto.ResponseDto;
import ru.test.model.CurrencyAccount;
import ru.test.model.RateExchange;
import ru.test.model.User;
import ru.test.repo.CurrencyAccountRepository;
import ru.test.repo.UserRepository;
import ru.test.service.CurrencyService;
import ru.test.service.ExchangeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController(value = "/currency")
@Api(tags = "Clients")
@RequiredArgsConstructor
public class CurrencyRestController {
    private final CurrencyService currencyService;
    private final ExchangeService exchangeService;
    private final CurrencyAccountRepository currencyAccountRepository;
    private final UserRepository userRepository;

    @ApiOperation(value = "This method is used to get the currency.", authorizations = {@Authorization(value = "jwtToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the currency", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            })
    })
    @GetMapping("/getAll")
    public List<RateExchange> getCurrency() {
        return currencyService.getAllCurrency();
    }

    @ApiOperation(value = "This method is currency converter.", authorizations = {@Authorization(value = "jwtToken")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exchange currency", content = {
                    @Content(mediaType = "application/json", array =
                    @ArraySchema(schema = @Schema(implementation = User.class)))
            })
    })
    @PostMapping("/exchange")
    public ResponseDto exchangeMoney(@RequestBody RequestDto request) {
        User authenticationPrincipal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BigDecimal exchangeValue = exchangeService.exchange(request);
        ResponseDto responseDto = ResponseDto.builder().toType(request.getTo())
                .fromType(request.getFrom()).newValue(exchangeValue).build();
        User user = userRepository.findById(authenticationPrincipal.getId()).get();
        List<CurrencyAccount> currencyAccounts = currencyAccountRepository.findCurrencyAccountsByUser(authenticationPrincipal);
        if (currencyAccounts.isEmpty()) {
            CurrencyAccount newCurrencyAccount = CurrencyAccount.builder()
                    .user(user).type(responseDto.getToType()).createDate(LocalDate.now())
                    .lastUpdateDate(LocalDate.now()).build();
            currencyAccountRepository.save(newCurrencyAccount);
            return responseDto;
        }
        List<CurrencyAccount> updateAccount = currencyAccounts.stream().filter(c -> c.getType().equals(request.getTo()))
                        .map(currencyAccount -> {
                            currencyAccount.setValue(exchangeValue);
                            currencyAccount.setLastUpdateDate(LocalDate.now());
                            return currencyAccount;
                        }).collect(Collectors.toList());
        user.setAccounts(updateAccount);
        return responseDto;
    }


}
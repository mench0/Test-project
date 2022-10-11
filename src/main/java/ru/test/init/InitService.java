package ru.test.init;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.test.model.CurrencyAccount;
import ru.test.model.CurrencyType;
import ru.test.model.RateExchange;
import ru.test.model.Role;
import ru.test.model.User;
import ru.test.repo.CurrencyAccountRepository;
import ru.test.repo.RateExchangeRepository;
import ru.test.repo.RoleRepository;
import ru.test.repo.UserRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CurrencyAccountRepository currencyAccountRepository;
    private final RateExchangeRepository rateExchangeRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        initRoles();
        initAdmin();
        initUser();
        initExchange();
    }

    private void initRoles() {
        roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));
    }

    private void initAdmin() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getByName("ADMIN"));

        User admin = User.builder().email("123").firstName("Армен").lastName("Мнацаканян")
                .middleName("Валерьевич").password(passwordEncoder.encode("123"))
                .roles(roles).enabled(true).registrationDate(LocalDate.now()).build();

        List<CurrencyAccount> accounts = new ArrayList<>();
        CurrencyAccount usdAccount = CurrencyAccount.builder().type(CurrencyType.USD).value(new BigDecimal(100))
                .createDate(LocalDate.now()).lastUpdateDate(LocalDate.now()).user(admin).build();
        CurrencyAccount rubAccount = CurrencyAccount.builder().type(CurrencyType.RUB).value(new BigDecimal(200))
                .createDate(LocalDate.now()).lastUpdateDate(LocalDate.now()).user(admin).build();
        accounts.add(usdAccount);
        accounts.add(rubAccount);

        currencyAccountRepository.saveAll(accounts);
    }

    private void initUser() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.getByName("USER"));
        User user = User.builder().email("456").firstName("Джон").lastName("Джонсон")
                .middleName("Майколович").password(passwordEncoder.encode("456"))
                .roles(roles).enabled(true).registrationDate(LocalDate.now()).build();
        List<CurrencyAccount> userAccounts = new ArrayList<>();
        userAccounts.add(CurrencyAccount.builder().type(CurrencyType.USD).value(new BigDecimal(123))
                .createDate(LocalDate.now()).lastUpdateDate(LocalDate.now()).user(user).build());
        userAccounts.add(CurrencyAccount.builder().type(CurrencyType.RUB).value(new BigDecimal(333))
                .createDate(LocalDate.now()).lastUpdateDate(LocalDate.now()).user(user).build());

        currencyAccountRepository.saveAll(userAccounts);

    }

    private void initExchange() {
        RateExchange eurByAmd = RateExchange.builder().firstCurrency(CurrencyType.EUR)
                .secondCurrency(CurrencyType.AMD).firstValue(new BigDecimal(1))
                .secondValue(new BigDecimal(415)).date(LocalDate.now()).build();
        RateExchange rubByAmd = RateExchange.builder().firstCurrency(CurrencyType.RUB)
                .secondCurrency(CurrencyType.AMD).firstValue(new BigDecimal(1))
                .secondValue(new BigDecimal("6.5", MathContext.DECIMAL32)).date(LocalDate.now()).build();
        RateExchange usdByAmd = RateExchange.builder().firstCurrency(CurrencyType.USD)
                .secondCurrency(CurrencyType.AMD).firstValue(new BigDecimal(1))
                .secondValue(new BigDecimal(400)).date(LocalDate.now()).build();
        rateExchangeRepository.save(eurByAmd);
        rateExchangeRepository.save(rubByAmd);
        rateExchangeRepository.save(usdByAmd);
    }

}

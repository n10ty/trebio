package com.trebio.context.account.service;

import com.trebio.context.account.exception.AccountAlreadyExistException;
import com.trebio.context.account.model.Account;
import com.trebio.context.account.persistence.jpa.AccountRepository;
import com.trebio.context.account.security.PasswordEncoder;
import com.trebio.context.account.service.dto.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public Account registerNewUserAccount(RegisterUser registerUser) throws AccountAlreadyExistException {
        if (emailRegistered(registerUser.email)) {
            throw new AccountAlreadyExistException();
        }
        Account account = new Account(
            registerUser.email,
            passwordEncoder.encode(registerUser.password)
        );

        return accountRepository.saveAndFlush(account);
    }

    private boolean emailRegistered(String email) {
        return accountRepository.findOneByEmail(email) != null;
    }
}

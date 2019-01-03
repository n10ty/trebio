package com.trebio.context.account.service;

import com.trebio.context.account.model.Account;
import com.trebio.context.account.persistence.jpa.AccountRepository;
import com.trebio.context.account.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public MyUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Account account = accountRepository.findOneByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(account);
    }
}


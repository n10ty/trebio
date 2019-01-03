package com.trebio.context.account.persistence.jpa;


import com.trebio.context.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Component
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findOneByEmail(String email);

    default Account find(Long id) throws AccountNotFoundException {
        Optional<Account> account = findById(id);

        if (!account.isPresent()) {
            throw new AccountNotFoundException("Account not found.");
        }

        return account.get();
    }
}

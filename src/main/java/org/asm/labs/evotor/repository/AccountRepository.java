package org.asm.labs.evotor.repository;

import org.asm.labs.evotor.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByLogin(String login);
}

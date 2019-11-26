package org.asm.labs.evotor.repository;

import org.asm.labs.evotor.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Account findByLogin(String login);
}

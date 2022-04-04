package org.essaadani.accountmscqrses.query.repository;

import org.essaadani.accountmscqrses.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}

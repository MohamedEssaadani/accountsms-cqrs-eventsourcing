package org.essaadani.accountmscqrses.query.repository;

import org.essaadani.accountmscqrses.query.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}

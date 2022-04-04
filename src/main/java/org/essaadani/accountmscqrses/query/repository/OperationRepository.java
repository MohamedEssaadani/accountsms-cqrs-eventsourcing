package org.essaadani.accountmscqrses.query.repository;

import org.essaadani.accountmscqrses.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}

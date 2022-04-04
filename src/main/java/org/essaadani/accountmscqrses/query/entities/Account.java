package org.essaadani.accountmscqrses.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.essaadani.accountmscqrses.commonapi.enums.AccountStatus;

import javax.persistence.*;

import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Account {
    @Id
    private String accountId;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    // customer
    private String customerId;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Collection<Operation> operations;


}

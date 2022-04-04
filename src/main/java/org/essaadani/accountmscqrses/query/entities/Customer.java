package org.essaadani.accountmscqrses.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Date;

@Data
@Entity @AllArgsConstructor @NoArgsConstructor @ToString
public class Customer {
    @Id
    private String customerId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String cin;
}

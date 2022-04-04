package org.essaadani.accountmscqrses.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.essaadani.accountmscqrses.commonapi.enums.OperationType;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}

package org.essaadani.accountmscqrses.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequestDTO {
    private double initialBalance;
    private String currency;
    private String customerId;
}

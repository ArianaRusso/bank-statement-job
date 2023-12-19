package br.com.arianarussp.bankstatementjob.dominio;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BankStatement {
    private Customer customer;
    private List<Transaction> transactions;
    private LocalDateTime timestamp;
}

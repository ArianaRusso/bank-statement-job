package br.com.arianarussp.bankstatementjob.dominio;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Transaction {
    private UUID id;
    private UUID idCustomer;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

}

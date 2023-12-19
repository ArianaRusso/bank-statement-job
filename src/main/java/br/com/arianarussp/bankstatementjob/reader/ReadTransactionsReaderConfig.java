package br.com.arianarussp.bankstatementjob.reader;

import br.com.arianarussp.bankstatementjob.dominio.Transaction;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Configuration
public class ReadTransactionsReaderConfig {

    @Bean
    public ItemStreamReader<Transaction> readerFile(){
        return new FlatFileItemReaderBuilder<Transaction>()
                .name("transactionItemReader")
                .resource(new ClassPathResource("transactions.txt"))
                .delimited()
                .names("id", "idCustomer","type", "amount", "timestamp")
                .fieldSetMapper(transactionFieldSetMapper())
                .build();

    }

    private FieldSetMapper<Transaction> transactionFieldSetMapper() {
        return fieldSet -> {
            return Transaction.builder()
                    .id(UUID.fromString(fieldSet.readString("id")))
                    .idCustomer(UUID.fromString(fieldSet.readString("idCustomer")))
                    .type(fieldSet.readString("type"))
                    .amount(new BigDecimal(fieldSet.readString("amount")))
                    .timestamp(LocalDateTime.parse(fieldSet.readString("timestamp"), DateTimeFormatter.ISO_DATE_TIME))
                    .build();
        };
    }
}

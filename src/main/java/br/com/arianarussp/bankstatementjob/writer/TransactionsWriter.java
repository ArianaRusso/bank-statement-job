package br.com.arianarussp.bankstatementjob.writer;

import br.com.arianarussp.bankstatementjob.dominio.BankStatement;
import br.com.arianarussp.bankstatementjob.dominio.Transaction;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionsWriter {

    @Bean
    public ItemWriter<BankStatement> writerConsole(){
        return items-> items.forEach(System.out::println);
    }
}

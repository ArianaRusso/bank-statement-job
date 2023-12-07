package br.com.arianarussp.bankstatementjob.step;

import br.com.arianarussp.bankstatementjob.dominio.BankStatement;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BankStatementeStepConfig {

    @Qualifier("bankStatementApp")
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public Step bankStatementStep(ItemReader<BankStatement> bankStatementItemReader,
                                  ItemProcessor<BankStatement, BankStatement> bankStatementItemProcessor,
                                  ItemWriter<BankStatement> bankStatementItemWriter,
                                  JobRepository jobRepository){
        return new StepBuilder("bankStatementStep", jobRepository)
                .<BankStatement, BankStatement>chunk(1, this.transactionManager)
                .reader(bankStatementItemReader)
                .processor(bankStatementItemProcessor)
                .writer(bankStatementItemWriter)
                .build();
    }
}

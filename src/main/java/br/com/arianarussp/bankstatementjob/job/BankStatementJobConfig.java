package br.com.arianarussp.bankstatementjob.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankStatementJobConfig {
    @Bean
    public Job bankStatementJob(Step bankStatementStep, JobRepository jobRepository) {
        return new JobBuilder("bankStatementJob", jobRepository)
                .start(bankStatementStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}

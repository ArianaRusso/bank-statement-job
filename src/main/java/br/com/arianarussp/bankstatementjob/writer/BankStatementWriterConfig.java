package br.com.arianarussp.bankstatementjob.writer;

import br.com.arianarussp.bankstatementjob.dominio.BankStatement;
import br.com.arianarussp.bankstatementjob.dominio.Transaction;
import org.springframework.batch.item.file.*;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

@Configuration
public class BankStatementWriterConfig {

    @Bean
    public MultiResourceItemWriter<BankStatement> filesBankStatement(){
        return new MultiResourceItemWriterBuilder<BankStatement>()
                .name("filesBankStatement")
                .resource(new FileSystemResource("files/bank-statement"))
                .itemCountLimitPerResource(1)
                .resourceSuffixCreator(suffixCreator())
                .delegate(fileBankStatement())
                .build();

    }

    private FlatFileItemWriter<BankStatement> fileBankStatement() {
        return new FlatFileItemWriterBuilder<BankStatement>()
                .name("fileBankStatement")
                .resource(new FileSystemResource("files/bank-statement.txt"))
                .lineAggregator(lineAggregator())
                .headerCallback(headerCallback())
                .build();
    }

    private FlatFileHeaderCallback headerCallback() {
        return new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.append(String.format("%84s\n", "RUSSO BANK"));
                writer.append(String.format("%84s\n", "Av Washington Luis, 4000, Valinhos-SP"));
            }
        };
    }





    private LineAggregator<BankStatement> lineAggregator() {
        return new LineAggregator<BankStatement>() {

            @Override
            public String aggregate(BankStatement bankStatement) {

                StringBuilder writer = new StringBuilder();
                writer.append(String.format("Nome: %s\n", bankStatement.getCustomer().getFirstName()+
                        " "+ bankStatement.getCustomer().getLastName()));
                writer.append(String.format("Documento: %s\n", bankStatement.getCustomer().getDocument()));
                writer.append(String.format("Endereço: %s\n\n", bankStatement.getCustomer().getAddress()));

                LocalDateTime timestamp= bankStatement.getTimestamp();
                String monthName = timestamp.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

                writer.append("------------------------------------------------------------------------------------\n");
                writer.append("LANÇAMENTOS DO MÊS DE "+monthName.toUpperCase(Locale.ROOT)+"\n");
                writer.append("------------------------------------------------------------------------------------\n");
                for(Transaction transaction : bankStatement.getTransactions()){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    String formattedTimestamp = transaction.getTimestamp().format(formatter);
                    writer.append(String.format("%s - %s - %.2f\n",
                            //transaction.getTimestamp(),
                            formattedTimestamp,
                            transaction.getType(),
                            transaction.getAmount()));
                }
                writer.append("\n");
                return writer.toString();
            }
        };
    }

    private ResourceSuffixCreator suffixCreator() {
        return new ResourceSuffixCreator() {
            @Override
            public String getSuffix(int index) {
                return index + ".txt";
            }
        };
    }

//    @Bean
//    public ItemWriter<BankStatement> writerConsole(){
//        return items-> items.forEach(System.out::println);
//    }
}

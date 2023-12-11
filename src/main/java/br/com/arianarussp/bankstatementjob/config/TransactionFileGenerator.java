package br.com.arianarussp.bankstatementjob.config;

import br.com.arianarussp.bankstatementjob.dominio.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class TransactionFileGenerator {

    private static final List<UUID> uniqueIds = new ArrayList<>();

    public static void main(String[] args) {
        generateTestFile();
    }

    private static void generateTestFile() {
        try (FileWriter writer = new FileWriter("transactions.txt")) {
            for (int i = 1; i <= 10; i++) {
                Transaction transaction = generateRandomTransaction(i);
                writer.write(transactionToString(transaction));
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Transaction generateRandomTransaction(int index) {
        return Transaction.builder()
                .id(UUID.randomUUID())
                .idCustomer(generateRandomId())
                .type(generateRandomType())
                .amount(BigDecimal.valueOf(Math.random() * 1000))
                .timestamp(LocalDateTime.now())
                .build();
    }

    private static String transactionToString(Transaction transaction) {
        return String.format("%s,%s,%s,%s,%s",
                transaction.getId(),
                transaction.getIdCustomer(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp());

    }
    private static String generateRandomType(){
        Random random = new Random();
        int randomTypeIndex = random.nextInt(3); // 0, 1, or 2

        switch (randomTypeIndex) {
            case 0:
                return "payIn";
            case 1:
                return "withDraw";
            case 2:
                return "transfer";
            default:
                throw new IllegalStateException("Unexpected value: " + randomTypeIndex);
        }

    }
    private static void populateUniqueIds() {
        while (uniqueIds.size() <= 2) {
            uniqueIds.add(UUID.randomUUID());
        }
    }
    private static UUID generateRandomId() {
        if (uniqueIds.isEmpty()) {
            populateUniqueIds();
        }
        int randomIndex = (int) (Math.random() * uniqueIds.size());
        return uniqueIds.get(randomIndex);
    }
}

package br.com.arianarussp.bankstatementjob.reader;

import br.com.arianarussp.bankstatementjob.dominio.BankStatement;
import br.com.arianarussp.bankstatementjob.dominio.Transaction;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BankStatementReader implements ItemStreamReader<BankStatement> {

    private ItemStreamReader<Transaction> delegate;
    private Transaction currentTransaction;

    public BankStatementReader(ItemStreamReader<Transaction> delegate) {
        this.delegate = delegate;
    }

    @Override
    public BankStatement read() throws Exception {
        if(currentTransaction == null){
            currentTransaction = delegate.read();
        }
        BankStatement bankStatement = null;
        Transaction transaction = currentTransaction;
        currentTransaction= null;

        if(transaction != null){
            bankStatement = new BankStatement();
            bankStatement.setIdCustomer(transaction.getIdCustomer());

            bankStatement.setTransactions(new ArrayList<>());
            bankStatement.getTransactions().add(transaction);
            bankStatement.setTimestamp(LocalDateTime.now());

            while (isTransactionRelated(transaction)){
                bankStatement.getTransactions().add(transaction);
            }

        }
        return bankStatement;
    }

    private boolean isTransactionRelated(Transaction transaction) throws Exception {
        return peek() != null && transaction.getIdCustomer() == currentTransaction.getIdCustomer();
    }

    private Transaction peek() throws Exception {
        currentTransaction = delegate.read();
        return currentTransaction;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
       delegate.close();
    }
}

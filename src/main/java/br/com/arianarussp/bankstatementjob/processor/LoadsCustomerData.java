package br.com.arianarussp.bankstatementjob.processor;

import br.com.arianarussp.bankstatementjob.dominio.Customer;
import br.com.arianarussp.bankstatementjob.dominio.BankStatement;
import br.com.arianarussp.bankstatementjob.dominio.Transaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class LoadsCustomerData implements ItemProcessor<BankStatement, BankStatement> {

    @Override
    public BankStatement process(BankStatement bankStatement) throws Exception {
        List<Transaction> transactions = bankStatement.getTransactions();

        if (transactions != null && transactions.size() > 0) {
            UUID id = transactions.get(0).getIdCustomer();
            Customer customer = getCustomer(id);
            bankStatement.setCustomer(customer);
        }
        return bankStatement;
    }

    private Customer getCustomer(UUID id){
        String uri= "http://localhost:8083/customer/" +id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Customer> response = restTemplate.getForEntity(uri, Customer.class);
        if (response.getStatusCode() != HttpStatus.OK){
            throw new ValidationException("Customer not found");
        }
       return response.getBody();
    }
}

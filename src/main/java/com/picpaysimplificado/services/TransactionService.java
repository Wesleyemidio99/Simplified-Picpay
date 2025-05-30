package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDto;
import com.picpaysimplificado.repositories.TransactioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
@AllArgsConstructor
public class TransactionService {

    private final UserService userService;

    private final TransactioRepository transactioRepository;

    private final RestTemplate restTemplate;

    public void createTransaction(TransactionDto transactionDTO) throws Exception {
        User sender = userService.findById(transactionDTO.getSenderId());
        User receiver = userService.findById(transactionDTO.getReceiverId());

        userService.validateTransactions(sender, transactionDTO.getValue());

        boolean isAuthorized = authorizeTransaction(sender, transactionDTO.getValue());
        if (!isAuthorized){
            throw new Exception("Transação não autorizada.");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getValue());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(java.time.LocalDate.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.getValue()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.getValue()));

        transactioRepository.save(transaction);
        userService.save(sender);
        userService.save(receiver);


    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK) {
            String authorizationCode = (String) authorizeResponse.getBody().get("authorization");
            return "true".equalsIgnoreCase(authorizationCode);
        } else return false;
    }
}

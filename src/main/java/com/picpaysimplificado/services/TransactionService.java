package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDto;
import com.picpaysimplificado.exception.ModifiedExceptions;
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

    private final NotificationService notificationService;

    public Transaction createTransaction(TransactionDto transactionDTO) throws java.lang.Exception {
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

        notificationService.sendNotification(receiver, "Você recebeu uma transação no valor de " + transactionDTO.getValue() + " de " + sender.getFirstName());
        notificationService.sendNotification(sender, "Você enviou uma transação no valor de " + transactionDTO.getValue() + " para " + receiver.getFirstName());

        return transaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        ResponseEntity<Map> authorizeResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (authorizeResponse.getStatusCode() == HttpStatus.OK) {
            String authorizationCode = authorizeResponse.getBody().get("status").toString();
            return "success".equalsIgnoreCase(authorizationCode);
        } else return false;
    }
}

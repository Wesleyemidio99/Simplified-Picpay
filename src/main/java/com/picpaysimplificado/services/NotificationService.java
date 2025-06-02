package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.NotificationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    private final RestTemplate restTemplate;


    public void sendNotification(User user, String messsage) throws Exception {
        String email = user.getEmail();

        NotificationDto notificationRequest = new NotificationDto(email, messsage);

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
//            log.info("Erro ao enviar notificação para o usuário: {}", user.getId());
//            throw new Exception("Service de notificação indisponível.");
//        }
        System.out.println("Enviando notificação para o usuário: " + user.getId() + " com a mensagem: " + messsage);
    }
}

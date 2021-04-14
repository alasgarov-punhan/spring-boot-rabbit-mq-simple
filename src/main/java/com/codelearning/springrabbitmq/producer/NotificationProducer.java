package com.codelearning.springrabbitmq.producer;

import com.codelearning.springrabbitmq.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Service
public class NotificationProducer {

    @Value("${sr.rabbit.routing.name}")
    private String routingName;

    @Value("${sr.rabbit.exchange.name}")
    private String exchangeName;


    // PostConstruct yeni bu obyekt dogru bir sekilde initialize oldugdan sonra gondersin
    @PostConstruct
    public void init() {
        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setCreatedAt(new Date());
        notification.setMessage("Haydi Kodlayalim platformuna hosgeldiniz");
        notification.setSeen(Boolean.FALSE);
        sendToQueue(notification);
    }

    //RabbitTemplate bu bizim rabbit mq nin connectin obyketi kimi bunun uzerinden getirik servere
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendToQueue(Notification notification) {
        System.out.println("Notification Sent ID : " + notification.getNotificationId());
        rabbitTemplate.convertAndSend(exchangeName, routingName, notification);
    }
}

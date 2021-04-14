package com.codelearning.springrabbitmq.listener;

import com.codelearning.springrabbitmq.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "rabbit-mq-test")
    public void handleMessage(Notification notification) {
        System.out.println("Message received");
        System.out.println(notification);
    }
}

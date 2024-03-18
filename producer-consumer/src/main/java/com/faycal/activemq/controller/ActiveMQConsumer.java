package com.faycal.activemq.controller;


import com.faycal.activemq.model.CustomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableJms
public class ActiveMQConsumer {


    @JmsListener(destination = "QUEUE_TOPIC")
    public void receiveMessage(Message message) {
        try {
            if (message instanceof ActiveMQObjectMessage objectMessage) {
                Object payload = objectMessage.getObject();
                CustomMessage customMessage = ((CustomMessage) payload);
                log.info("Received object message from ActiveMQ : {}", customMessage);
            } else {
                log.warn("Received message of unsupported type: {}", message.getClass().getName());
            }
        } catch (Exception e) {
            log.error("Unknown Error occurred in processing CustomMessage", e);
            throw new RuntimeException("Problem in receiving message from Active MQ");
        }
    }

}

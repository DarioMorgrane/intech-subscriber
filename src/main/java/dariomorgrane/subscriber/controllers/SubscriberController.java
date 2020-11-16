package dariomorgrane.subscriber.controllers;

import dariomorgrane.subscriber.models.Message;
import dariomorgrane.subscriber.services.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Controller
@RequestMapping("/subscriber")
public class SubscriberController {

    private static final Logger LOG = LogManager.getLogger(SubscriberController.class);
    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<String> saveMessage(@RequestBody Message message) {
        try {
            LOG.info("Received message mapped to object: " + message);
            messageService.saveMessage(message);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<String> getLastId() {
        Long lastIdFromPurchase = messageService.getLastIdFromPurchase();
        if (lastIdFromPurchase == null) {
            lastIdFromPurchase = 0L;
        }
        Long lastIdFromSubscription = messageService.getLastIdFromSubscription();
        if (lastIdFromSubscription == null) {
            lastIdFromSubscription = 0L;
        }
        long lastId = Math.max(lastIdFromPurchase, lastIdFromSubscription);
        LOG.info("Last used ID was requested. Return: " + lastId);
        return ResponseEntity.ok().body(String.valueOf(lastId));
    }

}

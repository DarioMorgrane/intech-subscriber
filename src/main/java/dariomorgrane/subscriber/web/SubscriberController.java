package dariomorgrane.subscriber.web;

import dariomorgrane.subscriber.exception.WebLayerException;
import dariomorgrane.subscriber.model.Message;
import dariomorgrane.subscriber.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/subscriber")
public class SubscriberController {

    private static final Logger LOG = LogManager.getLogger(SubscriberController.class);
    private final MessageService service;

    @Autowired
    public SubscriberController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> saveMessage(@RequestBody Message message) {
        long currentMessageId = message.getId();
        if (service.checkReceivedIdIsValid(currentMessageId)) {
            LOG.info("Received message mapped to object: " + message);
            service.saveMessage(message);
            return ResponseEntity.ok().build();
        } else {
            LOG.warn("Received id " + currentMessageId + " already exists in DB");
            throw new WebLayerException("Duplicate key value, id " + currentMessageId + " already exists.");
        }
    }

    @GetMapping
    public ResponseEntity<String> getLastId() {
        Long lastIdFromPurchase = service.getLastIdFromPurchase();
        if (lastIdFromPurchase == null) {
            lastIdFromPurchase = 0L;
        }
        Long lastIdFromSubscription = service.getLastIdFromSubscription();
        if (lastIdFromSubscription == null) {
            lastIdFromSubscription = 0L;
        }
        long lastId = Math.max(lastIdFromPurchase, lastIdFromSubscription);
        LOG.info("Last used ID was requested. Return: " + lastId);
        return ResponseEntity.ok().body(String.valueOf(lastId));
    }

}

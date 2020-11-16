package dariomorgrane.subscriber.services;

import dariomorgrane.subscriber.models.Message;
import dariomorgrane.subscriber.persistence.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public Long getLastIdFromPurchase() {
        return messageRepository.getLastIdFromPurchase();
    }

    public Long getLastIdFromSubscription() {
        return messageRepository.getLastIdFromSubscription();
    }

}

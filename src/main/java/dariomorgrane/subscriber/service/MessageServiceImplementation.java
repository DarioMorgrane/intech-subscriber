package dariomorgrane.subscriber.service;

import dariomorgrane.subscriber.model.Message;
import dariomorgrane.subscriber.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImplementation(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public boolean checkReceivedIdIsValid(long id) {
        List<Long> allIds = messageRepository.getAllIdsFromPurchaseAndSubscription();
        return !allIds.contains(id);
    }

    @Override
    public Long getLastIdFromPurchase() {
        return messageRepository.getLastIdFromPurchase();
    }

    @Override
    public Long getLastIdFromSubscription() {
        return messageRepository.getLastIdFromSubscription();
    }

}

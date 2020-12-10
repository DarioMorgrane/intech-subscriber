package dariomorgrane.subscriber.service;

import dariomorgrane.subscriber.model.Message;

public interface MessageService {

    boolean checkReceivedIdIsValid(long id);

    void saveMessage(Message message);

    Long getLastIdFromPurchase();

    Long getLastIdFromSubscription();

}

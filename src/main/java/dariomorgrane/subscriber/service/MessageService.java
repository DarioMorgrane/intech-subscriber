package dariomorgrane.subscriber.service;

import dariomorgrane.subscriber.model.Message;

public interface MessageService {

    boolean checkIdIsValid(long id);

    void saveMessage(Message message);

    Long getLastId();
}

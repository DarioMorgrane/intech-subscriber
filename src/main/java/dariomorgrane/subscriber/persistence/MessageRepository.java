package dariomorgrane.subscriber.persistence;

import dariomorgrane.subscriber.models.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    @Query(value = "SELECT id FROM Purchase ORDER BY id DESC LIMIT 1;", nativeQuery = true)
    Long getLastIdFromPurchase();

    @Query(value = "SELECT id FROM Subscription ORDER BY id DESC LIMIT 1;", nativeQuery = true)
    Long getLastIdFromSubscription();

}

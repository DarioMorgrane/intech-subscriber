package dariomorgrane.subscriber.repository;

import dariomorgrane.subscriber.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT id FROM Purchase ORDER BY id DESC LIMIT 1;", nativeQuery = true)
    Long getLastIdFromPurchase();

    @Query(value = "SELECT id FROM Subscription ORDER BY id DESC LIMIT 1;", nativeQuery = true)
    Long getLastIdFromSubscription();

    @Query(value = "SELECT id FROM Purchase UNION SELECT id FROM Subscription;", nativeQuery = true)
    List<Long> getAllIdsFromPurchaseAndSubscription();

}

package dariomorgrane.subscriber.repository;

import dariomorgrane.subscriber.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT id FROM Purchase UNION ALL SELECT id FROM Subscription ORDER BY id DESC;", nativeQuery = true)
    List<Long> getAllIdsSorted();

}

package dariomorgrane.subscriber.models;

import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_name")
public class Message implements Persistable<Long> {

    @Id
    private Long id;

    @Column
    private int msisdn;

    @Column
    private String action;

    @Column
    private long timestamp;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(int msisdn) {
        this.msisdn = msisdn;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msisdn=" + msisdn +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

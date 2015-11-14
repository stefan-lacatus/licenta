package ro.pub.acse.sapd.model.entities.logback;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by placatus on 15.11.2015.
 */
@Entity
public class LoggingEventProperty  implements Serializable {
    private LoggingEvent eventId;
    private String mappedKey;
    private String mappedValue;

    @Id
    @ManyToOne
    @JoinColumn(name = "event_id")
    public LoggingEvent getEventId() {
        return eventId;
    }

    public void setEventId(LoggingEvent eventId) {
        this.eventId = eventId;
    }

    @Id
    @Column(nullable = false)
    public String getMappedKey() {
        return mappedKey;
    }

    public void setMappedKey(String mappedKey) {
        this.mappedKey = mappedKey;
    }

    public String getMappedValue() {
        return mappedValue;
    }

    public void setMappedValue(String mappedValue) {
        this.mappedValue = mappedValue;
    }
}

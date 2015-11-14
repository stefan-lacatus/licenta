package ro.pub.acse.sapd.model.entities.logback;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class LoggingEventException implements Serializable {
    private LoggingEvent eventId;
    private short i;
    private String traceLine;

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
    public short getI() {
        return i;
    }

    public void setI(short i) {
        this.i = i;
    }

    @Column(nullable = false)
    public String getTraceLine() {
        return traceLine;
    }

    public void setTraceLine(String traceLine) {
        this.traceLine = traceLine;
    }
}

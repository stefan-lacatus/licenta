package ro.pub.acse.sapd.model.entities;


import ro.pub.acse.sapd.data.DataPoint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Used to initialize the dataPoint table
 */
@Entity
@IdClass(DataPointEntity.class)
@Table(name = "data_points",
        indexes = {@Index(name = "channel_index", columnList = "channel_id")})
public class DataPointEntity implements DataPoint, Serializable {
    private static final long serialVersionUID = 538836485799942437L;
    @Id
    @ManyToOne()
    @JoinColumn(name = "channel_id")
    private DataChannel channel;
    @Id
    private Date timestamp;
    @Id
    private String value;

    public DataChannel getChannel() {
        return channel;
    }

    public void setChannel(DataChannel channel) {
        this.channel = channel;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Date getTimeStamp() {
        return timestamp;
    }

    public void setTimeStamp(Date date) {
        this.timestamp = date;
    }
}

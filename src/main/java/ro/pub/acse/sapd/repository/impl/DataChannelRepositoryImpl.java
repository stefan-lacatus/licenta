package ro.pub.acse.sapd.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.repository.custom.DataChannelCustomRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;

@Component
public class DataChannelRepositoryImpl implements DataChannelCustomRepository {
    private final static String INSERT_DATA_QUERY = "INSERT INTO data_points(channel_id, value, timestamp) " +
            "VALUES (?, ?, ? )";
    @Autowired
    private DataSource dataSource;

    @Override
    public int addDataToTable(Long channelId, DataPoint data) {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // define query arguments
        Object[] params = new Object[]{channelId, data.getValue(),
                new Timestamp(data.getTimeStamp().getTime())};

        // define SQL types of the arguments
        int[] types = new int[]{Types.DOUBLE, Types.VARCHAR, Types.TIMESTAMP};

        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        return template.update(INSERT_DATA_QUERY, params, types);
    }
}

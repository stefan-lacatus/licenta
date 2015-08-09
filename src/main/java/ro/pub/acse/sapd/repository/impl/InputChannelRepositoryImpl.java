package ro.pub.acse.sapd.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import ro.pub.acse.sapd.data.DataPoint;
import ro.pub.acse.sapd.repository.custom.InputChannelCustomRepository;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by petrisor on 8/9/15.
 */
public class InputChannelRepositoryImpl implements InputChannelCustomRepository {
    private final static String INSERT_DATA_QUERY = "INSERT INTO input_data_points VALUES (?, ?, ?)";
    @Autowired
    private DataSource dataSource;

    @Override
    public void addDataToTable(Long channelId, Long inputBlockId, DataPoint data) {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        // define query arguments
        Object[] params = new Object[]{channelId, data.getValue(), data.getTimeStamp()};

        // define SQL types of the arguments
        int[] types = new int[]{Types.DOUBLE, Types.VARCHAR, Types.TIMESTAMP};

        // execute insert query to insert the data
        // return number of row / rows processed by the executed query
        int row = template.update(INSERT_DATA_QUERY, params, types);
        System.out.println(row + " row inserted.");
    }
}

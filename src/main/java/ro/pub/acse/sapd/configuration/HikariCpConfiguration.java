package ro.pub.acse.sapd.configuration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HikariCpConfiguration {

    @Value("${spring.dataSource.username}")
    private String user;
    @Value("${spring.dataSource.password}")
    private String password;
    @Value("${spring.dataSource.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${spring.dataSource.databaseName}")
    private String database;

    @Bean
    public DataSource primaryDataSource() {
        Properties dsProps = new Properties();
        dsProps.setProperty("user", user);
        dsProps.setProperty("password", password);
        Properties configProps = new Properties();
        configProps.setProperty("dataSourceClassName", dataSourceClassName);
        configProps.setProperty("dataSource.databaseName", database);
        HikariConfig hc = new HikariConfig(configProps);
        hc.setDataSourceProperties(dsProps);
        return new HikariDataSource(hc);
    }
}
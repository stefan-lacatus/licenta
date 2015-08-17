package ro.pub.acse.sapd.configuration;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariCpConfiguration {

    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.dataSourceClassName}")
    private String dataSourceClassName;
    @Value("${spring.datasource.database-name}")
    private String database;
    @Value("${spring.datasource.server-name}")
    private String server;

    @Bean
    public DataSource primaryDataSource() {
        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(10);
        config.setDataSourceClassName(dataSourceClassName);
        config.addDataSourceProperty("serverName", server);
        config.addDataSourceProperty("databaseName", database);
        config.addDataSourceProperty("user", user);
        config.addDataSourceProperty("password", password);
        return new HikariDataSource(config);
    }
}
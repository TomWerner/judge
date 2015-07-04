package edu.uiowa.acm.judge.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Tom on 6/21/2015.
 */
@Configuration
public class DatabaseConfig {
    private final Logger LOG = Logger.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Bean
    public DataSource datasource() {
        final org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(databasePassword);

        if ("create-drop".equals(ddlAuto)) {
            final JdbcTemplate template = new JdbcTemplate(ds);
            try {
                final Scanner scan = new Scanner(new File("data/initialize_tables.sql"));
                scan.useDelimiter(";");
                while (scan.hasNext()) {
                    final String statement = scan.next();
                    if (statement.charAt(0) != '#') {
                        template.execute(statement);
                    }
                }
            } catch (final FileNotFoundException e) {
                LOG.error("Error trying to load initialize_tables.sql. ", e);
            }
        }

        return ds;
    }
}

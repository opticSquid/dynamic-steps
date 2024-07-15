package experimental.dynamic_steps.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {
    // Datasource properties
    @ConfigurationProperties("spring.datasource.na-db")
    DataSourceProperties naDbProperties() {
        return new DataSourceProperties();
    }

    @ConfigurationProperties("spring.datasource.esis-db")
    DataSourceProperties esisDbProperties() {
        return new DataSourceProperties();
    }

    @ConfigurationProperties("spring.datasource.local-db")
    DataSourceProperties localDbProperties() {
        return new DataSourceProperties();
    }

    // Datasources
    // use this data source if region == 'NA'
    @ConfigurationProperties("spring.datasource.na-db.hikari")
    DataSource naDataSource() {
        return naDbProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    // use this data source if region == 'ESIS'
    @ConfigurationProperties("spring.datasource.esis-db.hikari")
    DataSource esisDataSource() {
        return esisDbProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    // this is the local data source where you will write the data
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.local-db.hikari")
    DataSource localDataSource() {
        return localDbProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    // jdbc templates
    JdbcTemplate nadbJdbcTemplate() {
        return new JdbcTemplate(naDataSource());
    }

    JdbcTemplate esisdbJdbcTemplate() {
        return new JdbcTemplate(esisDataSource());
    }

    @Bean
    @Primary
    JdbcTemplate localdbJdbcTemplate() {
        return new JdbcTemplate(localDataSource());
    }

    /**
     * @return returnns the jdbc template instance depending on the region
     *         This gives the functionality to choose the jdbcTemplate on the fly
     */
    @Bean
    @Primary
    Map<String, JdbcTemplate> jdbcTemplates() {
        Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();
        jdbcTemplateMap.put("NA", nadbJdbcTemplate());
        jdbcTemplateMap.put("ESIS", esisdbJdbcTemplate());
        // Add other entries for additional regions if needed
        return jdbcTemplateMap;
    }
}

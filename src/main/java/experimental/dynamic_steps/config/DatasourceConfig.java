package experimental.dynamic_steps.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatasourceConfig {
    // Datasource properties
    @Bean
    @ConfigurationProperties("spring.datasource.na-db")
    DataSourceProperties naDbProperties(){
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.esis-db")
    DataSourceProperties esisDbProperties(){
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("spring.datasource.local-db")
    DataSourceProperties localDbProperties(){
        return new DataSourceProperties();
    }

    // Datasources
    // use this data source if region == 'NA'
    @Bean
    @ConfigurationProperties("spring.datasource.na-db.hikari")
    DataSource naDataSource(){
        return naDbProperties()
        .initializeDataSourceBuilder()
        .build();
    }

    // use this data source if region == 'ESIS'
    @Bean
    @ConfigurationProperties("spring.datasource.esis-db.hikari")
    DataSource esisDataSource(){
        return esisDbProperties()
        .initializeDataSourceBuilder()
        .build();
    }

    // this gives the functionality to choose the datasource on the fly
    @Bean
    public Map<String, DataSource> dataSources() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("NA", naDataSource()); // Key based on your region identifier
        dataSourceMap.put("ESIS", esisDataSource()); // Key based on your region identifier
        // Add other entries for additional regions if needed
        return dataSourceMap;
    }

    // this is the local data source where you will write the data
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.local-db.hikari")
    DataSource localDataSource(){
        return localDbProperties()
        .initializeDataSourceBuilder()
        .build();
    }
    
    // jdbc templates
    @Bean
    JdbcTemplate nadbJdbcTemplate(@Qualifier("naDataSource") DataSource datasource){
        return new JdbcTemplate(datasource);
    }
    @Bean
    JdbcTemplate esisdbJdbcTemplate(@Qualifier("esisDataSource") DataSource datasource){
        return new JdbcTemplate(datasource);
    }
    @Bean
    @Primary
    JdbcTemplate localdbJdbcTemplate(@Qualifier("localDataSource") DataSource datasource){
        return new JdbcTemplate(datasource);
    }
}

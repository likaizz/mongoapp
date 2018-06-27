package com.example.mongoapp.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

/**
 * 配置neo4j的数据源
 */
@Configuration
public class Neo4jConfig {
    @Value("${spring.data.neo4j.uri}")
    private  String URI;
    @Value("${spring.data.neo4j.username}")
    private  String UAERNAME;
    @Value("${spring.data.neo4j.password}")
    private  String PASSWORD;
    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri(URI)
                .credentials(UAERNAME, PASSWORD)
                .build();
        return configuration;
    }
    @Bean
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(sessionFactory());
    }
    @Bean
    public SessionFactory sessionFactory() {
        return new SessionFactory(configuration(),"com.example.mongoapp.dao.neo4j");
    }

}

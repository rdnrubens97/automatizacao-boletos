package com.alra.service.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collections;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.alra.service.repository.cx230", transactionManagerRef = "cx230TransactionManager", entityManagerFactoryRef = "cx230EntityManager")
public class Cx230DbConfig {
    @Bean(name = "cx230DataSource")
    @ConfigurationProperties(prefix = "cx230.datasource")
    @Transactional
    public DataSource cx230DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cx230EntityManager", autowireCandidate = true)
    @Transactional
    public LocalContainerEntityManagerFactoryBean cx230EntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("cx230DataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.alra.service.model.cx230").build();
    }

    @Bean(name = "cx230TransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("cx230EntityManager")EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

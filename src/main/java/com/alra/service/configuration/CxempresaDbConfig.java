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

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.alra.service.repository.cxempresa", transactionManagerRef = "cxempresaTransactionManager", entityManagerFactoryRef = "cxempresaEntityManager")
public class CxempresaDbConfig {
    @Bean(name = "cxempresaDataSource")
    @Primary
    @ConfigurationProperties(prefix = "cxempresa.datasource")
    public DataSource cxempresaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "cxempresaEntityManager")
    @Primary
    public LocalContainerEntityManagerFactoryBean cxempresaEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("cxempresaDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.alra.service.model.cxempresa").build();
    }

    @Bean(name = "cxempresaTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("cxempresaEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}

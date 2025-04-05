package il.cshaifasweng.OCSFMediatorExample.server;

import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import java.util.Properties;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "il.cshaifasweng.OCSFMediatorExample.server.dal")
@EnableTransactionManagement
public class SpringConfig {
    @Bean
    public DataSource dataSource() {
        System.out.println("Creating DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/FinalProjectDB");
        dataSource.setUsername("root");
        dataSource.setPassword("9182736455");
        return dataSource;
    }

    /*@Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("il.cshaifasweng.OCSFMediatorExample.server.dal.models");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.getJpaPropertyMap().put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        emf.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
        emf.afterPropertiesSet();
        return emf.getObject();
    }*/

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("il.cshaifasweng.OCSFMediatorExample.server.dal.models");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        props.put("hibernate.show_sql", "true"); // Show SQL statements
        props.put("hibernate.format_sql", "true"); // Format SQL for readability
        props.put("hibernate.use_sql_comments", "true"); // Add comments to SQL
        em.setJpaProperties(props);

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }
}
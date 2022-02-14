package ua.com.alevel.hw_8_9_jpa_hibernate.configs;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class JpaConfig {

    @Value("${spring.datasource.url}")
    private String dataSoursUrl;
    @Value("${spring.datasource.username}")
    private String dataSourceUsername;
    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverName;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String jpaDdlAuto;
    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String jpaDialect;
    @Value("${spring.jpa.properties.hibernate.current_session_context_class}")
    private String sessionContextClass;
    @Value("${spring.jpa.properties.hibernate.enable_lazy_load_no_trans}")
    private String lazyLoadNoTrans;
    @Value("${spring.jpa.hibernate.naming.physical-strategy}")
    private String hibernatePhysicalStrategy;

    private static String SCAN_PACKAGE = "ua/com/alevel/hw_8_9_jpa_hibernate/entities";


    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dataSourceDriverName);
        dataSource.setUrl(dataSoursUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);
        return dataSource;
    }

    @Bean("entityManagerFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setPackagesToScan(SCAN_PACKAGE);
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setHibernateProperties(getHibernateProperties());
        try {
            sessionFactoryBean.afterPropertiesSet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactoryBean.getObject();
    }

    @Bean
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("spring.jpa.hibernate.ddl-auto", jpaDdlAuto);
        properties.put("spring.jpa.properties.hibernate.dialect", jpaDialect);
        properties.put("spring.jpa.properties.hibernate.current_session_context_class", sessionContextClass);
        properties.put("spring.jpa.properties.hibernate.enable_lazy_load_no_trans", lazyLoadNoTrans);
        properties.put("spring.jpa.hibernate.naming.physical-strategy", hibernatePhysicalStrategy);
        return properties;
    }

}

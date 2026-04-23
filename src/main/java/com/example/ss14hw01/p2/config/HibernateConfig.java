package com.example.ss14hw01.p2.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

public class HibernateConfig {
    // DataSource cung cấp các thông tin như url, username, password
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        // Thông tin kết nối đến database
        sessionFactory.setDataSource(dataSource);
        // Quét các thực thể để khởi tạo bảng sang database
        sessionFactory.setPackagesToScan("cntt1.ss13.model");
        // cấu hình thông số về hibernate dialect, show_sql, format_sql, ddl.auto
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.show_sql","true");
        properties.put("hibernate.format_sql","true");
        properties.put("hibernate.hbm2ddl.auto","update");
        // thêm thông số vào session factory
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}

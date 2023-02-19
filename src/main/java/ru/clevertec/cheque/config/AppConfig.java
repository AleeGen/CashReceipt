package ru.clevertec.cheque.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.Product;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.cheque")
public class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .addAnnotatedClass(DiscountCard.class)
                .buildSessionFactory();
    }
}

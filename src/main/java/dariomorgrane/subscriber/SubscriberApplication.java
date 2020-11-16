package dariomorgrane.subscriber;

import dariomorgrane.subscriber.controllers.SubscriberController;
import dariomorgrane.subscriber.models.Message;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class SubscriberApplication implements CommandLineRunner {

    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SubscriberApplication.class, args);
    }

    @Override
    public void run(String... args) {
        new Thread(() -> {
            new Scanner(System.in).nextLine();
            int exitCode = SpringApplication.exit(applicationContext, () -> 0);
            System.exit(exitCode);
        }).start();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder factory, DataSource dataSource,
            JpaProperties properties) {
        Map<String, Object> jpaProperties = new HashMap<>(properties.getProperties());
        jpaProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor());
        return factory.dataSource(dataSource).packages("dariomorgrane.subscriber")
                .properties(jpaProperties).build();
    }

    @Bean
    public EmptyInterceptor hibernateInterceptor() {
        return new EmptyInterceptor() {

            private Message currentMessage;

            @Override
            public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
                currentMessage = (Message) entity;
                return false;
            }

            @Override
            public String onPrepareStatement(String sql) {
                if (!sql.contains("ORDER BY id DESC LIMIT 1")) {
                    sql = sql.replace("table_name", currentMessage.getAction());
                }
                return sql;
            }
        };
    }

}

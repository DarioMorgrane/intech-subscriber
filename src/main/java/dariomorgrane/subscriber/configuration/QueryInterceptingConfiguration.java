package dariomorgrane.subscriber.configuration;

import dariomorgrane.subscriber.model.Message;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueryInterceptingConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factory,
                                                                       DataSource dataSource,
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
            public boolean onSave(Object entity,
                                  Serializable id,
                                  Object[] state,
                                  String[] propertyNames,
                                  Type[] types) {
                currentMessage = (Message) entity;
                return false;
            }

            @Override
            public String onPrepareStatement(String sql) {
                if (currentMessage != null) {
                    sql = sql.replace("table_name", currentMessage.getAction());
                }
                return sql;
            }
        };
    }

}

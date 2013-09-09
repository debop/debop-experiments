package kr.experiments.amqp.test.samples;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kr.experiments.amqp.test.samples.AbstractConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 9. 오후 4:47
 */
@Configuration
public abstract class AbstractConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("localhost");
        factory.setChannelCacheSize(16);
        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

}

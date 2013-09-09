package kr.experiments.amqp.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kr.experiments.amqp.config.AmqpConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 9. 오후 3:43
 */
@Configuration
public abstract class AbstractAmqpConfiguration {

    public String getHost() {
        return "localhost";
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(getHost());
        factory.setChannelCacheSize(16);
        return factory;
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setExchange("default.exchange");
        rabbitTemplate.setRoutingKey("default.key");
        rabbitTemplate.setReplyQueue(replyQueue());
        rabbitTemplate.setReplyTimeout(10 * 60 * 1000); // 10 Miutes

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());

        container.setQueues(replyQueue());
        container.setMessageListener(amqpTemplate());
        container.afterPropertiesSet();
        return container;
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("default.reply.queue");
    }

    @Bean
    public Queue tradeQueue() {
        Queue queue = new Queue("market.trade.queue");
        amqpAdmin().declareQueue(queue);

        return queue;
    }

    @Bean
    public Queue marketDataQueue() {
        return new Queue("market.data.queue", true, false, false);
    }

    @Bean
    public FanoutExchange broadcastResponses() {
        FanoutExchange fanout = new FanoutExchange("broadcast.reponses", true, false);
        BindingBuilder.bind(tradeQueue()).to(fanout);
        return fanout;
    }

    @Bean
    public TopicExchange marketDataExchange() {
        return new TopicExchange("market.topic.exchange", true, false);
    }
}

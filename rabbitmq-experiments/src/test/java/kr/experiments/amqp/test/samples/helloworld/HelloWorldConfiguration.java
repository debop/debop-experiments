package kr.experiments.amqp.test.samples.helloworld;

import kr.experiments.amqp.test.samples.AbstractConfiguration;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kr.experiments.amqp.test.samples.helloworld.HelloWorldConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 9. 오후 4:47
 */
@Configuration
public class HelloWorldConfiguration extends AbstractConfiguration {


    @Bean
    public org.springframework.amqp.core.Queue helloWorldQueue() {
        return new Queue("helloworld.queue");
    }
}

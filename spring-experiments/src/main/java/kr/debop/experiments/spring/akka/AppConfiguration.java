package kr.debop.experiments.spring.akka;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static kr.debop.experiments.spring.akka.SpringExtension.springExtProvider;

/**
 * kr.debop.experiments.spring.akka.AppConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 10:21
 */
@Configuration
@ComponentScan(basePackageClasses = { CountingActor.class })
public class AppConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("akkaJavaSpring");

        springExtProvider.get(system).initialize(applicationContext);
        return system;
    }
}

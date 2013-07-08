package kr.debop.spring.tests.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import kr.debop.experiments.spring.akka.AppConfiguration;
import kr.debop.experiments.spring.akka.CountingActor;
import lombok.extern.slf4j.Slf4j;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

import static kr.debop.experiments.spring.akka.CountingActor.Count;
import static kr.debop.experiments.spring.akka.SpringExtension.springExtProvider;

/**
 * kr.debop.spring.tests.akka.SpringTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 10:35
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfiguration.class })
public class SpringTest {

    @Autowired
    ApplicationContext ctx;

    @Test
    public void springTest() throws Exception {

        ActorSystem system = ctx.getBean(ActorSystem.class);
        ActorRef counter = system.actorOf(springExtProvider.get(system).props("countingActor"), "counter");

        counter.tell(new Count(), null);
        counter.tell(new Count(), null);
        counter.tell(new Count(), null);

        FiniteDuration duration = FiniteDuration.create(3, TimeUnit.SECONDS);
        Future<Object> result = Patterns.ask(counter, new CountingActor.Get(), Timeout.durationToTimeout(duration));

        Assertions.assertThat(Await.result(result, duration)).isEqualTo(3);

        system.shutdown();
        system.awaitTermination();
    }
}

package kr.debop.experiments.spring.akka;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.Props;
import org.springframework.context.ApplicationContext;
import scala.collection.Seq$;

/**
 * kr.debop.experiments.spring.akka.SpringExtension
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 10:30
 */
public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExt> {

    public static final SpringExtension springExtProvider = new SpringExtension();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {

        private volatile ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(String actorBeanName) {
            return
                Props.create(SpringActorProducer.class,
                             Seq$.MODULE$.newBuilder()
                                 .$plus$eq(applicationContext)
                                 .$plus$eq(actorBeanName)
                                 .result());
        }
    }
}

package kr.debop.experiments.spring.akka;

import akka.actor.UntypedActor;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * kr.debop.experiments.spring.akka.CountingActor
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 10:15
 */
@Named("countingActor")
@Scope("prototype")
public class CountingActor extends UntypedActor {

    public static class Count { }

    public static class Get { }

    final CountingService countingService;

    @Inject
    public CountingActor(@Named("countingService") CountingService countingService) {
        this.countingService = countingService;
    }

    private int count = 0;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Count) {
            count = countingService.increment(count);
        } else if (message instanceof Get) {
            getSender().tell(count, getSelf());
        } else {
            unhandled(message);
        }
    }
}

package kr.debop.experiments.spring.akka;

import javax.inject.Named;

/**
 * kr.debop.experiments.spring.akka.CountingService
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 8. 오후 10:18
 */
@Named("countingService")
public class CountingService {

    public int increment(int count) {
        return count + 1;
    }
}

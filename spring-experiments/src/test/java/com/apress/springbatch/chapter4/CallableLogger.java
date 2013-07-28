package com.apress.springbatch.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.concurrent.Callable;

/**
 * com.apress.springbatch.chapter4.CallableLogger
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 4:02
 */
@Slf4j
public class CallableLogger implements Callable<RepeatStatus> {
    @Override
    public RepeatStatus call() throws Exception {
        log.info("이 메소드는 다른 스레드에서 실행했습니다.");
        return RepeatStatus.FINISHED;
    }
}

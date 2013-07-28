package com.apress.springbatch.chapter2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Hello World Tasklet
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 6:13
 */
@Slf4j
public class HelloWorld implements Tasklet {

    private static final String HELLO_WORLD = "Hello, world!";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug("Tasklet execute: " + HELLO_WORLD);
        return RepeatStatus.FINISHED;
    }
}

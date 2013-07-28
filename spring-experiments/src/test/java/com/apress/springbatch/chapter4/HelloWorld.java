package com.apress.springbatch.chapter4;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * com.apress.springbatch.chapter4.HelloWorld
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 3:46
 */
@Slf4j
public class HelloWorld implements Tasklet {

    private static final String HELLO_WORLD = "Hello, %s";

    @Getter
    @Setter
    private String name;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String name = (String) chunkContext.getStepContext().getJobParameters().get("name");

        ExecutionContext jobContext = chunkContext.getStepContext()
                                                  .getStepExecution()
                                                  .getJobExecution()
                                                  .getExecutionContext();
        jobContext.put("user.name", name);

        log.info(String.format(HELLO_WORLD, name));

        return RepeatStatus.FINISHED;
    }
}

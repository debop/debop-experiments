package com.apress.springbatch.chapter4.jobs;

import com.apress.springbatch.BatchConfiguration;
import com.apress.springbatch.chapter4.HelloWorld;
import com.apress.springbatch.chapter4.JobLoggerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * com.apress.springbatch.chapter4.jobs.ListenerJobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 9:46
 */
@Slf4j
@Configuration
@EnableBatchProcessing(modular = true)
@Import(BatchConfiguration.class)
public class ListenerJobConfiguration extends AbstractJobConfiguration {

    @Bean(name = "helloWorld")
    @StepScope
    public HelloWorld helloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("Sunghyouk Bae");
        return helloWorld;
    }

    @Bean(name = "endTasklet")
    @StepScope
    public HelloWorld endTasklet() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("and Goodbye");
        return helloWorld;
    }

    @Bean
    public Step helloWorldStep() {
        return stepBuilders.get("helloWorldStep")
                           .tasklet(helloWorld())
                           .repository(jobRepository)
                           .transactionManager(transactionManager)
                           .build();
    }

    @Bean
    public Step endStep() {
        return stepBuilders.get("endStep")
                           .tasklet(endTasklet())
                           .repository(jobRepository)
                           .transactionManager(transactionManager)
                           .build();
    }

    @Bean
    public JobLoggerListener loggerListener() {
        return new JobLoggerListener();
    }

    @Bean
    public Job listenerJob() {

        Step step1 = helloWorldStep();
        Step step2 = endStep();

        /**
         * 이렇게 Step 순서가 가능한데, XML보다 힘든거 같다.
         */
        return jobBuilders.get("inheritenceJob")
                          .repository(jobRepository)
                          .listener(loggerListener())
                          .start(step1).on("FAILED").stopAndRestart(step2).on("COMPLETED").to(step2)
                          .from(step1).on("*").to(step2).end()
                          .build();
    }
}

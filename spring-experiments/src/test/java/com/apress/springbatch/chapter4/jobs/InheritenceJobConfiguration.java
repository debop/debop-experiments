package com.apress.springbatch.chapter4.jobs;

import com.apress.springbatch.BatchConfiguration;
import com.apress.springbatch.chapter4.ExampleJobListener;
import com.apress.springbatch.chapter4.HelloWorld;
import com.apress.springbatch.chapter4.ParameterValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * com.apress.springbatch.chapter4.jobs.InheritenceJobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 4:54
 */
@Slf4j
@Configuration
@EnableBatchProcessing(modular = true)
@Import(BatchConfiguration.class)
public class InheritenceJobConfiguration extends AbstractJobConfiguration {


    @Value("#{jobParameters[name]}") String name;

    @Bean(name = "helloWorld")
    @StepScope
    public HelloWorld helloWorld() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("Sunghyouk Bae");
        return helloWorld;
    }

    @Bean(name = "michealTasklet")
    @StepScope
    public HelloWorld michaelTasklet() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setName("Michael");
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
    public ParameterValidator myParameterValidator() {
        return new ParameterValidator();
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
    public Step helloMichaelStep() {
        return stepBuilders.get("helloMichaelStep")
                           .tasklet(michaelTasklet())
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
    public RunIdIncrementer idIncrementer() {
        return new RunIdIncrementer();
    }

    @Bean
    public ExampleJobListener myJobListener() {
        return new ExampleJobListener();
    }

    @Bean
    public Job inheritenceJob() {

        Step step1 = helloMichaelStep();
        Step step2 = endStep();

        /**
         * 이렇게 Step 순서가 가능한데, XML보다 힘든거 같다.
         */
        return jobBuilders.get("inheritenceJob")
                          .repository(jobRepository)
                          .incrementer(idIncrementer())
                          .listener(myJobListener())
                          .start(step1).on("FAILED").stopAndRestart(step2).on("COMPLETED").to(step2)
                          .from(step1).on("*").to(step2).end()
                          .build();
    }
}

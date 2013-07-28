package com.apress.springbatch.chapter2;

import com.apress.springbatch.BatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * kr.debop.spring.tests.batch.helloworld.JobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 7:15
 */
@Configuration
@Import(BatchConfiguration.class)
public class HelloWorldJobConfiguration {

    @Autowired
    JobBuilderFactory jobBuilders;

    @Autowired
    StepBuilderFactory stepBuilders;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;


    @Bean
    public Job helloWorldJob() throws Exception {
        return jobBuilders.get("helloWorldJob")
                          .repository(jobRepository)
                          .start(helloWorldStep()).build();
    }

    @Bean
    public Step helloWorldStep() throws Exception {
        return stepBuilders.get("helloWorldStep")
                           .tasklet(helloWorldTasklet())
                           .repository(jobRepository)
                           .transactionManager(transactionManager)
                           .build();
    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return new HelloWorld();
    }
}

package com.apress.springbatch.chapter4.jobs;

import com.apress.springbatch.chapter4.CallableLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.tasklet.CallableTaskletAdapter;
import org.springframework.context.annotation.Bean;

/**
 * com.apress.springbatch.chapter4.jobs.CallableJobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 7:46
 */
@Slf4j
public class CallableJobConfiguration extends AbstractJobConfiguration {

    @Bean
    public CallableLogger callableObject() {
        return new CallableLogger();
    }

    @Bean
    public CallableTaskletAdapter callableTaskletAdapter() throws Exception {
        CallableTaskletAdapter adapter = new CallableTaskletAdapter();
        adapter.setCallable(callableObject());
        adapter.afterPropertiesSet();
        return adapter;
    }

    @Bean
    public Job callableJob() throws Exception {
        return jobBuilders.get("callableJob")
                          .repository(jobRepository)
                          .start(callableStep())
                          .build();
    }

    @Bean
    Step callableStep() throws Exception {
        return stepBuilders.get("callableStep")
                           .tasklet(callableTaskletAdapter())
                           .repository(jobRepository)
                           .transactionManager(transactionManager)
                           .build();
    }
}

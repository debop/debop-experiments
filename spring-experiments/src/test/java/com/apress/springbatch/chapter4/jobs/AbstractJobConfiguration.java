package com.apress.springbatch.chapter4.jobs;

import com.apress.springbatch.BatchConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * com.apress.springbatch.chapter4.jobs.AbstractJobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 7:47
 */
@Slf4j
@Configuration
@EnableBatchProcessing(modular = true)
@Import(BatchConfiguration.class)
public abstract class AbstractJobConfiguration {

    @Autowired
    protected JobBuilderFactory jobBuilders;

    @Autowired
    protected StepBuilderFactory stepBuilders;

    @Autowired
    protected JobRepository jobRepository;

    @Autowired
    protected JobRegistry jobRegistry;

    @Autowired
    protected PlatformTransactionManager transactionManager;
}

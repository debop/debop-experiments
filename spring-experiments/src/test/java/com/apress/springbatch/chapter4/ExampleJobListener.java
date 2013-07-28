package com.apress.springbatch.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

/**
 * com.apress.springbatch.chapter4.ExampleJobListener
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 5:07
 */
@Slf4j
public class ExampleJobListener implements JobExecutionListener {

    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job 시작 전에 호출됩니다.");
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        log.info("Job 작업 후에 호출됩니다.");
    }
}

package com.apress.springbatch.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * com.apress.springbatch.chapter4.JobLoggerListener
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 9:40
 */
@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.debug("[{}] is beginning execution", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.debug("[{}] has completed with the status [{}]",
                  jobExecution.getJobInstance().getJobName(), jobExecution.getStatus());
    }
}

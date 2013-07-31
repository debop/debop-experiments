package com.apress.springbatch.chapter4.tests;

import com.apress.springbatch.chapter4.jobs.ListenerJobConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * com.apress.springbatch.chapter4.tests.ListenerJobTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 9:49
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ListenerJobConfiguration.class)
public class ListenerJobTest {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job listenerJob;

    @Test
    public void listenerJobTest() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("name", "sunghyouk bae")
                                                                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(listenerJob, jobParameters);
        assertThat(jobExecution).isNotNull();

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertThat(stepExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        }
    }
}

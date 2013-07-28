package com.apress.springbatch.chapter4;

import com.apress.springbatch.chapter4.jobs.InheritenceJobConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * com.apress.springbatch.chapter4.InheritenceJobTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 6:26
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InheritenceJobConfiguration.class)
public class InheritenceJobTest {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job inheritenceJob;

    @Test
    public void inheritenceJobTest() throws Exception {
        Map<String, JobParameter> parameters = new HashMap<String, JobParameter>();
        parameters.put("name", new JobParameter("sunghyouk bae"));
        JobParameters jobParameters = new JobParameters(parameters);

        JobExecution jobExecution = jobLauncher.run(inheritenceJob, new JobParameters(parameters));
        assertThat(jobExecution).isNotNull();

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertThat(stepExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        }
    }
}

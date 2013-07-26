package kr.debop.spring.tests.batch.helloworld;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Hello World Job 을 수행합니다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 7:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HelloWorldConfiguration.class, JobConfiguration.class })
public class HelloWorldTest {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job helloWorldJob;

    @Test
    public void configurationTest() {
        assertThat(helloWorldJob).isNotNull();
    }

    @Test
    public void runJob() throws Exception {
        JobExecution jobExecution = jobLauncher.run(helloWorldJob, new JobParameters());
        assertThat(jobExecution).isNotNull();

        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            assertThat(stepExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);
        }
    }
}

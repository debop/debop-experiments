package com.apress.springbatch.chapter4.jobs;

import com.apress.springbatch.BatchConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.PassThroughLineMapper;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;

/**
 * com.apress.springbatch.chapter4.jobs.StepJobConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 10:55
 */
@Slf4j
@Configuration
@EnableBatchProcessing(modular = true)
@Import(BatchConfiguration.class)
public class StepJobConfiguration extends AbstractJobConfiguration {

    @Value("#{jobParameters[inputFile]}") String inputFile;
    @Value("#{jobParameters[outputFile]}") String outputFile;

    @Bean
    public FileSystemResource inputFile() {
        return new FileSystemResource(inputFile);
    }

    @Bean
    public FileSystemResource outputFile() {
        return new FileSystemResource(outputFile);
    }

    @Bean
    public FlatFileItemReader<String> inputReader() throws Exception {
        FlatFileItemReader<String> reader = new FlatFileItemReader<String>();
        reader.setResource(inputFile());
        reader.setLineMapper(new PassThroughLineMapper());
        reader.afterPropertiesSet();

        return reader;
    }

    @Bean
    public FlatFileItemWriter<String> outputWriter() throws Exception {
        FlatFileItemWriter<String> writer = new FlatFileItemWriter<String>();
        writer.setResource(outputFile());
        writer.setLineAggregator(new PassThroughLineAggregator<String>());
        writer.afterPropertiesSet();

        return writer;
    }

    @Bean
    public Job stepJob() throws Exception {

        // 참고 : http://blog.codecentric.de/en/2013/06/spring-batch-2-2-javaconfig-part-1-a-comparison-to-xml/
        Step step = stepBuilders.get("step1")
                                .repository(jobRepository)
                                .<String, String>chunk(50)
                                .reader(inputReader())
                                .writer(outputWriter())
                                .build();

        return jobBuilders.get("stepJob")
                          .repository(jobRepository)
                          .start(step)
                          .build();
    }
}

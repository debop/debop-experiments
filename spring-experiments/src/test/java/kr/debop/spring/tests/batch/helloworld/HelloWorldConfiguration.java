package kr.debop.spring.tests.batch.helloworld;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * kr.debop.spring.tests.batch.helloworld.HelloWorldConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 6:25
 */
@Configuration
@EnableBatchProcessing
@ImportResource({ "classpath:spring-batch-schema.xml" })
public class HelloWorldConfiguration {

//    @Value("classpath:schema-drop-hsqldb.sql")
//    private Resource schemaDropScript;
//
//    @Value("classpath:schema-hsqldb.sql")
//    private Resource schemaScript;
//
//    @Bean
//    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//        final DataSourceInitializer initializer = new DataSourceInitializer();
//        initializer.setDataSource(dataSource);
//        initializer.setDatabasePopulator(databasePopulator());
//
//        return initializer;
//    }
//
//    private DatabasePopulator databasePopulator() {
//        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(schemaDropScript);
//        populator.addScript(schemaScript);
//
//        return populator;
//    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JobOperator jobOperator() throws Exception {
        SimpleJobOperator jobOperator = new SimpleJobOperator();
        jobOperator.setJobLauncher(jobLauncher());
        jobOperator.setJobRepository(jobRepository());
        jobOperator.setJobExplorer(jobExplorer());
        jobOperator.setJobRegistry(jobRegistry());

        jobOperator.afterPropertiesSet();
        return jobOperator;
    }

    @Bean
    public JobExplorer jobExplorer() throws Exception {
        JobExplorerFactoryBean factory = new JobExplorerFactoryBean();
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return (JobExplorer) factory.getObject();
    }

    @Bean
    public JobRegistry jobRegistry() {
        return new MapJobRegistry();
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry());
        return postProcessor;
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(transactionManager());

        factory.afterPropertiesSet();
        return factory.getJobRepository();
    }


    @Bean
    public PropertyPlaceholderConfigurer placeHolderProperties() throws Exception {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("batch.properties"));
        configurer.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setOrder(1);

        return configurer;
    }

}

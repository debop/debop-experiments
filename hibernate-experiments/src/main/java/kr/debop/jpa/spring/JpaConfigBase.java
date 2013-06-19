package kr.debop.jpa.spring;

import kr.debop4j.core.tools.StringTool;
import kr.debop4j.data.jdbc.JdbcTool;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * JPA 용 Spring Configuration 기본 클래스
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:14
 */
public abstract class JpaConfigBase {

    private static final Logger log = LoggerFactory.getLogger(JpaConfigBase.class);

    /** 매핑된 엔티티가 있는 Pagacke 명들 */
    abstract protected String[] getMappedPackageNames();

    /** Hibernate 설정 값 */
    protected Properties jpaProperties() {

        Properties props = new Properties();

        props.put(Environment.FORMAT_SQL, "true");
        props.put(Environment.HBM2DDL_AUTO, "create-drop"); // create | spawn | spawn-drop | update | validate | none
        props.put(Environment.SHOW_SQL, "true");
        props.put(Environment.RELEASE_CONNECTIONS, ConnectionReleaseMode.ON_CLOSE);
        props.put(Environment.AUTOCOMMIT, "true");
        props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        props.put(Environment.STATEMENT_BATCH_SIZE, "30");

        return props;
    }

    protected DataSource buildDataSource(String driverClass, String url, String username, String password) {
        return JdbcTool.getDataSource(driverClass, url, username, password);
    }

    public abstract DataSource dataSource();

    /**
     * {@link EntityManagerFactory}에 추가적인 작업을 수행할 수 있습니다.
     *
     * @param emf EntityManagerFactory 인스턴스
     */
    protected void setupEntityManagerFactory(EntityManagerFactory emf) {
        // Nothing to do.
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        log.info("EntityManagerFactory를 생성합니다...");

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaProperties(jpaProperties());

        String[] packageNames = getMappedPackageNames();
        if (packageNames != null) {
            log.info("JPA Entity들을 스캔합니다. packages=[{}]", StringTool.listToString(packageNames));
            factoryBean.setPackagesToScan(packageNames);
        }

        factoryBean.afterPropertiesSet();

        EntityManagerFactory emf = factoryBean.getObject();
        setupEntityManagerFactory(emf);

        log.info("EntityManagerFactory를 생성했습니다.");

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}

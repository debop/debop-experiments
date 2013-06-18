package kr.debop.jpa.spring;

import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * kr.debop.jpa.spring.HSqlJpaConfigBase
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:49
 */
public abstract class HSqlJpaConfigBase extends JpaConfigBase {

    public static final String DRIVER_NAME = "org.hsqldb.jdbcDriver";
    public static final String HSQL_DIALECT = "org.hibernate.dialect.HSQLDialect";

    @Override
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return buildDataSource(DRIVER_NAME,
                               "jdbc:hsqldb:mem:test;MVCC=TRUE;",
                               "sa",
                               "");
    }

    @Override
    protected Properties jpaProperties() {
        Properties props = super.jpaProperties();
        props.put(Environment.DIALECT, HSQL_DIALECT);
        return props;
    }
}

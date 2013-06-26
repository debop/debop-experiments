package kr.debop.hibernate.scala;

import kr.debop4j.data.hibernate.spring.HSqlConfigBase;
import kr.debop4j.hibernate.scala.model.Buddy;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * kr.debop.hibernate.scala.HibernateConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 26. 오후 4:11
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig extends HSqlConfigBase {

    @Override
    protected String[] getMappedPackageNames() {
        return new String[] {
                Buddy.class.getPackage().getName()
        };
    }
}

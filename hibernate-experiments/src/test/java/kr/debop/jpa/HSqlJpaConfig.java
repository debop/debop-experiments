package kr.debop.jpa;

import kr.debop.jpa.spring.HSqlJpaConfigBase;
import org.springframework.context.annotation.Configuration;

/**
 * kr.debop.jpa.HSqlConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:56
 */
@Configuration
public class HSqlJpaConfig extends HSqlJpaConfigBase {

    @Override
    protected String[] getMappedPackageNames() {
        return new String[]{
                kr.debop.jpa.domain.User.class.getPackage().getName()
        };
    }
}

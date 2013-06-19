package kr.debop.jpa;

import kr.debop.jpa.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PACKAGE_NAME.kr.debop.jpa.AppConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:55
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@Import({ JpaHSqlConfig.class })
public class AppConfig {

    // Spring Bean을 추가합니다.
}

package kr.debop.jpa;

import kr.debop.jpa.repository.UserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * PACKAGE_NAME.kr.debop.jpa.AppConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:55
 */
@Configuration
@EnableTransactionManagement
@ComponentScan( basePackageClasses = { UserRepository.class } )
@Import({ JpaHSqlConfig.class })
public class AppConfig {

    // Spring Bean을 추가합니다.
}

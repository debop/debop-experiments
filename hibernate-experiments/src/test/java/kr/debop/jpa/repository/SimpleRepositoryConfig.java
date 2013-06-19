package kr.debop.jpa.repository;

import kr.debop.jpa.JpaHSqlConfig;
import kr.debop.jpa.repository.simple.SimpleUserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * kr.debop.jpa.repository.SimpleRepositoryConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 11:40
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
@ComponentScan( basePackageClasses = { SimpleUserRepository.class } )
@Import( { JpaHSqlConfig.class } )
public class SimpleRepositoryConfig {


}

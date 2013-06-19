package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * kr.debop.jpa.repository.UserRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:18
 */
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom{
}

package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * kr.debop.jpa.repository.UserRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:18
 */
@Transactional
public interface UserRepository extends UserRepositoryCustom {

    User save(User user);

    User findOne(Serializable id);

    /** Find user by user name */
    @Transactional(readOnly = true)
    User findByUsername(String username);

    /**
     * Find users by last name
     *
     * @param lastname
     * @return
     */
    @Transactional(readOnly = true)
    List<User> findByLastname(String lastname);

    /**
     * Find users by first name
     *
     * @param firstname
     * @return
     */
    @Transactional(readOnly = true)
    List<User> findByFirstname(String firstname);
}

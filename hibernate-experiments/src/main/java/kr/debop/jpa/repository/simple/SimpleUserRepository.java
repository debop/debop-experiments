package kr.debop.jpa.repository.simple;

import kr.debop.jpa.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 * kr.debop.jpa.repository.simple.SimpleUserRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:38
 */
public interface SimpleUserRepository {

    User save(User user);

    User findOne(Serializable id);

    /** Find user by user name */
    User findByUsername(String username);

    /**
     * Find users by last name
     *
     * @param lastname
     * @return
     */
    List<User> findByLastname(String lastname);

    /**
     * Find users by first name
     *
     * @param firstname
     * @return
     */
    List<User> findByFirstname(String firstname);

    List<User> findByFirstnameOrLastname(String name);
}

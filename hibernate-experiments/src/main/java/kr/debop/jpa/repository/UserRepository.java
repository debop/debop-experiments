package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * kr.debop.jpa.repository.UserRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:18
 */
public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom {

    /**
     * Find user by user name
     */
    User findByUsername(String username);

    /**
     * Find users by last name
     * @param lastname
     * @return
     */
    List<User> findByLastname(String lastname);

    /**
     * Find users by first name
     * @param firstname
     * @return
     */
    @Query("select u from User u where u.firstname = ?1")
    List<User> findByFirstname(String firstname);
}

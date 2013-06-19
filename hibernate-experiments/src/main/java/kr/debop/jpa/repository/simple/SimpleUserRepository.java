package kr.debop.jpa.repository.simple;

import kr.debop.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * kr.debop.jpa.repository.simple.SimpleUserRepository
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:38
 */
public interface SimpleUserRepository extends JpaRepository<User, Long> {

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
    @Query( "select u from User u where u.firstname = ?1" )
    List<User> findByFirstname(String firstname);

    @Query( "select u from User u where u.firstname = :name or u.lastname = :name" )
    List<User> findByFirstnameOrLastname(@Param( "name" ) String name);
}

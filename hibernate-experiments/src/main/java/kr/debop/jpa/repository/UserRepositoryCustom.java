package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;

import java.util.List;

/**
 * Interface for repository functionality that ought to be implemented manually.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:19
 */
public interface UserRepositoryCustom {

    /**
     * Custom repository operation.
     *
     * @return
     */
    List<User> myCustomBatchOperation();
}

package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * kr.debop.jpa.repository.UserRepositoryImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:33
 */
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> myCustomBatchOperation() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        return em.createQuery(criteriaQuery).getResultList();
    }
}

package kr.debop.jpa.repository;

import kr.debop.jpa.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * kr.debop.jpa.repository.UserRepositoryImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 10:33
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;

    public UserRepositoryImpl() { }

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public List<User> myCustomBatchOperation() {
        CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public User findOne(Serializable id) {
        return em.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        Query query = em.createQuery("from User u where u.username = :name");
        query.setParameter("name", username);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> findByLastname(String lastname) {
        Query query = em.createQuery("from User u where u.lastname = :lastname");
        query.setParameter("lastname", lastname);
        return query.getResultList();
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        Query query = em.createQuery("from User u where u.firstname = :firstname");
        query.setParameter("firstname", firstname);
        return query.getResultList();
    }
}

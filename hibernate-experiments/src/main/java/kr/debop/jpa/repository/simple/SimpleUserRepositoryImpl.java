package kr.debop.jpa.repository.simple;

import kr.debop.jpa.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

/**
 * kr.debop.jpa.repository.simple.SimpleUserRepositoryImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 20. 오전 9:23
 */
@Repository
public class SimpleUserRepositoryImpl implements SimpleUserRepository {

    @PersistenceContext
    private EntityManager em;

    public SimpleUserRepositoryImpl() { }

    public SimpleUserRepositoryImpl(EntityManager em) {
        this.em = em;
    }


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

    @Override
    public List<User> findByFirstnameOrLastname(String name) {
        Query query = em.createQuery("from User u where u.firstname = :name or u.lastname = :name");
        query.setParameter("name", name);
        return query.getResultList();
    }
}

package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Address;
import web.model.User;

import javax.persistence.*;
import java.util.List;
/* Можно ли переиспользовать EntityManager после транзакции? https://stackoverflow.com/a/16147776
I would recommend creating a new EntityManager per transaction. This is the way JPA was designed. The EntityManager
should not be an expensive object to create. (the EntityManagerFactory is very expensive though, so ensure you only have one of those).
* */

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void addUser(User user) {
        mergeAddress(user);
        em.persist(user);
    }

    @Override
    public void updateUser(User user) {
        mergeAddress(user);
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        em.remove(em.merge(new User(id)));
    }

    @Override
    public User getUser(long id) {
        return em.find(User.class, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return (List<User>) em.createQuery("from User").getResultList();
    }

    @Override
    public void mergeAddress(User user) {
        TypedQuery<Address> query = em.createQuery("from Address a where a.city=:city and a.street=:street and a.building=:building", Address.class)
                .setParameter("city", user.getAddress().getCity())
                .setParameter("street", user.getAddress().getStreet())
                .setParameter("building", user.getAddress().getBuilding());
        List<Address> list = query.getResultList();
        if (list.size() == 1)
            user.setAddress(list.get(0));
    }
}

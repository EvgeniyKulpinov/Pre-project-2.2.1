package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {
        User user = null;
        try {
            Session session = sessionFactory.openSession();
            user = (User) session.createQuery("from User as u inner join fetch u.car as c " +
                            "where c.model = :model and c.series = :series")
                    .setParameter("model", model)
                    .setParameter("series", series)
                    .getSingleResult();
            session.close();
        } catch (NoResultException e) {
            System.out.println("Пользователя с такой машиной не существует");
        }
        return user;
    }
}

package ru.natapro.eatnfitbot.telegram.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.natapro.eatnfitbot.telegram.HibernateSessionFactoryUtil;
import ru.natapro.eatnfitbot.telegram.domain.Result;
import ru.natapro.eatnfitbot.telegram.domain.User;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findById(long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @Override
    public Result findResultById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Result.class, id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public void saveResult(Result result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(result);
        tx1.commit();
        session.close();
    }
}

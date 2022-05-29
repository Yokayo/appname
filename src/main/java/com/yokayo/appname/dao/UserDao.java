package com.yokayo.appname.dao;

import java.util.HashMap;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.yokayo.appname.entities.User;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import static org.hibernate.criterion.Restrictions.eq;

public class UserDao {
    
    @Inject
    private Session session;
    
    public UserDao() {}
    
    // сохранить пользователя
    public void save(User user) throws Exception {
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            session.persist(user);
            tx.begin();
            session.flush();
            tx.commit();
        } catch (Exception e) {
            System.err.println("Error saving user:");
            e.printStackTrace();
            ensureRollback(tx);
            throw e;
        }
    }
    
    // получить пользователя по юзернейму
    public User getByUsername(String username) throws Exception {
        try {
            return (User) session.createQuery("FROM User WHERE username = :username")
                        .setParameter("username", username)
                        .uniqueResult();
        } catch (Exception e) {
            System.err.println("Error getting user by username:");
            e.printStackTrace();
            throw e;
        }
    }
    
    // получить пользователя по идентификатору
    public User get(String id) {
        return (User) session.get(User.class, id);
    }
    
    private void ensureRollback(Transaction tx) {
        if (tx.getStatus() != TransactionStatus.COMMITTED && tx.getStatus() != TransactionStatus.ROLLED_BACK) {
            tx.rollback();
        }
    }
    
}
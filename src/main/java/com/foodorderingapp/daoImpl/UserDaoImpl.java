package com.foodorderingapp.daoImpl;

import com.foodorderingapp.dao.OrderDetailDAO;
import com.foodorderingapp.dao.UserDAO;
import com.foodorderingapp.dto.UserListMapperDto;
import com.foodorderingapp.exception.NotFoundException;
import com.foodorderingapp.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDAO")
public class UserDaoImpl implements UserDAO {

    private final SessionFactory sessionFactory;
    private final OrderDetailDAO orderDetailDAO;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory, OrderDetailDAO orderDetailDAO) {
        this.sessionFactory = sessionFactory;
        this.orderDetailDAO = orderDetailDAO;
    }

    public Boolean addUser(User user) {
        try {
            sessionFactory.getCurrentSession().persist(user);
            return true;
        } catch (Exception ex) {
            throw new NotFoundException("cannot add user");
        }
    }

    public List<User> getUsers() {
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class).getResultList();
    }

    public User getUser(int userId) {
        return sessionFactory.getCurrentSession().get(User.class, userId);
    }

    public User getUserByEmail(String userPassword,String email) {

        try {
            User user1 = sessionFactory
                    .getCurrentSession()
                    .createQuery("FROM User WHERE email=:email AND userPassword=:userPassword", User.class)
                    .setParameter("email", email)
                    .setParameter("userPassword", userPassword).getSingleResult();
            return user1;
        } catch (Exception ex) {
            throw new NotFoundException("user not found");
        }
    }

    public User getUserByEmailId(User user) {

        try {
            User user1 = sessionFactory.getCurrentSession().
                    createQuery("FROM User WHERE email=:email", User.class).
                    setParameter("email", user.getEmail()).
                    getSingleResult();
            System.out.println(user);
            return user1;
        } catch (Exception e) {
            return null;
        }
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    public List<UserListMapperDto> getByUserId(int userId) {
        Query qry = sessionFactory
                .getCurrentSession()
                .createNativeQuery("SELECT tbl_orders.order_id ,tbl_orders.ordered_date, tbl_users.first_name ," +
                        "tbl_users.middle_name,tbl_users.last_name,tbl_orders.user_id \n" +
                        "FROM tbl_orders\n" +
                        "INNER JOIN  tbl_users ON tbl_orders.user_id=tbl_users.user_id  \n" +
                        "WHERE tbl_orders.user_id=?\n" +
                        "AND tbl_orders.confirm=true","UserMapping")
                .setParameter(1, userId);
        return qry.getResultList();
    }
}


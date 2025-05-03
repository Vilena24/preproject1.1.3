package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users ("+
                "id SERIAL PRIMARY KEY, "+
                "first_name VARCHAR(50), "+
                "last_name VARCHAR(50), "+
                "age INT NOT NULL "+
                ")";
        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        Session session = null;
        Transaction transaction = null;

        try{
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            session.createNativeQuery(sql).executeUpdate();

            transaction.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        // Создаем объект пользователя
        User user = new User(name, lastName, age);

        Session session = null;
        Transaction transaction = null;

        // Открываем сессию Hibernate
        try{
            session = Util.getSessionFactory().openSession();
            //Начинаем транзакцию
            transaction = session.beginTransaction();

            //сохраняем пользователя
            session.save(user);

            //Завершаем транзакцию
            transaction.commit();
        }catch(Exception e){
            //В случае ошибки, откатываем транзакцию и выводим ошибку
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Session session = null;
        Transaction transaction = null;

        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            //находим пользователя по ID
            User user = (User) session.get(User.class, id);

            if (user != null) {
                //Если пользователь найден, удаляем его
                session.delete(user);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден.");
            }
            //завершаем транзакцию
            transaction.commit();
        } catch (Exception e){
            // в случае ошибки, откатываем транзакцию и выводим ошибку
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        Session session = null;
        Transaction transaction = null;

        try{
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            users = session.createQuery("FROM User").list();

            // Завершаем транзакцию
            session.getTransaction().commit();
        }catch(Exception e){
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {

        Session session = null;
        Transaction transaction = null;

        try{
            session = Util.getSessionFactory().openSession();
            // Начинаем транзакцию
            session.beginTransaction();

            // HQL запрос для удаления всех пользователей
            session.createQuery("DELETE FROM User").executeUpdate();

            //Завершаем транзакцию
            session.getTransaction().commit();
        } catch (Exception e){
            // В случае ошибки откатываем транзакцию и выводим стек ошибки
            e.printStackTrace();
        }

    }
}

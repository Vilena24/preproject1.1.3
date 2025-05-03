package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.*;

public class Util {
    private static final String URL = "jdbc:postgresql://localhost:5432/user_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";



    //Настройки для Hibernate
    private static final SessionFactory sessionFactory;
    static{
        try{
            //Конфигурация Hibernate
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                    .setProperty("hibernate.show_sql","true")
                    .addAnnotatedClass(User.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException("Ошибка инициализации Hibernate", e);
        }
    }

    //Метод для получения SessionFactory (Hibernate)
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }


//    // настройки для JDBC
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Методы для закрытия всех ресурсов
   public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии ResultSet: " + e.getMessage());
        }

        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии Statement: " + e.getMessage());
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при закрытии Connection: " + e.getMessage());
        }
   }

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(); // Теперь используется правильный метод
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                System.out.println("Data: " + resultSet.getString(1));
           }
        } catch (SQLException e) {
            System.err.println("Ошибка подключения или выполнения запроса: " + e.getMessage());
       } finally {
            closeResources(connection, statement, resultSet);
       }
    }
}
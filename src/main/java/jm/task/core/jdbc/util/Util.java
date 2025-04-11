package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class Util {

    // URL для подключения с отключением SSL и установкой часового пояса
    private static final String URL = "jdbc:mysql://localhost:3306/test?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    // методы для закрытия всех ресурсов
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        try{
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e ){
            System.err.println("Ошибка при закрытии ResultSet: " + e.getMessage());
        }

        try{
            if (statement != null){
                statement.close();
            }
        } catch (SQLException e){
            System.err.println("Ошибка при закрытии Statement: " + e.getMessage());
        }

        try{
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e){
            System.err.println("Ошибка при закрытии Connection: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

       try {
           connection = getConnection();

           statement = connection.createStatement();
           resultSet = statement.executeQuery("SELECT * FROM users");

           while (resultSet.next()){
               System.out.println("Data: " + resultSet.getString(1));
           }

       } catch (SQLException e ){
           System.err.println("Ошибка подключения или выполнения запроса: " + e.getMessage());
       } finally {
           closeResources(connection, statement, resultSet);
       }

    }
}

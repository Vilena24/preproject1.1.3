package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {

        // Проверка подключения
        try (Connection connection = Util.getConnection()) {
            System.out.println("Подключение к базе данных успешно");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
            return;
        }




        // 1. Создание таблицы пользователей
        userService.createUsersTable();
        System.out.println("Таблица пользователей создана");

        // 2. Добавление 4 пользователей
        userService.saveUser("Иван", "Иванов", (byte) 25);
        System.out.println("User с именем Иван добавлен в базу данных");

        userService.saveUser("Петр", "Петров", (byte) 30);
        System.out.println("User с именем Петр добавлен в базу данных");

        userService.saveUser("Мария", "Сидорова", (byte) 28);
        System.out.println("User с именем Мария добавлен в базу данных");

        userService.saveUser("Анна", "Смирнова", (byte) 22);
        System.out.println("User с именем Анна добавлен в базу данных");

        // 3. Получение всех пользователей и вывод в консоль
        System.out.println("\nСписок всех пользователей:");
        userService.getAllUsers().forEach(System.out::println);

        // 4. Очистка таблицы
        userService.cleanUsersTable();
        System.out.println("\nТаблица пользователей очищена");

        // 5. Удаление таблицы
        userService.dropUsersTable();
        System.out.println("Таблица пользователей удалена");


    }
}
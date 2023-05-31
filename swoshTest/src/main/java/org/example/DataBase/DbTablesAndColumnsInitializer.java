package org.example.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbTablesAndColumnsInitializer {

    static String query1 = """
            CREATE TABLE IF NOT EXISTS users (
                id int AUTO_INCREMENT PRIMARY KEY,
                account_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                password varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                first_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                last_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                signature_number INT UNSIGNED NOT NULL,
                ssn INT UNSIGNED NOT NULL UNIQUE,
                logged_in tinyint(1) NOT NULL DEFAULT 0
            );
            """;
    static String query2 = """
            CREATE TABLE IF NOT EXISTS accounts (
                id int AUTO_INCREMENT PRIMARY KEY,
                name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                account_created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                phone_number INT UNSIGNED NOT NULL,
                user_id int NOT NULL,
                balance INT UNSIGNED NOT NULL
            );
            """;
    static String query3 = """
            CREATE TABLE IF NOT EXISTS transactions (
                id int AUTO_INCREMENT PRIMARY KEY,
                sender int NOT NULL,
                recipient int NOT NULL,
                date_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                amount INT UNSIGNED NOT NULL
            );
            """;

    public DbTablesAndColumnsInitializer() {
        createTablesAndColumnsInDb();
    }

    private void createTablesAndColumnsInDb() {
        ArrayList<String> sqlSetTableAndColumns = new ArrayList<>();
        sqlSetTableAndColumns.add(query1);
        sqlSetTableAndColumns.add(query2);
        sqlSetTableAndColumns.add(query3);

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            for (String sqlSetTableAndColumn : sqlSetTableAndColumns) {
                conn.prepareStatement(sqlSetTableAndColumn).executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example.DataBase;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection singleton;
    private static MysqlDataSource dataSource;

    private DatabaseConnection() {
        dataSource = configureDataSource();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (singleton == null) {
            singleton = new DatabaseConnection();
        }
        return singleton;
    }

    private MysqlDataSource configureDataSource() {
        System.out.print("Configuring data source...");
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setUser(getDbCredential("username"));
        dataSource.setPassword(getDbCredential("password"));
        dataSource.setUrl(
                "jdbc:mysql://"
                        + getDbCredential("url")
                        + ":"
                        + getDbCredential("port")
                        + "/"
                        + getDbCredential("database")
                        + "?serverTimezone=UTC");

        try {
            dataSource.setUseSSL(false);
        } catch (SQLException ignore) {
            System.out.print("Error! Data source -> set use of SSL to 'false' failed.");
        }
        System.out.print("done!\n");

        return dataSource;
    }

    private String getDbCredential(String target) {
        try {
            return new JSONObject(new String(
                    Files.readAllBytes(
                            Paths.get("src/main/dbCredentials/dbConnectCredentials.json"))))
                    .get(target).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

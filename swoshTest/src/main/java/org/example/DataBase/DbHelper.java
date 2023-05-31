package org.example.DataBase;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    private static DbHelper singleton;

    private DbHelper() {

    }

    public static synchronized DbHelper getInstance() {
        if (singleton == null) {
            singleton = new DbHelper();
        }
        return singleton;
    }

    public Connection getConnection() throws SQLException {

        return DatabaseConnection.getInstance().getConnection();
    }


    public boolean executeUpdate(String sql, Object... parameters) {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;
            for (Object param : parameters) {

                preparedStatement.setObject(i++, param);
            }

            return preparedStatement.executeUpdate() == 1;

        } catch (SQLException sqlEx) {
            System.out.println("ignored line 62 exception -> " + sqlEx);
        }

        return false;
    }

    public <T> List<T> executeQuery(String sql, Class<T> returnType, Object... parameters) {
        List<T> list = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int i = 1;
            for (Object param : parameters) {

                preparedStatement.setObject(i, param);
                System.out.println("prepstate Object param # -> " + i + ", param -> " + param + ", param class -> " + param.getClass());
                i++;

            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                T obj = processResultSet(rs, returnType);
                list.add(obj);
            }
            System.out.println("executeQuery row 83 check\n");
            return list;

        } catch (SQLException sqlEx) {
            System.out.println("ignored line 62 exception -> " + sqlEx);
        }

        return null;
    }

    public <T> T processResultSet(ResultSet resultSet, Class<T> tClass) {
        System.out.println("executeQuery row 75 check\n");
        try {
            T obj = tClass.getDeclaredConstructor().newInstance();
            System.out.println("processing resultSet row 79");
            for (Field field : tClass.getDeclaredFields()) {
                field.setAccessible(true);

                System.out.println("Row 82 field.getName() => " + field.getName());

                field.set(obj, resultSet.getObject(field.getName()));

            }

            return obj;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException("Failed to create bean from ResultSet", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

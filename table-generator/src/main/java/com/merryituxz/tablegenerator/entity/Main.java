package com.merryituxz.tablegenerator.entity;

import com.merryituxz.tablegenerator.annotations.AutoIncrement;
import com.merryituxz.tablegenerator.annotations.Length;
import com.merryituxz.tablegenerator.annotations.NotNull;
import com.merryituxz.tablegenerator.annotations.PrimaryKey;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private final static int DEFAULT_LENGTH = 255;
    private final static Map<Class<?>, String> types = new HashMap<>();

    private final static String URL = "jdbc:mysql://localhost:3306/cloud?serverTimezone=Asia/Shanghai&useSSL=true";
    private final static String USERNAME = "log";
    private final static String PASSWORD = "log965019948";

    static {
        types.put(String.class, "varchar");
        types.put(Integer.class, "int");
        types.put(Date.class, "datetime");
    }

    private String getSQL(Class<?> cls, String tableName) throws IllegalAccessException {
        StringBuilder sql = new StringBuilder();
        sql.append("create table `").append(tableName).append("`(\n");
        Field[] fields = cls.getDeclaredFields();
        String fieldName;
        Length length;
        PrimaryKey primaryKey;
        NotNull notNull;
        AutoIncrement autoIncrement;
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Class<?> dataType = field.getType();
            length = field.getDeclaredAnnotation(Length.class);
            primaryKey = field.getDeclaredAnnotation(PrimaryKey.class);
            notNull = field.getDeclaredAnnotation(NotNull.class);
            autoIncrement = field.getDeclaredAnnotation(AutoIncrement.class);
            fieldName = field.getName();
            sql.append("`").append(fieldName).append("`").append(" ");
            sql.append(types.get(dataType));
            if (dataType != Date.class) {
                sql.append("(");
                if (length == null) {
                    sql.append(DEFAULT_LENGTH).append(")");
                } else {
                    sql.append(length.value()).append(")");
                }
            }
            if (primaryKey != null) sql.append(" PRIMARY KEY");
            if (notNull != null) sql.append(" NOT NULL");
            if (autoIncrement != null) sql.append(" AUTO_INCREMENT");
            if (i < fields.length - 1) sql.append(",\n");
        }
        sql.append("\n) ENGINE=innoDB DEFAULT CHARSET=utf8");
        System.out.println(sql.toString());
        return sql.toString();
    }

    public boolean createTable(Class<?> cls, String tableName) throws IllegalAccessException {
        String SQL = getSQL(cls, tableName);
        boolean result = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement statement = connection.createStatement();){
                result = statement.execute(SQL);
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IllegalAccessException {
        Main main = new Main();
        main.createTable(User.class, "tb_user");
    }
}

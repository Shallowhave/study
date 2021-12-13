package com.hk.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //数据库地址
        String url="jdbc:mysql://127.0.0.1:3306/test";
        //用户名
        String user="root";
        //密码
        String password="root";
        //连接到数据库
        Connection connection = DriverManager.getConnection(url, user, password);
        //执行的sql语句
        String sql="show databases";
        //创建Statement对象
        Statement statement = connection.createStatement();
        boolean execute = statement.execute(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        System.out.println(execute);

        while (resultSet.next()) {
            System.out.println(resultSet);
        }


    }
}

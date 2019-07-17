package com.jingjing.executor;

/**
 * @Author: 020188
 * @Date: 2019/7/8
 */


import com.jingjing.bean.User;
import com.jingjing.config.MyConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyExcutor封装jdbc的操作
 */
public class MyExcutor implements Excutor {
    private MyConfiguration xmlConfiguration = new MyConfiguration();
    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet set = null;
        PreparedStatement pre = null;
        try {
            pre = connection.prepareStatement(sql);
            //设置参数
            pre.setString(1, parameter.toString());
            set = pre.executeQuery();
            User u = new User();
            //遍历结果集
            while (set.next()) {
                u.setId(set.getString(1));
                u.setUsername(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T) u;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        Connection connection = xmlConfiguration.build("config.xml");
        return connection;
    }
}

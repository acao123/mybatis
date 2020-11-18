package com.rongda.sqlSession;


import com.rongda.bean.User;
import com.rongda.config.MyConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @class: MyExcutor.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 11:16
 **/
public class MyExecutor implements Executor {

    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {

        ResultSet set = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //设置参数
            ps.setString(1, parameter.toString());
            set = ps.executeQuery();
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
        }finally {
            try {
                if (set != null) {
                    set.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return xmlConfiguration.build("config.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

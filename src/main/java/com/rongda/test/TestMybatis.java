package com.rongda.test;

import com.rongda.bean.User;
import com.rongda.mapper.UserMapper;
import com.rongda.sqlSession.MySqlsession;

/**
 * @class: TestMybatis.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 12:23
 **/
public class TestMybatis {

    public static void main(String[] args) {
        MySqlsession sqlsession=new MySqlsession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}

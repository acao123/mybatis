package com.rongda.sqlSession;

import com.rongda.config.MyConfiguration;

import java.lang.reflect.Proxy;

/**
 * @class: MySqlsession.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 11:11
 **/
public class MySqlsession {

    private Executor executor = new MyExecutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    //动态代理调用
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new MyMapperProxy(myConfiguration, this));
    }

}

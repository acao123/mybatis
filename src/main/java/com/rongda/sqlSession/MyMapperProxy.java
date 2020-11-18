package com.rongda.sqlSession;

import com.rongda.config.Function;
import com.rongda.config.MapperBean;
import com.rongda.config.MyConfiguration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @class: MyMapperProxy.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 11:24
 **/
public class MyMapperProxy implements InvocationHandler {

    private MySqlsession mySqlsession;

    private MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration, MySqlsession mySqlsession) {
        this.myConfiguration = myConfiguration;
        this.mySqlsession = mySqlsession;
    }


    /**
     * 代理对象
     *
     * @param proxy  proxy
     * @param method method
     * @param args   args
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        MapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
        //是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())) {
            return null;
        }

        List<Function> functionList = readMapper.getList();
        if (Objects.isNull(functionList) || 0 == functionList.size()) {
            return null;
        }

        for (Function function : functionList) {
            //id是否和接口方法名一样
            if (method.getName().equals(function.getFuncName())) {
                return mySqlsession.selectOne(function.getSql(), String.valueOf(args[0]));
            }
        }
        return null;
    }


}

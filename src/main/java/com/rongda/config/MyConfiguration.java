package com.rongda.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @class: MyConfiguration.class
 * @description: 读取与解析配置信息，并返回处理后的environment
 * @author: acao
 * @create: 2020-11-18 09:56
 **/
public class MyConfiguration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    public Connection build(String resource) {
        try {
            return evalDataSource(getConfigRootNode(resource));
        } catch (Exception e) {
            throw new RuntimeException("read configuration is error " + resource);
        }
    }

    /**
     * 读取数据库配置文件
     *
     * @param node node
     */
    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!"database".equals(node.getName())) {
            throw new RuntimeException("root should be <database>");
        }


        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        for (Object item : node.elements("property")) {
            Element element = (Element) item;
            String text = getValue(element);
            String name = element.attributeValue("name");       //读取xml节点的name属性
            if (Objects.isNull(text) || Objects.isNull(name)) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }

            //赋值
            switch (name) {
                case "url":
                    url = text;
                    break;
                case "username":
                    username = text;
                    break;
                case "password":
                    password = text;
                    break;
                case "driverClassName":
                    driverClassName = text;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }

        Class.forName(driverClassName);
        Connection connection = null;
        try {
            //建立数据库链接
            assert url != null;
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();

        try {
            Element root = getConfigRootNode(path);
            //把mapping.xml 中nameSpace 节点的属性值作为接口名称
            mapper.setInterfaceName(root.attributeValue("nameSpace"));

            //遍历根节点下的所有子节点
            List<Function> list = new ArrayList<Function>(); //用来存储方法的List
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                Function function = new Function();
                Element e = (Element) rootIter.next();

                String sqltype = e.getName().trim();                               //sql类型 select/insert/delete/update
                String funcName = e.attributeValue("id").trim();                //接口上方法名
                String sql = e.getText().trim();                                   //sql
                String resultType = e.attributeValue("resultType").trim();      //返回值类型

                function.setSqltype(sqltype);
                function.setFuncName(funcName);
                function.setSql(sql);

                //初始化返回值
                try {
                    function.setResultType(Class.forName(resultType).newInstance());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                list.add(function);
            }
            mapper.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return mapper;
    }


    /**
     * 读取配置文件的根节点
     *
     * @param path config path
     */
    private Element getConfigRootNode(String path) throws DocumentException {
        InputStream is = loader.getResourceAsStream(path);

        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        return document.getRootElement();
    }

    /**
     * 获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
     *
     * @param node node
     */
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }


}

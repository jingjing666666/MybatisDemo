package com.jingjing.config;

/**
 * @Author: 020188
 * @Date: 2019/7/8
 */

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 */
public class MyConfiguration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml信息并处理
     */
    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }

    }

    /**
     * 连接数据源
     *
     * @param node
     * @return
     * @throws ClassNotFoundException
     */
    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]:<property> should contain name and value");
            }
            //赋值
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
            }
        }

        Class.forName(driverClassName);
        Connection connection = null;
        try {
            //建立数据库连接
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 获取property属性的值，如果有value值，则读取 ，没有设置value则读取内容
     *
     * @param node
     * @return
     */
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    /**
     * 读取mapper文件，并将mapper文件转成mapper对象
     *
     * @param path
     * @return
     */
    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        try {
            InputStream stream = loader.getResourceAsStream(path);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            //xml文件的根结点
            Element root = document.getRootElement();
            //把mapper节点的nameSpace值存为接口名
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
            //用来储存方法的list
            List<Function> list = new ArrayList<>();
            for (Iterator rootIter = root.elementIterator(); rootIter.hasNext(); ) {
                //用来储存一条方法的信息
                Function fun = new Function();
                Element e = (Element) rootIter.next();
                String sqltype = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqltype(sqltype);
                fun.setFuncName(funcName);
                Object newInstance = null;
                newInstance = Class.forName(resultType).newInstance();
                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }
            mapper.setList(list);
        } catch (Exception e) {

        }
        return mapper;
    }


}

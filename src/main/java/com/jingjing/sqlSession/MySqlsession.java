package com.jingjing.sqlSession;

import com.jingjing.config.MyConfiguration;
import com.jingjing.executor.Excutor;
import com.jingjing.executor.MyExcutor;
import com.jingjing.mapper.MyMapperProxy;

import java.lang.reflect.Proxy;

/**
 * @Author: 020188
 * @Date: 2019/7/8
 * MySqlSession中成员变量有Excutor和MyConfiguration，
 * 代码的精髓在getMapper的方法里
 */
public class MySqlsession {
    private Excutor excutor = new MyExcutor();
    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String statement,Object parameter){
        return  excutor.query(statement,parameter);
    }

    public <T> T getMapper(Class<T> clazz){
        //动态代理调用
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(myConfiguration,this));
    }
}


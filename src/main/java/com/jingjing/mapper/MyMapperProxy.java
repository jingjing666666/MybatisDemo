package com.jingjing.mapper;

import com.jingjing.config.Function;
import com.jingjing.config.MapperBean;
import com.jingjing.config.MyConfiguration;
import com.jingjing.sqlSession.MySqlsession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: 020188
 * @Date: 2019/7/11
 * MyMapperProxy代理类完成xml方法和真实方法对应，执行查询
 */
public class MyMapperProxy implements InvocationHandler {
    private MySqlsession sqlsession;

    private MyConfiguration myConfiguration ;

    public MyMapperProxy( MyConfiguration myConfiguration,MySqlsession sqlsession) {
        this.sqlsession = sqlsession;
        this.myConfiguration = myConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper = myConfiguration.readMapper("UserMapper.xml");
        //是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
            return null;
        }
        List<Function> list = readMapper.getList();
        if (list!=null||list.size()!=0){
            for (Function function: list){
                //id是否和接口方法名一样
                if (method.getName().equals(function.getFuncName())){
                    return sqlsession.selectOne(function.getSql(),String.valueOf(args[0]));
                }
            }
        }

        return null;
    }
}

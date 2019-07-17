package com.jingjing;

import com.jingjing.bean.User;
import com.jingjing.mapper.UserMapper;
import com.jingjing.sqlSession.MySqlsession;

/**
 * @Author: 020188
 * @Date: 2019/7/11
 */
public class TestMybatis {
    public static void main(String[] args) {
        MySqlsession sqlsession = new MySqlsession();
        UserMapper mapper = sqlsession.getMapper(UserMapper.class);
        User user = mapper.getUserById("1");
        System.out.println(user);
    }
}

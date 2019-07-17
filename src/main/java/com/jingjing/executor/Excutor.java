package com.jingjing.executor;

/**
 * @Author: 020188
 * @Date: 2019/7/8
 */
public interface Excutor {
    public <T> T query(String statement,Object parameter);
}

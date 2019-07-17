package com.jingjing.config;



import java.util.List;

/**
 * @Author: 020188
 * @Date: 2019/7/8
 */
public class MapperBean {
    private String interfaceName;//接口名
    private List<Function> list;//接口下所有方法
//获取接口名
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}

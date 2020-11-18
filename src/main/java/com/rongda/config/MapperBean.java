package com.rongda.config;


import java.util.List;

/**
 * @class: MapperBean.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 10:39
 **/
public class MapperBean {

    private String interfaceName; //接口名
    private List<Function> list; //接口下所有方法

    public MapperBean() {
    }

    public MapperBean(String interfaceName, List<Function> list) {
        this.interfaceName = interfaceName;
        this.list = list;
    }

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

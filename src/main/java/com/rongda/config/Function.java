package com.rongda.config;


/**
 * @class: Function.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 10:41
 **/
public class Function {

    private String sqltype;
    private String funcName;
    private String sql;
    private Object resultType;
    private String parameterType;

    public Function() {
    }

    public Function(String sqltype, String funcName, String sql, Object resultType, String parameterType) {
        this.sqltype = sqltype;
        this.funcName = funcName;
        this.sql = sql;
        this.resultType = resultType;
        this.parameterType = parameterType;
    }

    public String getSqltype() {
        return sqltype;
    }

    public void setSqltype(String sqltype) {
        this.sqltype = sqltype;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object getResultType() {
        return resultType;
    }

    public void setResultType(Object resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
}

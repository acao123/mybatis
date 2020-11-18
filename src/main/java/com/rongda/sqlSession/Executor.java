package com.rongda.sqlSession;

public interface Executor {

    <T> T query(String statement, Object parameter);
}

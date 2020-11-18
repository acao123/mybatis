package com.rongda.mapper;

import com.rongda.bean.User;

/**
 * @class: UserMapper.class
 * @description:
 * @author: acao
 * @create: 2020-11-18 09:52
 **/
public interface UserMapper {

    User getUserById(String id);
}

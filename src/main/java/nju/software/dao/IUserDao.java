package nju.software.dao;

import nju.software.model.User;

import java.util.List;

/**
 * 用户的持久层接口。
 * 需要实现：查询。修改。删除。添加。
 * 由mybatis自动完成Impl(实现)
 */

public interface IUserDao {
    /**
     * 查询索引操作
     */
    List<User> findAll();

    Integer addUser(User u);

    User findByNickname(String nickname);

    Integer deleteByNickname(String nickname);

    Integer updateUser(User u);

    Integer updateUserImg(User u);

    Integer updateUserPrivateInfo(User u);
}

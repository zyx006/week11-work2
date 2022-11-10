package cn.czyx007.week11.service;

import cn.czyx007.week11.bean.User;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface UserService {
    /**
     * 用户登录(使用动态代理完成事务控制)
     * @param username 输入的用户登录名
     * @param password 输入的用户密码
     * @return 内部验证密码后返回User对象，若返回null表示密码错误
     */
    User login(String username, String password);

    /**
     * 修改用户密码(使用动态代理完成事务控制)
     * @param username 已登录的用户名
     */
    void changePassword(String username);
}

package cn.czyx007.week11.dao;

import cn.czyx007.week11.bean.User;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface UserDAO {
    /**
     * 修改用户密码
     * @param username 用户登录名
     * @param password 新密码
     * @return 影响行数(1为修改成功，0为修改失败)
     */
    int updatePasswordByUsername(String username, String password);

    /**
     * 验证密码是否正确
     * @param username 用户名
     * @param password 密码
     * @return 密码正确返回true，密码错误或用户不存在返回false
     */
    boolean checkPassword(String username, String password);

    /**
     * 根据用户登录名获取用户User对象
     * @param username 用户登录名
     * @return 用户User对象
     */
    User selectByUsername(String username);
}

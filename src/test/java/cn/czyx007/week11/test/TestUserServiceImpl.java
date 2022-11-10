package cn.czyx007.week11.test;

import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.UserService;
import cn.czyx007.week11.service.impl.UserServiceImpl;
import org.junit.Test;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10 - 14:17
 */
public class TestUserServiceImpl {
    @Test
    public void testLogin(){
        UserService userService = (UserService) new TransactionProxyFactory(new UserServiceImpl()).getProxy();
        System.out.println(userService.login("abc", "123456"));
    }

    @Test
    public void testChangePassword(){
        UserService userService = (UserService) new TransactionProxyFactory(new UserServiceImpl()).getProxy();
        userService.changePassword("abc");
    }
}

package cn.czyx007.week11;

import cn.czyx007.week11.bean.User;
import cn.czyx007.week11.controller.MainMenu;
import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.UserService;
import cn.czyx007.week11.service.impl.UserServiceImpl;

import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class Driver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = (UserService) new TransactionProxyFactory(new UserServiceImpl()).getProxy();

        System.out.println("欢迎使用超市收银系统，请登陆：");

        int loginCnt = 0;
        User user;
        while (true) {
            System.out.print("请输入用户名：");
            String username = scanner.nextLine();
            System.out.print("请输入密码：");
            String password = scanner.nextLine();

            user = userService.login(username, password);
            if (user != null) {
                System.out.println("登录成功");
                MainMenu.mainMenu(user);
            }else {
                if (loginCnt++ < 2) {
                    System.out.println("用户名或密码不正确，请重新输入");
                }
                else {
                    System.out.println("最多只能尝试 3 次");
                    break;
                }
            }
        }
    }
}

package cn.czyx007.week11.service.impl;

import cn.czyx007.week11.bean.User;
import cn.czyx007.week11.dao.UserDAO;
import cn.czyx007.week11.dao.impl.UserDAOImpl;
import cn.czyx007.week11.service.UserService;
import cn.czyx007.week11.utils.PasswordUtil;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class UserServiceImpl implements UserService {
    private static UserDAO userDAO = new UserDAOImpl();

    @Override
    public User login(String username, String password) {
        User user = null;
        if (userDAO.checkPassword(username, password)) {
            user = userDAO.selectByUsername(username);
        }
        return user;
    }

    @Override
    public void changePassword(String username) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请输入当前用户的原密码：");
            if (!userDAO.checkPassword(username, scanner.nextLine())) {
                System.out.println("原密码输入不正确，请重新输入");
            } else {
                break;
            }
        }

        String newPassword;
        System.out.println("请设置新的密码：");
        while (true) {
            newPassword = scanner.nextLine();
            if (PasswordUtil.checkSecurity(newPassword)) {
                break;
            } else {
                System.out.println(PasswordUtil.PASSWORD_SECURITY_ERROR);
            }
        }

        String ensurePassword;
        System.out.println("请设置新的密码：");
        while (true) {
            ensurePassword = scanner.nextLine();
            if (newPassword.equals(ensurePassword)) {
                if (userDAO.updatePasswordByUsername(username, newPassword) == 1) {
                    System.out.println("您已成功修改密码，请谨记");
                } else {
                    System.out.println("密码修改失败！");
                }
                break;
            } else {
                System.out.println("两次输入的密码必须一致，请重新输入确认密码：");
            }
        }

        try {
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

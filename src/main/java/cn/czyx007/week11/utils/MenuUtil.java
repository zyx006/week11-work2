package cn.czyx007.week11.utils;

import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class MenuUtil {
    //静态方法工具类，封闭构造函数
    private MenuUtil(){}

    /**
     * 检查菜单选项输入合法性
     * @param low  选项下界
     * @param high 选项上界
     * @return 合法选项值(不为 - 1)
     */
    public static int checkInput(int low, int high) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextInt()) {
            int opt = scanner.nextInt();
            if (opt >= low && opt <= high) {
                return opt;
            }
            System.out.println("输入无效，只能输入" + low + "-" + high + "的数字");
        } else {
            System.out.println("输入无效，只能输入" + low + "-" + high + "的数字\n请重新输入：");
            scanner.next();
        }
        return -1;
    }
}

package cn.czyx007.week11.controller;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.bean.User;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.utils.ExcelUtil;
import cn.czyx007.week11.utils.MenuUtil;
import cn.czyx007.week11.utils.ProductUtil;
import cn.czyx007.week11.utils.TxtUtil;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * 商品维护菜单
 * @author : 张宇轩
 * @createTime : 2022/11/10 - 19:53
 */
public class GoodMaintenanceMenu {
    public static void goodMaintenanceMenu(User user) {
        if ("管理员".equals(user.getRole())) {
            while (true) {
                int opt;
                System.out.print("===超市商品管理维护====\n" +
                        "1、从 excel 中导入数据\n" +
                        "2、从文本文件导入数据\n" +
                        "3、键盘输入\n" +
                        "4、商品查询\n" +
                        "5、返回主菜单\n" +
                        "请选择（1-5）：");
                do {
                    opt = MenuUtil.checkInput(1, 5);
                } while (opt == -1);

                switch (opt) {
                    case 1://从excel中导入数据
                        ExcelUtil.loadDataFromExcel();
                        break;
                    case 2://从文本文件导入数据
                        TxtUtil.loadDataFromTxt();
                        break;
                    case 3://键盘输入数据
                        ProductUtil.loadDataFromKeyboard();
                        break;
                    case 4://商品查询
                        productQuery();
                        break;
                    case 5://返回主菜单
                        return;
                }
            }
        } else {
            System.out.println("当前用户没有执行该项功能的权限");
            try {
                System.out.print("按任意键返回主菜单");
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 商品查询
     * 根据输入的商品名称进行模糊查询
     */
    public static void productQuery(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入查询的商品名称：");
        String productName = scanner.nextLine();

        List<Product> products = new ProductDAOImpl().selectByProductName(productName);
        if (products.size() > 0) {
            System.out.printf("满足条件的记录总共%d条，信息如下：\n", products.size());
            System.out.println("序号 条形码 商品名称  单价\t供应商");
            System.out.println("=== =====  =======   \t====\t=====");

            for (int i = 0; i < products.size(); i++) {
                System.out.print((i + 1) + "\t" + products.get(i).getBarCode() + "\t" + products.get(i).getProductName() + "\t");
                System.out.printf("%- 8.2f\t", products.get(i).getPrice());
                System.out.println(products.get(i).getSupply());
            }
        } else {
            System.out.println("不存在满足条件的记录！");
        }

        try {
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

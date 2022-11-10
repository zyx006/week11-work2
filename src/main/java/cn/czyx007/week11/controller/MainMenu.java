package cn.czyx007.week11.controller;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.bean.SaleDetail;
import cn.czyx007.week11.bean.User;
import cn.czyx007.week11.dao.SaleDetailDAO;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.dao.impl.SaleDetailDAOImpl;
import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.SaleDetailService;
import cn.czyx007.week11.service.UserService;
import cn.czyx007.week11.service.impl.SaleDetailServiceImpl;
import cn.czyx007.week11.service.impl.UserServiceImpl;
import cn.czyx007.week11.utils.MenuUtil;
import cn.czyx007.week11.utils.ProductUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

/**
 * 主菜单
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class MainMenu {
    private static User user = null;

    public static void mainMenu(User user) {
        MainMenu.user = user;
        UserService userService = (UserService) new TransactionProxyFactory(new UserServiceImpl()).getProxy();

        Scanner scanner = new Scanner(System.in);
        int opt;
        while (true) {
            System.out.printf("\n===超市收银系统=== \n" +
                    "1、收银\n" +
                    "2、查询统计\n" +
                    "3、商品维护\n" +
                    "4、修改密码\n" +
                    "5、数据导出\n" +
                    "6、退出\n" +
                    "当前收银员：" + user.getChrName() + "\n请选择（1-6）：");
            do {
                opt = MenuUtil.checkInput(1, 6);
            } while (opt == -1);

            switch (opt) {
                case 1://1、收银
                    cashier();
                    break;
                case 2://2、查询统计
                    querySaleDetailsBySaleTime();
                    break;
                case 3://3、商品维护
                    GoodMaintenanceMenu.goodMaintenanceMenu(user);
                    break;
                case 4://4、修改密码
                    userService.changePassword(user.getUsername());
                    break;
                case 5://5、数据导出
                    DataExportMenu.dataExportMenu();
                    break;
                case 6://6、退出
                    System.out.println("您确认退出系统吗（y/n）");
                    String exitOpt = scanner.nextLine();
                    if ("y".equalsIgnoreCase(exitOpt)) {
                        System.out.println("欢迎下次继续使用");
                        System.exit(0);
                    } else {
                        break;
                    }
            }
        }
    }

    /**
     * 功能1：收银
     */
    public static void cashier() {
        Scanner scanner = new Scanner(System.in);
        ProductDAOImpl productDAO = new ProductDAOImpl();

        System.out.println("请输入商品条形码（6 位数字字符）：");
        String barCode;
        while (true) {
            barCode = scanner.nextLine();
            if (ProductUtil.checkBarCodeFormat(barCode)) {
                if (productDAO.checkBarCodeExist(barCode)) {
                    System.out.println("请输入商品数量：");
                    try {
                        int count = Integer.parseInt(scanner.nextLine());

                        //生成流水号
                        String todayDateStr = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
                        String lastNo = new SaleDetailDAOImpl().getLastNo(todayDateStr);
                        String lsh = todayDateStr + lastNo;

                        Product product = productDAO.selectByBarCode(barCode);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SaleDetailService saleDetailService = (SaleDetailService) new TransactionProxyFactory(new SaleDetailServiceImpl()).getProxy();

                        boolean addSuccess = saleDetailService.addSaleDetail(new SaleDetail(lsh, barCode, product.getProductName(),
                                product.getPrice(), count, user.getUsername(), sdf.parse(sdf.format(System.currentTimeMillis()))));
                        if (addSuccess) {
                            System.out.println("成功添加一笔销售数据");
                        } else {
                            System.out.println("添加失败！");
                        }
                    } catch (Exception e) {
                        System.out.println("添加失败！");
                    }
                    break;
                } else {
                    System.out.println("您输入的商品条形码不存在，请确认后重新输入");
                }
            } else {
                System.out.println("条形码输入格式不正确，请重新输入");
            }
        }

        try {
            System.out.println("按任意键返回主菜单");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能2：查询统计
     * 查询统计指定日期所有的销售信息
     */
    public static void querySaleDetailsBySaleTime() {
        Scanner scanner = new Scanner(System.in);
        SaleDetailDAO saleDetailDAO = new SaleDetailDAOImpl();
        System.out.println("请输入销售日期（yyyy-MM-dd）:");
        while (true) {
            String dateStr = scanner.nextLine();
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                String[] dateDetail = dateStr.split("-");

                List<SaleDetail> saleDetails = saleDetailDAO.selectBySaleTime(dateStr);
                int productNum = saleDetailDAO.getProductSumBySaleTime(dateStr);
                double saleMoney = saleDetailDAO.getSaleMoneyBySaleTime(dateStr);

                System.out.println(dateDetail[0] + " 年 " + dateDetail[1] + " 月 " + dateDetail[2] + " 日销售如下");
                System.out.println("\t流水号\t\t商品名称\t\t 单价\t数量\t\t金额\t\t\t时间\t\t收银员\n" +
                        "=============\t=======\t\t======\t====\t========\t===================\t======");
                if (saleDetails.size() != 0) {
                    for (SaleDetail saleDetail : saleDetails) {
                        saleDetail.showInfo();
                        System.out.println(user.getUsername());
                    }
                }
                System.out.println("销售总数：" + saleDetails.size() + " 商品总件：" + productNum + " 销售总金额：" + saleMoney);
                System.out.println("日期：" + dateDetail[0] + " 年 " + dateDetail[1] + " 月 " + dateDetail[2] + " 日");
                break;
            } catch (ParseException e) {
                System.out.println("你输入的日期格式不正确，请重新输入");
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

package cn.czyx007.week11.utils;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.ProductService;
import cn.czyx007.week11.service.impl.ProductServiceImpl;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class ProductUtil {
    //静态方法工具类，封闭构造函数
    private ProductUtil(){}

    /**
     * 检验条形码格式（6 位数字字符）
     * @param barCode 待检验的条形码
     * @return true：格式正确  false：格式错误
     */
    public static boolean checkBarCodeFormat(String barCode) {
        if (barCode.length() == 6) {
            for (int i = 0; i < 6; i++) {
                char each = barCode.charAt(i);
                if (!(each >= '0' && each <= '9')) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 从键盘录入商品信息
     */
    public static void loadDataFromKeyboard() {
        ProductDAOImpl productDAO = new ProductDAOImpl();
        ProductService productService = (ProductService) new TransactionProxyFactory(new ProductServiceImpl()).getProxy();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入待录入的商品信息(格式为：商品条形码，商品名称，单价，供应商)：");
            String input = scanner.nextLine();
            String[] product = input.split("[,，]");

            if (product.length != 4) {
                System.out.println("输入的数据格式不正确，请重新输入");
                continue;
            }
            if (!checkBarCodeFormat(product[0])) {
                System.out.println("条形码输入格式不正确，请重新输入");
                continue;
            }
            if (productDAO.checkBarCodeExist(product[0])) {
                System.out.println("条形码不能重复，请重新输入");
                continue;
            }

            double price;
            try {
                price = Double.parseDouble(product[2]);
            } catch (NumberFormatException e) {
                System.out.println("输入的价格格式不正确");
                continue;
            }

            if (productService.addProduct(new Product(product[0], product[1], price, product[3]))) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }

            System.out.println("是否继续录入？(y/n)：");
            String opt = scanner.nextLine();
            if ("n".equalsIgnoreCase(opt)) break;
        }

        try {
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

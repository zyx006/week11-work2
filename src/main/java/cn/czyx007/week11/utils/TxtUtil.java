package cn.czyx007.week11.utils;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.bean.SaleDetail;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.dao.impl.SaleDetailDAOImpl;
import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.ProductService;
import cn.czyx007.week11.service.impl.ProductServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class TxtUtil {
    //静态方法工具类，封闭构造函数
    private TxtUtil(){}

    /**
     * 导出数据到txt
     */
    public static void exportDataToTxt(){
        String[] dateDetail = LocalDate.now().toString().split("-");
        String exportTextName = "saleDetail" + dateDetail[0].concat(dateDetail[1]).concat(dateDetail[2]) + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + exportTextName))){
            bw.write("lsh bar_code product_name price count operator sale_time\n");

            List<SaleDetail> saleDetails = new SaleDetailDAOImpl().selectAll();
            for (SaleDetail saleDetail : saleDetails) {
                bw.write(saleDetail.getLsh() + " ");
                bw.write(saleDetail.getBarCode() + " ");
                bw.write(saleDetail.getProductName() + " ");
                bw.write(saleDetail.getPrice() + " ");
                bw.write(saleDetail.getCount() + " ");
                bw.write(saleDetail.getOperator() + " ");
                bw.write(saleDetail.getSaleTime() + "\n");
            }
            System.out.println("成功导出 " + saleDetails.size() + " 条销售数据到文本文件中");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从txt文件导入数据
     */
    public static void loadDataFromTxt() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/product.txt"))) {
            List<Product> products = new ArrayList<>();
            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(" ");
                products.add(new Product(attributes[0], attributes[1], Double.parseDouble(attributes[2]), attributes[3]));
            }

            int cnt = 0;
            ProductDAOImpl productDAO = new ProductDAOImpl();
            ProductService productService = (ProductService) new TransactionProxyFactory(new ProductServiceImpl()).getProxy();
            for (Product product : products) {
                if (!productDAO.checkBarCodeExist(product.getBarCode())) {
                    productService.addProduct(product);
                    cnt++;
                }
            }
            System.out.println("成功从文本文件导入 " + cnt + " 条商品数据");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导入失败！");
        }
    }
}

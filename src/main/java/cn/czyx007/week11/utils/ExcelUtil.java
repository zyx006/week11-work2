package cn.czyx007.week11.utils;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.bean.SaleDetail;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.dao.impl.SaleDetailDAOImpl;
import cn.czyx007.week11.proxy.TransactionProxyFactory;
import cn.czyx007.week11.service.ProductService;
import cn.czyx007.week11.service.impl.ProductServiceImpl;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class ExcelUtil {
    //静态方法工具类，封闭构造函数
    private ExcelUtil(){}

    /**
     * 导出数据到excel
     */
    public static void exportDataToExcel(){
        String[] dateDetail = LocalDate.now().toString().split("-");
        String exportExcelName = "saleDetail" + dateDetail[0].concat(dateDetail[1]).concat(dateDetail[2]) + ".xls";
        WritableWorkbook wwb = null;
        try {
            wwb = Workbook.createWorkbook(new File("src/main/resources/" + exportExcelName));
            WritableSheet ws = wwb.createSheet("销售明细表", 0);

            //添加标题
            ws.addCell(new Label(0,0,"lsh"));
            ws.addCell(new Label(1,0,"bar_code"));
            ws.addCell(new Label(2,0,"product_name"));
            ws.addCell(new Label(3,0,"price"));
            ws.addCell(new Label(4,0,"count"));
            ws.addCell(new Label(5,0,"operator"));
            ws.addCell(new Label(6,0,"sale_time"));

            //按序添加每条商品信息
            List<SaleDetail> saleDetails = new SaleDetailDAOImpl().selectAll();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < saleDetails.size(); i++) {
                SaleDetail saleDetail = saleDetails.get(i);
                ws.addCell(new Label(0,i+1,saleDetail.getLsh()));
                ws.addCell(new Label(1,i+1,saleDetail.getBarCode()));
                ws.addCell(new Label(2,i+1,saleDetail.getProductName()));
                ws.addCell(new Number(3,i+1,saleDetail.getPrice()));
                ws.addCell(new Number(4,i+1,saleDetail.getCount()));
                ws.addCell(new Label(5,i+1,saleDetail.getOperator()));
                ws.addCell(new Label(6,i+1,sdf.format(saleDetail.getSaleTime())));
            }
            wwb.write();
            System.out.println("成功导出 " + saleDetails.size() + " 条销售数据到 excel 文件中");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导出失败！");
        }finally {
            if (wwb != null){
                try {
                    wwb.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从excel导入数据
     */
    public static void loadDataFromExcel() {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File("src/main/resources/product.xls"));
            Sheet sheet = workbook.getSheet(0);

            ArrayList<Product> products = new ArrayList<>();
            int rows = sheet.getRows();
            for (int i = 1; i < rows; i++) {
                String barCode = sheet.getCell(0, i).getContents();
                String productName = sheet.getCell(1, i).getContents();
                double price = Double.parseDouble(sheet.getCell(2, i).getContents());
                String supply = sheet.getCell(3, i).getContents();

                products.add(new Product(barCode, productName, price, supply));
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
            System.out.println("成功从 excel 文件导入 " + cnt + " 条商品数据");
            System.out.print("按任意键返回主菜单");
            System.in.read();
        } catch (Exception e) {
            System.out.println("数据导入失败！");
        }finally {
            if (workbook != null){
                try {
                    workbook.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

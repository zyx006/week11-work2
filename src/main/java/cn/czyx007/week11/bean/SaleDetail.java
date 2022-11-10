package cn.czyx007.week11.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * tsaledetail表对应的实体类
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleDetail {
    //流水号
    private String lsh;
    //商品条形码
    private String barCode;
    //商品名称
    private String productName;
    //商品单价
    private Double price;
    //数量
    private Integer count;
    //收银员
    private String operator;
    //销售时间
    private Date saleTime;

    public void showInfo(){
        System.out.print(lsh + "\t" + productName + "\t");
        System.out.printf("% 8.2f\t% 4d\t% 8.2f\t",price,count,price*count);
        System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(saleTime) + "\t");
    }
}

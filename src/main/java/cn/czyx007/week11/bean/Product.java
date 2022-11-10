package cn.czyx007.week11.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tproduct表对应的实体类
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    //商品条形码
    private String barCode;
    //商品名称
    private String productName;
    //单价
    private Double price;
    //供应商
    private String supply;
}

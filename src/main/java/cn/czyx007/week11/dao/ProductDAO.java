package cn.czyx007.week11.dao;

import cn.czyx007.week11.bean.Product;

import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface ProductDAO {
    /**
     * 插入一条商品数据
     * @param product 待插入的商品Product对象
     * @return 影响行数(1为插入成功，0为插入失败)
     */
    int insert(Product product);

    /**
     * 检验条形码是否已存在
     * @param barCode 待检验重复的条形码
     * @return true：已存在  false：不存在
     */
    boolean checkBarCodeExist(String barCode);

    /**
     * 根据条形码获取商品信息
     * @param barCode 条形码
     * @return 条形码为barCode的商品Product对象
     */
    Product selectByBarCode(String barCode);

    /**
     * 根据商品名称进行模糊查询，获取所有匹配的商品信息
     * @param productName 商品名称
     * @return 所有匹配的商品信息List集合
     */
    List<Product> selectByProductName(String productName);
}

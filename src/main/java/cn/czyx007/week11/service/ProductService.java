package cn.czyx007.week11.service;

import cn.czyx007.week11.bean.Product;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface ProductService {
    /**
     * 添加一条商品信息记录(使用动态代理完成事务控制)
     * @param product 待添加的商品信息
     * @return true：添加成功  false：添加失败
     */
    boolean addProduct(Product product);
}

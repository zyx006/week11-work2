package cn.czyx007.week11.service.impl;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.dao.ProductDAO;
import cn.czyx007.week11.dao.impl.ProductDAOImpl;
import cn.czyx007.week11.service.ProductService;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class ProductServiceImpl implements ProductService {
    private static ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public boolean addProduct(Product product) {
        if (!productDAO.checkBarCodeExist(product.getBarCode())) {
            productDAO.insert(product);
            return true;
        }
        return false;
    }
}

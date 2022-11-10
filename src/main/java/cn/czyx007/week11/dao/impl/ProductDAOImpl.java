package cn.czyx007.week11.dao.impl;

import cn.czyx007.week11.bean.Product;
import cn.czyx007.week11.dao.ProductDAO;
import cn.czyx007.week11.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class ProductDAOImpl implements ProductDAO {
    @Override
    public int insert(Product product) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "insert into tproduct values(?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, product.getBarCode());
            ps.setObject(2, product.getProductName());
            ps.setObject(3, product.getPrice());
            ps.setObject(4, product.getSupply());
            count = ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    @Override
    public boolean checkBarCodeExist(String barCode) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select count(*) from tproduct where bar_code = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, barCode);
            rs = ps.executeQuery();
            if(rs.next()){
                count = rs.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return count==1;
    }

    @Override
    public Product selectByBarCode(String barCode) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Product product = null;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from tproduct where bar_code = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, barCode);
            rs = ps.executeQuery();
            if(rs.next()){
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                String supply = rs.getString("supply");
                product = new Product(barCode, productName, price, supply);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return product;
    }

    @Override
    public List<Product> selectByProductName(String productName) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from tproduct where product_name like ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, "%" + productName + "%");
            rs = ps.executeQuery();
            while (rs.next()){
                String barCode = rs.getString("bar_code");
                productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                String supply = rs.getString("supply");
                products.add(new Product(barCode, productName, price, supply));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return products;
    }
}

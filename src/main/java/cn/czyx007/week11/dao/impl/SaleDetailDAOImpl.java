package cn.czyx007.week11.dao.impl;

import cn.czyx007.week11.bean.SaleDetail;
import cn.czyx007.week11.dao.SaleDetailDAO;
import cn.czyx007.week11.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class SaleDetailDAOImpl implements SaleDetailDAO {
    @Override
    public int insert(SaleDetail saleDetail) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "insert into tsaledetail values(?,?,?,?,?,?,?)";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, saleDetail.getLsh());
            ps.setObject(2, saleDetail.getBarCode());
            ps.setObject(3, saleDetail.getProductName());
            ps.setObject(4, saleDetail.getPrice());
            ps.setObject(5, saleDetail.getCount());
            ps.setObject(6, saleDetail.getOperator());
            ps.setObject(7, saleDetail.getSaleTime());
            count = ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    @Override
    public String getLastNo(String dateStr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String lastLsh = null;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select max(right(lsh,4)) as lastNo from tsaledetail where left(lsh,8) = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, dateStr);
            rs = ps.executeQuery();
            if(rs.next()){
                lastLsh = rs.getString("lastNo");
                lastLsh = String.format("%04d", Integer.parseInt(lastLsh)+1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return lastLsh == null ? "0001" : lastLsh;
    }

    @Override
    public int getProductSumBySaleTime(String dateStr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int productSum = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select sum(count) from tsaledetail where STR_TO_DATE(sale_time,'%Y-%m-%d') = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, dateStr);
            rs = ps.executeQuery();
            if (rs.next()){
                productSum = rs.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return productSum;
    }

    @Override
    public double getSaleMoneyBySaleTime(String dateStr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double saleMoney = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select sum(count*price) from tsaledetail where STR_TO_DATE(sale_time,'%Y-%m-%d') = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, dateStr);
            rs = ps.executeQuery();
            if (rs.next()){
                saleMoney = rs.getDouble(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return saleMoney;
    }

    @Override
    public List<SaleDetail> selectBySaleTime(String dateStr) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SaleDetail> saleDetails = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from tsaledetail where STR_TO_DATE(sale_time,'%Y-%m-%d') = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, dateStr);
            rs = ps.executeQuery();
            while (rs.next()){
                String lsh = rs.getString("lsh");
                String barCode = rs.getString("bar_code");
                String productName = rs.getString("product_name");
                Double price = rs.getDouble("price");
                Integer count = rs.getInt("count");
                String operator = rs.getString("operator");
                Date saleTime = rs.getTimestamp("sale_time");
                saleDetails.add(new SaleDetail(lsh, barCode, productName, price, count, operator, saleTime));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return saleDetails;
    }

    @Override
    public List<SaleDetail> selectAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<SaleDetail> saleDetails = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from tsaledetail";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                String lsh = rs.getString("lsh");
                String barCode = rs.getString("bar_code");
                String productName = rs.getString("product_name");
                Double price = rs.getDouble("price");
                Integer count = rs.getInt("count");
                String operator = rs.getString("operator");
                Date saleTime = rs.getTimestamp("sale_time");
                saleDetails.add(new SaleDetail(lsh, barCode, productName, price, count, operator, saleTime));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return saleDetails;
    }
}

package cn.czyx007.week11.dao;

import cn.czyx007.week11.bean.SaleDetail;

import java.util.List;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface SaleDetailDAO {
    /**
     * 插入一条销售明细信息
     * @param saleDetail 一条销售明细信息
     * @return 影响行数(1为插入成功，0为插入失败)
     */
    int insert(SaleDetail saleDetail);

    /**
     * 获取某一天最后一笔销售明细的流水号
     * @param dateStr 指定日期(yyyyMMdd)
     * @return 指定日期最后一笔销售明细的流水号（若该天不存在销售明细则返回"0001"）
     */
    String getLastNo(String dateStr);

    /**
     * 获取某一天商品销售总件数
     * @param dateStr 指定日期(yyyy-MM-dd)
     * @return 指定日期的商品销售总件数
     */
    int getProductSumBySaleTime(String dateStr);

    /**
     * 获取某一天商品销售总金额
     * @param dateStr 指定日期(yyyy-MM-dd)
     * @return 指定日期的商品销售总金额
     */
    double getSaleMoneyBySaleTime(String dateStr);

    /**
     * 根据销售日期获取所有销售明细记录
     * @param dateStr 指定日期(yyyy-MM-dd)
     * @return 指定日期的所有销售明细记录
     */
    List<SaleDetail> selectBySaleTime(String dateStr);

    /**
     * 获取所有销售明细记录
     * @return 所有销售明细记录
     */
    List<SaleDetail> selectAll();
}

package cn.czyx007.week11.service;

import cn.czyx007.week11.bean.SaleDetail;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public interface SaleDetailService {
    /**
     * 添加一条销售明细记录(使用动态代理完成事务控制)
     * @param saleDetail 待添加的销售明细记录
     * @return true：添加成功  false：添加失败
     */
    boolean addSaleDetail(SaleDetail saleDetail);
}

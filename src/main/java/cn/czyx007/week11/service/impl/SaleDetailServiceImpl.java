package cn.czyx007.week11.service.impl;

import cn.czyx007.week11.bean.SaleDetail;
import cn.czyx007.week11.dao.SaleDetailDAO;
import cn.czyx007.week11.dao.impl.SaleDetailDAOImpl;
import cn.czyx007.week11.service.SaleDetailService;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class SaleDetailServiceImpl implements SaleDetailService {
    private static SaleDetailDAO saleDetailDAO = new SaleDetailDAOImpl();

    @Override
    public boolean addSaleDetail(SaleDetail saleDetail) {
        return saleDetailDAO.insert(saleDetail) == 1;
    }
}

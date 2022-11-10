package cn.czyx007.week11.controller;

import cn.czyx007.week11.utils.ExcelUtil;
import cn.czyx007.week11.utils.MenuUtil;
import cn.czyx007.week11.utils.TxtUtil;

/**
 * 数据导出菜单
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class DataExportMenu {
    public static void dataExportMenu(){
        while (true) {
            int opt;
            System.out.print("===超市销售信息导出====\n" +
                    "1、导出到 excel 文件\n" +
                    "2、导出到文本文件\n" +
                    "3、返回主菜单\n" +
                    "请选择（1-3）：");
            do {
                opt = MenuUtil.checkInput(1, 3);
            } while (opt == -1);

            switch (opt) {
                case 1://导出到excel文件
                    ExcelUtil.exportDataToExcel();
                    break;
                case 2://导出到txt文件
                    TxtUtil.exportDataToTxt();
                    break;
                case 3://返回主菜单
                    return;
            }
        }
    }
}

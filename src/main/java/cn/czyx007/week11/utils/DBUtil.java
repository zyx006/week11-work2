package cn.czyx007.week11.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库连接工具类
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class DBUtil {
    //静态方法工具类，封闭构造函数
    private DBUtil(){}

    //每个线程向其中绑定自己的数据(数据库连接对象)
    private static ThreadLocal<Connection> local = new ThreadLocal<>();

    private static ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String username = bundle.getString("username");
    private static String password = bundle.getString("password");

    //类加载时注册驱动
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取某线程对应的连接对象
     * @return 数据库连接对象connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = local.get();
        if(connection == null){
            //某线程不存在对应的数据库连接对象，创建并绑定到ThreadLocal中
            connection = DriverManager.getConnection(url, username, password);
            local.set(connection);
        }
        return connection;
    }

    /**
     * 关闭数据库资源
     * @param connection 待关闭的Connection数据库连接对象(关闭后会从ThreadLocal中解绑)
     * @param ps 待关闭的PreparedStatement预编译语句对象
     * @param rs 待关闭的ResultSet结果集对象
     */
    public static void close(Connection connection, PreparedStatement ps, ResultSet rs){
        if (connection != null) {
            try {
                connection.close();
                local.remove();//从ThreadLocal中移除某线程对应的连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

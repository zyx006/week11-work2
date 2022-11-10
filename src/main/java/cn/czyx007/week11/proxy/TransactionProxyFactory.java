package cn.czyx007.week11.proxy;

import cn.czyx007.week11.utils.DBUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 使用动态代理完成事务控制
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class TransactionProxyFactory {
    private Object target;

    public TransactionProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxy(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();
        InvocationHandler handler = new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Connection connection = null;
                Object result = null;
                try {
                    connection = DBUtil.getConnection();
                    //关闭自动提交，开启事务
                    connection.setAutoCommit(false);

                    result = method.invoke(target, args);

                    //事务完成，提交事务
                    connection.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭数据库连接，若事务失败会自动回滚
                    DBUtil.close(connection, null, null);
                }
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, handler);
    }
}

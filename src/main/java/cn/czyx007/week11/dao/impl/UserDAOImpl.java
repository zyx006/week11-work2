package cn.czyx007.week11.dao.impl;

import cn.czyx007.week11.bean.User;
import cn.czyx007.week11.dao.UserDAO;
import cn.czyx007.week11.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class UserDAOImpl implements UserDAO {
    @Override
    public int updatePasswordByUsername(String username, String password) {
        PreparedStatement ps = null;
        int count = 0;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "update tuser set password = sha1(?) where user_name = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, password);
            ps.setObject(2, username);
            count = ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, null);
        }
        return count;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean passwordEqual = false;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select password = sha1(?) from tuser where user_name = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, password);
            ps.setObject(2, username);
            rs = ps.executeQuery();
            if(rs.next()){
                passwordEqual = rs.getBoolean(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return passwordEqual;
    }

    @Override
    public User selectByUsername(String username) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            Connection connection = DBUtil.getConnection();
            String sql = "select * from tuser where user_name = ?";
            ps = connection.prepareStatement(sql);
            ps.setObject(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString("password");
                String chrName = rs.getString("chr_name");
                String role = rs.getString("role");
                user = new User(username, password, chrName, role);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return user;
    }
}

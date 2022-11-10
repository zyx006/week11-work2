package cn.czyx007.week11.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tuser表对应的实体类
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    //用户登录名
    private String username;
    //密码 sha1加密存储
    private String password;
    //中文名
    private String chrName;
    //用户角色 管理员/收银员
    private String role;
}

package cn.czyx007.week11.utils;

/**
 * @author : 张宇轩
 * @createTime : 2022/11/10
 */
public class PasswordUtil {
    //静态方法工具类，封闭构造函数
    private PasswordUtil(){}

    /**
     * 密码不符合复杂性要求
     */
    public static final String PASSWORD_SECURITY_ERROR = "您的密码不符合复杂性要求（密码长度不少于 6 个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字），请重新输入：";

    /**
     * 检验密码复杂性（长度不少于 6 个字符，至少有一个小写字母，至少有一个大写字母，至少一个数字）
     * @param password 输入的密码
     * @return true：符合复杂性要求  false：不符合复杂性要求
     */
    public static boolean checkSecurity(String password) {
        if (password.length() >= 6) {
            int numCnt = 0, lowCnt = 0, upCnt = 0;
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) >= '0' && password.charAt(i) <= '9')
                    numCnt++;
                else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                    lowCnt++;
                else if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                    upCnt++;
            }
            return numCnt >= 1 && lowCnt >= 1 && upCnt >= 1;
        }
        return false;
    }
}

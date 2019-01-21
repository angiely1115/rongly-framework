import com.xs.rongly.framework.stater.security.shiro.AuthcEnum;

/**
 * @Author: lvrongzhuan
 * @Description:
 * @Date: 2019/1/21 12:03
 * @Version: 1.0
 * modified by:
 */
public class Test1 {
    public static void main(String[] args) {
        System.out.println(AuthcEnum.anon);
        System.out.println(AuthcEnum.anon.name());
        AuthcEnum[] authcEnums = AuthcEnum.values();
        for (AuthcEnum authcEnum:authcEnums){
            System.out.println(authcEnum);
        }
    }
}

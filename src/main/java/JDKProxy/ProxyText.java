package JDKProxy;

import org.junit.Test;

/**
 * Created by wxb on 2017/9/28.
 */
public class ProxyText {
    @Test
    public void testProxy() {
        //实例化目标对象
        UserService userService = new UserServiceImpl();
        //实例化invocationHandler
        MyInvocationHandler invocationHandler = new MyInvocationHandler(userService);
        //根据目标对象生成代理对象
        UserService proxy = (UserService) invocationHandler.getProxy();
        //调用代理对象的方法
        proxy.add();

    }
}

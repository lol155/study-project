package JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wxb on 2017/9/27.
 */
public class MyInvocationHandler implements InvocationHandler {
    // 目标对象
    private Object target;

    /**
     * 构造方法
     *
     * @param target 目标对象
     */
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 执行目标对象的方法
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }

    /**
     * 获取目标对象的代理对象
     * @return 代理对象
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }
}

package typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

interface Interface {
	void doSomething();
	void doSomethingElse(String arg);
}

class RealObject implements Interface {

	@Override
	public void doSomething() {
		System.out.println("Do something");
	}

	@Override
	public void doSomethingElse(String arg) {
		System.out.println("SomethingElse " + arg);
	}
	
}


class DynamicProxyHandler implements InvocationHandler {

	private Object proxied;
	
	public DynamicProxyHandler(Object proxied) {
		this.proxied = proxied;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("**** proxy: " + proxy.getClass() + ", method: " + method + ", args: " + Arrays.toString(args));
		return method.invoke(proxied, args);
	}

}

public class SimpleDynamicProxy {

	public static void consumer(Interface iface) {
		iface.doSomething();
		iface.doSomethingElse("bonono");
	}
	
	public static void main(String[] args) {
		RealObject real = new RealObject();
		consumer(real);
		
		// Insert a proxy and call again
		Interface proxy = (Interface) Proxy.newProxyInstance(
				Interface.class.getClassLoader(),
				new Class[] { Interface.class }, new DynamicProxyHandler(real));
		consumer(proxy);
	}

}

package typeinfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

import util.Null;

class NullRobotProxyHandler implements InvocationHandler {

	private String nullName;
	private Robot proxied = new NRobot();

	public NullRobotProxyHandler(Class<? extends Robot> type) {
		nullName = type.getSimpleName() + " NullRobot";
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		return method.invoke(proxied, args);
	}

	private class NRobot implements Robot, Null {

		@Override
		public String name() {
			return nullName;
		}

		@Override
		public String model() {
			return nullName;
		}

		@Override
		public List<Operation> operations() {
			return Collections.emptyList();
		}

	}
}

/**
 * Use dynamic proxy to create a Null Object
 * 
 * @author pirent
 *
 */
public class NullRobot {
	public static Robot newNullRobot(Class<? extends Robot> type) {
		return (Robot) Proxy.newProxyInstance(NullRobot.class.getClassLoader(), new Class[] {
				Null.class, Robot.class }, new NullRobotProxyHandler(type));
	}
	
	public static void main(String[] args) {
		Robot[] robots = new Robot[] {
				new SnowRemovalRobot("SnowBee"),
				newNullRobot(SnowRemovalRobot.class)
		};
		
		for (Robot robot : robots) {
			Robot.Test.test(robot);
		}
	}
}

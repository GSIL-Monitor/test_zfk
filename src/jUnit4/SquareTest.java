package jUnit4;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/*
 * 代码分析如下：
为这种测试专门生成一个新的类，而不能与其他测试共用同一个类，此例中我们定义了一个SquareTest类。
为这个类指定一个Runner，而不能使用默认的Runner，@RunWith(Parameterized.class)这条语句就是为这个类指定了一个ParameterizedRunner
定义一个待测试的类，并且定义两个变量，一个用于存放参数，一个用于存放期待的结果。
定义测试数据的集合，也就是上述的data()方法，该方法可以任意命名，但是必须使用@Parameters标注进行修饰。
定义构造函数，其功能就是对先前定义的两个参数进行初始化
 */
@RunWith(Parameterized.class)
public class SquareTest {
	private static Calculator calculator = new Calculator();
	private int param;
	private int result;

	@Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] { { 2, 4 }, { 0, 0 }, { -3, 9 }, });
	}

	// 构造函数，对变量进行初始化
	public SquareTest(int param, int result) {
		this.param = param;
		this.result = result;
	}

	@Test
	public void square() {
		calculator.square(param);
		assertEquals(result, calculator.getResult());
	}
}

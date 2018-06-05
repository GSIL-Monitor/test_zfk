package jUnit4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CalculatorTest {

	private static Calculator calculator = new Calculator();

	@Before
	public void setUp() throws Exception {
		calculator.clear();
	}

	@Test
	public void testAdd() {
		calculator.add(3);
		calculator.add(4);
		assertEquals(7, calculator.getResult());
	}

	@Test
	public void testSubstract() {
		calculator.add(8);
		calculator.substract(3);
		assertEquals(5, calculator.getResult());
	}

	@Ignore("Multiply() Not yet implemented")
	@Test
	public void testMultiply() {
		fail("Not yet implemented");
	}

	@Test
	public void testDivide() {
		calculator.add(8);
		calculator.divide(2);
		assertEquals(4, calculator.getResult());

	}

	/*
	 * 限时测试 对于那些逻辑很复杂，循环嵌套比较深的程序，很有可能出现死循环，因此一定要采取一些预防措施。
	 * 限时测试是一个很好的解决方案。我们给这些测试函数设定一个执行时间，超过了这个时间，他们就会被系统强行终止，
	 * 并且系统还会向你汇报该函数结束的原因是因为超时，这样你就可以发现这些Bug了。
	 */
	@Test(timeout = 1000)
	public void squareRoot() {
		calculator.squareRoot(4);
		assertEquals(2, calculator.getResult());
	}

	/*
	 * 测试异常 JAVA中的异常处理也是一个重点，因此你经常会编写一些需要抛出异常的函数。
	 * 那么，如果你觉得一个函数应该抛出异常，但是它没抛出，这算不算Bug呢？ 这当然是Bug，并JUnit也考虑到了这一点，来帮助我们找到这种Bug。
	 * 例如，我们写的计算器类有除法功能，如果除数是一个0，那么必然要抛出“ 除0异常”。因此，我们很有必要对这些进行测试。代码如下：
	 */
	@Test(expected = ArithmeticException.class)
	public void divideByZero() {
		calculator.divide(0);
	}

}

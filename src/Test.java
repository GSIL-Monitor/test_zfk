import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		//testAsList();
		
		//testSB();
		
		testInteger();
	}

	private static void testInteger() {
		Integer in = null;
		
		int i = in;
		
		System.out.println(i);
		
	}

	private static void testSB() {
		StringBuffer sb = new StringBuffer();
		System.out.println(sb.toString().length());
		
	}

	private static void testAsList() {
		List<String> list = new ArrayList<String>(Arrays.asList(new String[]{"aa", "", null}));
		//List<String> list = Arrays.asList(new String[]{"aa", "", null});
		System.out.println(list.size());
		System.out.println(list);
		List<String> ll = Arrays.asList("", null);
		//List<String> ll = new ArrayList<String>(Arrays.asList("", null));
		list.removeAll(ll);
		System.out.println(list.size());
		System.out.println(list);
		
	}

}

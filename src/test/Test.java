package test;

public class Test {


	public static void main(String[] args) {
		//nullTest();
		int i = -1;
		try {
			i = exceptionReturnTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(i);

	}

	private static int exceptionReturnTest() throws Exception {
		int i = 1;
		try {
			i = 1/0;
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception("zzzzzzzzz");
			//return 22;
		}finally {
			i = 11;
			System.out.println("111111111111");
		}
		
		i = 22;
		System.out.println("2222222222222");
		return i;
		
	}

	private static void nullTest() {
		String s = new String(new byte[0]);
		if (s == null) {
			System.out.println("null");
		} else {
			System.out.println(s);
		}
	}

}

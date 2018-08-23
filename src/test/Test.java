package test;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String s = new String(new byte[0]);
		if (s == null) {
			System.out.println("null");
		} else {
			System.out.println(s);
		}

	}

}

package regex;

public class Test {

	public static void main(String[] args) {
		System.err.println(HideDataUtil.hideCardNo("6228480791928882111"));
		System.err.println(HideDataUtil.hidePhoneNo("13155119165"));
		System.err.println(HideDataUtil.hideName("朱付款昆"));
		System.err.println(HideDataUtil.hideText("科8号",2,4));
	}


}

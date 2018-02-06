package gkbCenter;

import gkbCenter.service.TulingService;

public class Test {
	static TulingService tls = new TulingService();
	
	public static void main(String[] args) {
		// 图灵
		System.out.println(tls.getAnswer("今天天气怎么样", "1"));
		System.out.println(tls.getAnswer("来凤", "1"));

	}
}

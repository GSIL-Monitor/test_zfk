package xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Test{
	public static void main(String[] args) {
		String xmlString = "<x x1='1'><xx xx1='2'/><xx xx1='2'/></x>";
		List<Map<String,String>> mapList = XmlUtil.StringToMap2(xmlString);
		List<String> list = new ArrayList<String>();
		list.add("zaaz");
		list.add("azza");
		Collections.sort(list);
		System.out.println(list);
		Collections.sort(list,new Comparator<String>() {
			public int compare(String o1, String o2) {
				char c1 = o1.charAt(0);
				char c2 = o2.charAt(0);
				//前面的大吗？大=-1不换 ：不大，那么等于吗？等于0 ：不等于，小=1换 
				return c1>c2 ? -1: c1==c2 ? 0 : 1;
			}
		});
		System.out.println(list);
		System.out.println(mapList);
	}

}


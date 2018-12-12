package office;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import office.excel.ReadExcelUtils;

public class TestExcel {
	public static void main(String[] args) {
		try {
			String filepath = "C:\\Users\\ZHUFUKUN\\Desktop\\test1.xlsx";
			ReadExcelUtils excelReader = new ReadExcelUtils();

			// 对读取Excel表格内容测试
			List<Map<String, Object>> list = excelReader.readExcelContent(filepath);
			System.out.println("获得Excel表格的内容大小:"+list.size());
			System.out.println("获得Excel表格的内容:");
//			for (int i = 0; i <= list.size(); i++) {
//				System.out.println(list.get(i));
//			}
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

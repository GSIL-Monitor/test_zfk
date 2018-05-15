package hm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import office.excel.ExportExcelUtil;

public class ReadChatTxt {
	public static void main(String[] args) throws Exception {
		String logPath = "C:\\Users\\zhufukun\\Desktop\\机器人超级话美女版.txt";

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logPath), "utf-8"));

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		String line = "";
		int num = 0;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
			if (line.length() == 0) {
				map = new HashMap<String, String>();
				num = 0;
			} else {
				if (num == 0) {
					map.put("question", line);
					num++;
				} else if (num == 1) {
					map.put("answer", line);
					num++;
					list.add(map);
				}

			}
		}

		// 导出
		ExportExcelUtil.exportOneSheet("sheet", new String[] { "问题", "回答" }, new String[] { "question", "answer" },
				list, "C:\\Users\\zhufukun\\Desktop\\机器人超级话美女版.xlsx");
	}
}

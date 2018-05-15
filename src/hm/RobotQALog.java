package hm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import office.excel.ExportExcelUtil;

public class RobotQALog {
	public static void main(String[] args) throws Exception {
		
		String logPath = "C:\\Users\\zhufukun\\Desktop\\20180419.log";
		
		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(logPath),"gbk"));
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		String line ="";
		while((line=bf.readLine()) != null){
			int index = line.indexOf("接收到事件, 参数为: ");
			if(index > 0){
				line = line.substring(index+12, line.length()-2);
				
				if(line.length() == 0){
					continue;
				}
				System.out.println(line);
				
				Map<String,String> map = new HashMap<String,String>();
				JSONObject json = new JSONObject();
				try {
					json = new JSONObject(line);
				} catch (Exception e) {
					continue;
				}
				
				
				if(!json.has("question")){
					continue;
				}
				
				String question = json.getString("question");

				if(question == null || question.length()==0){
					continue;
				}
				
				map.put("question", question);
				map.put("tts", "");
				map.put("screenShow", "");
				
				JSONObject richText = json.getJSONObject("richText");
				if(richText.has("richContent")){
					JSONObject richContent = richText.getJSONObject("richContent");
					if(richContent.has("tts")){
						String tts = richContent.getString("tts");
						String screenShow = richContent.getString("screenShow");
						
						map.put("tts", tts);
						map.put("screenShow", screenShow);
					}
				}
				list.add(map);
			}
			
		}
		
//		System.out.println(list);
		
		// 导出
		ExportExcelUtil.exportOneSheet("sheet", new String[] { "问题", "回答"}, new String[] { "question", "tts"}, list,
						"C:\\Users\\zhufukun\\Desktop\\qa20180419.xlsx");
		
	}
	
	
	

}

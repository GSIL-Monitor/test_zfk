package hm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 更新现有的关系，在way_gkb_flow表中第一列加入main_id，他将作为后续新的flow_id，
 * 根据main_id生成新的group_id，替换原来的group_id
 * 
 * 中间表也是根据main_id生成新的flow_id与parent_flow_id
 */
public class HmFlow {
	public static void main(String[] args) {
		String flowfile = "D:\\way_gkb_flow.xlsx";
		String flowmiddlefile = "D:\\way_gkb_flow_middle.xlsx";

		Workbook wb = null;
		Sheet sheet;
		Row row;

		String ext = flowfile.substring(flowfile.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(flowfile);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = wb.getSheetAt(0);

		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 得到中列数
		int colNum = row.getPhysicalNumberOfCells();

		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellStringValue(row.getCell(i));
		}
		System.out.println("标题列===" + Arrays.toString(title));

		// 数据
		List<String> mainIdList = new ArrayList<String>();
		List<String> flowIdList = new ArrayList<String>();
		List<String> groupIdList = new ArrayList<String>();
		List<Map<String, String>> newGroupIdDatas = new ArrayList<Map<String, String>>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			mainIdList.add(getCellStringValue(row.getCell(0)));//自己手动加，讲作为新的flowId
			flowIdList.add(getCellStringValue(row.getCell(1)));//flowId
			groupIdList.add(getCellStringValue(row.getCell(12)));//groupId

		}

		//根据main_id生成新的group_id，替换原来的group_id
		for (int i = 0; i < groupIdList.size(); i++) {
			boolean isfind = false;
			for (int j = 0; j < flowIdList.size(); j++) {
				if (groupIdList.get(i).equals(flowIdList.get(j))) {
					Map<String, String> rowValues = new HashMap<String, String>();
					rowValues.put("groupId", mainIdList.get(j));
					newGroupIdDatas.add(rowValues);
					isfind = true;
					break;
				}
			}
			if(!isfind){
				System.out.println("groupId没有找到对应flow_id，group_id==="+groupIdList.get(i));
			}
		}

		// 导出，
		ExportExcelUtil.exportOneSheet("sheet", new String[] { "groupId" }, new String[] { "groupId" }, newGroupIdDatas,
				"D:\\newgroupid.xls");

		//分析中间表
		flowmiddle(flowmiddlefile, mainIdList, flowIdList);

	}

	private static void flowmiddle(String flowmiddlefile, List<String> mainIdList, List<String> flowIdList) {
		Workbook wb = null;
		Sheet sheet;
		Row row;

		//中间表数据文件
		String ext = flowmiddlefile.substring(flowmiddlefile.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(flowmiddlefile);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = wb.getSheetAt(0);

		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		// 得到中列数
		int colNum = row.getPhysicalNumberOfCells();

		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = getCellStringValue(row.getCell(i));
		}
		System.out.println("标题列===" + Arrays.toString(title));

		// 数据
		List<String> idList = new ArrayList<String>();
		List<String> pidList = new ArrayList<String>();
		List<Map<String, String>> newDates = new ArrayList<Map<String, String>>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			idList.add(getCellStringValue(row.getCell(0)));
			pidList.add(getCellStringValue(row.getCell(1)));

		}

		
		for (int i = 0; i < idList.size(); i++) {
			Map<String, String> rowValues = new HashMap<String, String>();
			boolean f1 = false;
			boolean f2 = false;
			for (int j = 0; j < flowIdList.size(); j++) {
				//这里的idList与主表的flowId比较
				if (idList.get(i).equals(flowIdList.get(j))) {
					rowValues.put("id", mainIdList.get(j));
					f1 = true;
				}
				//这里的pidList与主表的flowId比较
				if (pidList.get(i).equals(flowIdList.get(j))) {
					rowValues.put("pid", mainIdList.get(j));
					f2 = true;
				}
				if (f1 && f2) {
					newDates.add(rowValues);
					break;
				}
			}

		}

		// 导出
		ExportExcelUtil.exportOneSheet("sheet", new String[] { "id", "pid" }, new String[] { "id", "pid" }, newDates,
				"D:\\newmiddleid.xls");

	}

	/**
	 * 得到单元格内容
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellStringValue(Cell cell) {
		try {
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return null;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return cell.getBooleanCellValue() + "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
				return cell.getErrorCellValue() + "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return cell.getNumericCellValue() + "";
			} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {// 公式
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return deleleFrontBlannk(cell.getStringCellValue() + "");
			} else {
				return deleleFrontBlannk(cell.getStringCellValue() + "");
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 去掉前面的空白
	 * 
	 * @param s
	 * @return
	 */
	private static String deleleFrontBlannk(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				s = s.substring(i, s.length());
				break;
			}
		}
		return s;
	}
}

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

import office.excel.ExportExcelUtil;

public class HmFlow {
	public static void main(String[] args) {
		String flowfile = "C:\\Users\\zhufukun\\Desktop\\way_gkb_flow.xls";
		String flowmiddlefile = "C:\\Users\\zhufukun\\Desktop\\way_gkb_flow_middle.xls";

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
			mainIdList.add(getCellStringValue(row.getCell(0)));
			flowIdList.add(getCellStringValue(row.getCell(1)));
			groupIdList.add(getCellStringValue(row.getCell(12)));

		}

		for (int i = 0; i < groupIdList.size(); i++) {
			for (int j = 0; j < flowIdList.size(); j++) {
				if (groupIdList.get(i).equals(flowIdList.get(j))) {
					Map<String, String> rowValues = new HashMap<String, String>();
					rowValues.put("groupId", mainIdList.get(j));
					newGroupIdDatas.add(rowValues);
					break;
				}
			}
		}

		// 导出
		ExportExcelUtil.exportOneSheet("sheet", new String[] { "groupId" }, new String[] { "groupId" }, newGroupIdDatas,
				"C:\\Users\\zhufukun\\Desktop\\newgroupid.xls");

		flowmiddle(flowmiddlefile, mainIdList, flowIdList);

	}

	private static void flowmiddle(String flowmiddlefile, List<String> mainIdList, List<String> flowIdList) {
		Workbook wb = null;
		Sheet sheet;
		Row row;

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
				if (idList.get(i).equals(flowIdList.get(j))) {
					rowValues.put("id", mainIdList.get(j));
					f1 = true;
				}
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
				"C:\\Users\\zhufukun\\Desktop\\newmiddleid.xls");

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

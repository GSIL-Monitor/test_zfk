package office.excel;

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
 * 读取Excel
 * 
 */
public class ReadExcelUtils {
	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<String, Object>> readExcelContent(String filepath) throws Exception {
		Workbook wb = null;
		Sheet sheet;
		Row row;
		if (filepath == null) {
			throw new Exception("filepath参数为空！");
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
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

		if (wb == null) {
			throw new Exception("Workbook对象为空！");
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
		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			Map<String, Object> rowValues = new HashMap<String, Object>();
			while (j < colNum) {
				Object obj = getCellStringValue(row.getCell(j));
				rowValues.put(j+"", obj);
				j++;
			}
			content.add(rowValues);
		}
		return content;
	}

	/**
	 * 得到单元格内容
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellStringValue(Cell cell) {
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
	private String deleleFrontBlannk(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				s = s.substring(i, s.length());
				break;
			}
		}
		return s;
	}

	/**
	 * 去掉后面的空白
	 * 
	 * @param s
	 * @return
	 */
	private String deleleBehindBlannk(String s) {
		for (int i = s.length() - 1; i > 0; i--) {
			if (s.charAt(i) != ' ') {
				s = s.substring(0, i + 1);
				break;
			}
		}
		return s;
	}

	/**
	 * 去掉文本的空白
	 * 
	 * @param s
	 * @return
	 */
	private String deleleBlannk(String s) {
		s = s.replaceAll(" |　", "");
		return s;
	}

	public static void main(String[] args) {
		try {
			String filepath = "C:\\Users\\ZHUFUKUN\\Desktop\\test1.xls";
			ReadExcelUtils excelReader = new ReadExcelUtils();

			// 对读取Excel表格内容测试
			List<Map<String, Object>> list = excelReader.readExcelContent(filepath);
			System.out.println("获得Excel表格的内容:");
			for (int i = 0; i <= list.size(); i++) {
				System.out.println(list.get(i));
			}
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

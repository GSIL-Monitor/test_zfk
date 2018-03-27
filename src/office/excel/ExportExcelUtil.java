package office.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelUtil {
	public static void main(String[] args) throws Exception {
		String sheetName = "朱富昆book";
		String[] colNames = { "ID", "书名", "作者" };
		String[] propertyNames = { "id", "name", "author" };

		List<Book> datas = new ArrayList<Book>();
		for (int i = 1; i <= 100; i++) {
			Book book = new Book("1_" + i, "book_" + i, "zhufukun");
			datas.add(book);
		}

		exportOneSheet(sheetName, colNames, propertyNames, datas, "D:\\book.xlsx");
	}

	/**
	 * 
	 * 生成excelWorkBook
	 * 
	 * @param sheetName
	 *            sheet页名称
	 * @param colNames
	 *            列名称
	 * @param datas
	 *            数据对象集合
	 * @param propertyNames
	 *            对象属性名
	 * @param path
	 *            导出路径
	 * @return
	 * @throws Exception
	 */
	public static String exportOneSheet(String sheetName, String[] colNames, String[] propertyNames, List<?> datas,
			String path) {
		Workbook wb = new XSSFWorkbook();
		// Workbook wb = new SXSSFWorkbook(200);
		FileOutputStream out = null;
		try {
			Sheet sheet = wb.createSheet(sheetName);
			sheet.setDefaultColumnWidth(20);

			// 列头单元格样式
			CellStyle cs = wb.createCellStyle();
			cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cs.setAlignment(HorizontalAlignment.CENTER);
			Font font = wb.createFont();
			font.setBold(true);
			cs.setFont(font);

			// 列头
			Row row = sheet.createRow(0);
			for (int i = 0; i < colNames.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellValue(colNames[i]);
				cell.setCellStyle(cs);
				// 列宽
				sheet.setColumnWidth(i, 20 * 400);
			}

			// 数据
			for (int i = 0; i < datas.size(); i++) {
				row = sheet.createRow(i + 1);
				Object obj = datas.get(i);
				Class<? extends Object> clazz = obj.getClass();
				String clazzName = clazz.getName();
				//Map
				if (clazzName.contains("java.util.") && clazzName.contains("Map")) {
					for (int j = 0; j < propertyNames.length; j++) {
						Map map = (Map) obj;
						String value = map.get(propertyNames[j]).toString();
						Cell cell = row.createCell(j);
						cell.setCellValue(value);
					}
				} else {
					//对象
					for (int j = 0; j < propertyNames.length; j++) {
						Field field = clazz.getDeclaredField(propertyNames[j]);
						field.setAccessible(true);
						String value = field.get(obj).toString();
						Cell cell = row.createCell(j);
						cell.setCellValue(value);
					}
				}
			}
			out = new FileOutputStream(path);
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
			return "no";
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "ok";
	}

}
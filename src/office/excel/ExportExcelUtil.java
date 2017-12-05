package office.excel;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
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
			Book book = new Book("1_"+i, "book_" + i, "zhufukun");
			datas.add(book);
		}

		exportOneSheet(sheetName, colNames, propertyNames, datas);
	}

	/**
	 * 
	 * 生成excelWorkBook
	 * 
	 * @param sheetName sheet页名称
	 * @param colNames 列名称
	 * @param datas 数据对象集合
	 * @param propertyNames 对象属性名
	 * @return
	 * @throws Exception
	 */
	public static Workbook exportOneSheet(String sheetName, String[] colNames, String[] propertyNames, List<?> datas) throws Exception {

		Workbook wb = new XSSFWorkbook();
		// Workbook wb = new SXSSFWorkbook(200);
		Sheet sheet = wb.createSheet(sheetName);
		sheet.setDefaultColumnWidth(20);

		// 列头单元格样式  
		CellStyle cs = wb.createCellStyle();
		cs.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);// 设置背景色
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);  
		cs.setAlignment(CellStyle.ALIGN_CENTER); // 居中
		
		Font font = wb.createFont();    
		//font.setFontName("黑体");
		//font.setFontHeightInPoints((short) 12);//设置字体大小    
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体显示
		cs.setFont(font);
		
		// 列头
		Row row = sheet.createRow(0);
		for (int i = 0; i < colNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(colNames[i]);
			cell.setCellStyle(cs);
			//列宽
			sheet.setColumnWidth(i, 20*400);
		}
		
		// 数据
		for (int i = 0; i < datas.size(); i++) {
			row = sheet.createRow(i + 1);
			Object obj = datas.get(i);
			Class clazz = obj.getClass();
			for (int j = 0; j < propertyNames.length; j++) {
				Field field = clazz.getDeclaredField(propertyNames[j]);
				field.setAccessible(true);
				String value = field.get(obj).toString();
				Cell cell = row.createCell(j);
				cell.setCellValue(value);
			}

		}
		FileOutputStream out = new FileOutputStream("D:\\book.xlsx");
		wb.write(out);
		out.close();
		
		return wb;
	}

	
	
}
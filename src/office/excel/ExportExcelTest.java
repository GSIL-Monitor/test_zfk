package office.excel;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportExcelTest {
	public static void main(String[] args) throws Exception {
		excelExport();
	}

	private static void excelExport() throws Exception {
		long t1 = System.currentTimeMillis();
		// Workbook wb = new XSSFWorkbook();
		Workbook wb = new SXSSFWorkbook(200);
		Sheet sh = wb.createSheet();
		for (int rownum = 0; rownum < 100000; rownum++) {
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < 10; cellnum++) {
				Cell cell = row.createCell(cellnum);
				String address = new CellReference(cell).formatAsString();
				cell.setCellValue(address);
			}
		}
		FileOutputStream out = new FileOutputStream(
				"C:\\Users\\ZHUFUKUN\\Desktop\\test3.xlsx");
		wb.write(out);
		out.close();
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1) / 1000);
	}

}

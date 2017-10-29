package office.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * xls工具类
 * 
 * @author hjn
 * 
 */
public class XlsUtil {
	
	public static void main(String[] args) {
//		try {
//			XlsUtil.write("C:\\Users\\ZHUFUKUN\\Desktop\\testx.xls");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			XlsUtil.read("C:\\Users\\ZHUFUKUN\\Desktop\\test1.xlsx");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void read(String filePath) throws IOException {
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1,
				filePath.length());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(stream);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(stream);
		} else {
			System.out.println("您输入的excel格式不正确");
		}
		
		Iterator<Sheet> it = wb.iterator();
		while(it.hasNext()){
			Sheet sheet1 = it.next();
			for (Row row : sheet1) {
				for (Cell cell : row) {
					System.out.print(getCellString(cell) + " ");
				}
				System.out.println();
			}
			System.out.println("***************");
		}
		
	}

	public static boolean write(String outPath) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1,
				outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 循环写入行数据
		for (int i = 0; i < 5; i++) {
			Row row = (Row) sheet1.createRow(i);
			// 循环写入列数据
			for (int j = 0; j < 8; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("测试" + i +j);
			}
		}
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}
	
	
	/**
	 * 得到单元格内容
	 * @param cell
	 * @return
	 */
	private static String getCellString(Cell cell){
		try {
			if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
				return null;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
				return cell.getBooleanCellValue() + "";
			}else if(cell.getCellType()==Cell.CELL_TYPE_ERROR){
				return cell.getErrorCellValue() + "";
			}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
				return cell.getNumericCellValue() + "";
			}else if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){//公式
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return cell.getStringCellValue()+"";
			}else{
				return cell.getStringCellValue()+"";
			}
		} catch (Exception e) {
//			e.printStackTrace();
			return null;
		}
		
	}


}

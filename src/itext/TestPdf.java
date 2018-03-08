package itext;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import sun.text.IntHashtable;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;

public class TestPdf {
	public static void main(String[] args) throws Exception {
		// String sourcePdf = "C:\\Users\\ZHUFUKUN\\Desktop\\httpclient.pdf";
		String sourcePdf = "C:\\Users\\ZHUFUKUN\\Desktop\\2017Q1.pdf";
		String destPdf = "C:\\Users\\ZHUFUKUN\\Desktop\\test111.pdf";

		// // 读入pdf文件
		PdfReader reader = new PdfReader(sourcePdf);
		Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
		f.setAccessible(true);
		f.set(reader, Boolean.TRUE);
		// List<HashMap<String, Object>> addoutlines = SimpleBookmark
		// .getBookmark(reader);

		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		// TextExtractionStrategy strategy = parser.processContent(4,
		// new SimpleTextExtractionStrategy());
		// System.out.println(strategy.getResultantText());

		parser.processContent(4, new RenderListener() {

			@Override
			public void renderText(TextRenderInfo paramTextRenderInfo) {
				System.out.println(paramTextRenderInfo.getFont() + "=========="
						+ paramTextRenderInfo.getText());
			}

			@Override
			public void renderImage(ImageRenderInfo paramImageRenderInfo) {
				// System.out.println("###################");

			}

			@Override
			public void endTextBlock() {
				// System.out.println("!!!!!!!!!!!!!!!!!!!!!");

			}

			@Override
			public void beginTextBlock() {
				// System.out.println("@@@@@@@@@@@@@@@@@@@");

			}
		});

		// byte[] bytes = createPdfOutlines2(sourcePdf);
		// FileOutputStream fos = new FileOutputStream(destPdf);
		// fos.write(bytes);
		// fos.close();
	}

	private static void addFirstOutlineReservedPdf(
			List<HashMap<String, Object>> outlines, PdfReader reader) {
		PdfDictionary catalog = reader.getCatalog();
		PdfObject obj = PdfReader.getPdfObjectRelease(catalog
				.get(PdfName.OUTLINES));
		// 没有书签
		if (obj == null || !obj.isDictionary())
			return;
		PdfDictionary outlinesDictionary = (PdfDictionary) obj;
		// 得到第一个书签
		PdfDictionary firstOutline = (PdfDictionary) PdfReader
				.getPdfObjectRelease(outlinesDictionary.get(PdfName.FIRST));

		PdfString titleObj = firstOutline.getAsString((PdfName.TITLE));
		String title = titleObj.toUnicodeString();

		PdfArray dest = firstOutline.getAsArray(PdfName.DEST);

		if (dest == null) {
			PdfDictionary action = (PdfDictionary) PdfReader
					.getPdfObjectRelease(firstOutline.get(PdfName.A));
			if (action != null) {
				if (PdfName.GOTO.equals(PdfReader.getPdfObjectRelease(action
						.get(PdfName.S)))) {
					dest = (PdfArray) PdfReader.getPdfObjectRelease(action
							.get(PdfName.D));
				}
			}
		}

		// ///
		String destStr = parseDestString(dest, reader);

		String[] decodeStr = destStr.split(" ");
		int num = Integer.valueOf(decodeStr[0]);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Title", title);
		map.put("Action", "GoTo");
		map.put("Page", num + " Fit");

		outlines.add(map);
	}

	private static String parseDestString(PdfArray dest, PdfReader reader) {
		String destStr = "";
		if (dest.isString()) {
			destStr = dest.toString();
		} else if (dest.isName()) {
			destStr = PdfName.decodeName(dest.toString());
		} else if (dest.isArray()) {
			IntHashtable pages = new IntHashtable();
			int numPages = reader.getNumberOfPages();
			for (int k = 1; k <= numPages; ++k) {
				pages.put(reader.getPageOrigRef(k).getNumber(), k);
				reader.releasePage(k);
			}

			// /
			destStr = makeBookmarkParam((PdfArray) dest, pages);
		}
		return destStr;
	}

	private static String makeBookmarkParam(PdfArray dest, IntHashtable pages) {
		StringBuffer s = new StringBuffer();
		PdfObject obj = dest.getPdfObject(0);
		if (obj.isNumber()) {
			s.append(((PdfNumber) obj).intValue() + 1);
		} else {
			// /
			s.append(pages.get(getNumber((PdfIndirectReference) obj)));
		}
		s.append(' ').append(dest.getPdfObject(1).toString().substring(1));
		for (int k = 2; k < dest.size(); ++k) {
			s.append(' ').append(dest.getPdfObject(k).toString());
		}
		return s.toString();
	}

	private static int getNumber(PdfIndirectReference indirect) {
		PdfDictionary pdfObj = (PdfDictionary) PdfReader
				.getPdfObjectRelease(indirect);
		if (pdfObj.contains(PdfName.TYPE)
				&& pdfObj.get(PdfName.TYPE).equals(PdfName.PAGES)
				&& pdfObj.contains(PdfName.KIDS)) {
			PdfArray kids = (PdfArray) pdfObj.get(PdfName.KIDS);
			indirect = (PdfIndirectReference) kids.getPdfObject(0);
		}
		return indirect.getNumber();
	}

	// private void addBookmarks(BufferedReader bufRead,
	// List<HashMap<String, Object>> outlines,
	// HashMap<String, Object> preOutline, int preLevel)
	// throws IOException {
	// String contentFormatLine = null;
	// bufRead.mark(1);
	// if ((contentFormatLine = bufRead.readLine()) != null) {
	// FormattedBookmark bookmark = parseFormmattedText(contentFormatLine);
	//
	// HashMap<String, Object> map = parseBookmarkToHashMap(bookmark);
	//
	// int level = bookmark.getLevel();
	// // 如果n==m, 那么是同一层的，这个时候，就加到ArrayList中,继续往下面读取
	// if (level == preLevel) {
	// outlines.add(map);
	// addBookmarks(bufRead, outlines, map, level);
	// }
	// // 如果n>m,那么可以肯定，该行是上一行的孩子，, new 一个kids的arraylist,并且加入到这个arraylist中
	// else if (level > preLevel) {
	// List<HashMap<String, Object>> kids = new ArrayList<HashMap<String,
	// Object>>();
	// kids.add(map);
	// preOutline.put("Kids", kids);
	// // 记录有孩子的outline信息
	// parentOutlineStack.push(new OutlineInfo(preOutline, outlines,
	// preLevel));
	// addBookmarks(bufRead, kids, map, level);
	// }
	// // 如果n<m , 那么就是说孩子增加完了，退回到上层，bufRead倒退一行
	// else if (level < preLevel) {
	// bufRead.reset();
	// OutlineInfo obj = parentOutlineStack.pop();
	// addBookmarks(bufRead, obj.getOutlines(), obj.getPreOutline(),
	// obj.getPreLevel());
	// }
	//
	// }
	// }
	//
	// private HashMap<String, Object> parseBookmarkToHashMap(
	// FormattedBookmark bookmark) {
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("Title", bookmark.getTitle());
	// map.put("Action", "GoTo");
	// map.put("Page", bookmark.getPage() + " Fit");
	// return map;
	// }
	//
	// private FormattedBookmark parseFormmattedText(String contentFormatLine) {
	// FormattedBookmark bookmark = new FormattedBookmark();
	// String title = "";
	// String destPage = "";
	//
	// // 当没有页码在字符串结尾的时候，一般就是书的名字，如果格式正确的话。
	// int lastSpaceIndex = contentFormatLine.lastIndexOf(" ");
	// if (lastSpaceIndex == -1) {
	// title = contentFormatLine;
	// destPage = "1";
	// } else {
	// title = contentFormatLine.substring(0, lastSpaceIndex);
	// destPage = contentFormatLine.substring(lastSpaceIndex + 1);
	// }
	//
	// String[] titleSplit = title.split(" ");
	// int dotCount = titleSplit[0].split("\\.").length - 1;
	//
	// bookmark.setLevel(dotCount);
	// bookmark.setPage(destPage);
	// bookmark.setTitle(title);
	// return bookmark;
	// }

	/**
	 * 按路径解析pdf文档
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPdfContent(String fileName) {
		try {
			PdfReader reader = new PdfReader(fileName); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static byte[] createPdfOutlines(String fileName) {
		ByteArrayOutputStream bos = null;
		PdfStamper stamper = null;
		try {
			PdfReader reader = new PdfReader(fileName); // 读取pdf所使用的输出流
			Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
			f.setAccessible(true);
			f.set(reader, Boolean.TRUE);
			int pageNum = reader.getNumberOfPages();// 获得页数
			StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
			boolean isArrive = false;
			for (int i = 1; i <= pageNum; i++) {
				String pageContent = PdfTextExtractor
						.getTextFromPage(reader, i);
				if (pageContent.contains(".........")) {
					content.append(pageContent); // 读取第i页的文档内容
					isArrive = true;
				} else if (isArrive) {
					break;
				}
			}
			String catelogContent = content.toString();
			String[] catelogs = catelogContent.split("\n");
			List<HashMap<String, Object>> outlines = new ArrayList<HashMap<String, Object>>();
			for (String catelog : catelogs) {
				if (catelog.contains(".........")) {
					try {
						HashMap<String, Object> map = new HashMap<String, Object>();
						String title = catelog.substring(0, catelog
								.indexOf("........."));
						String numStr = catelog.substring(catelog
								.lastIndexOf(".") + 1);
						int num = Integer.parseInt(numStr.trim());
						map.put("Title", title);
						map.put("Action", "GoTo");
						map.put("Page", num + " Fit");
						outlines.add(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			bos = new ByteArrayOutputStream();// 新建一个输出流
			stamper = new PdfStamper(reader, bos);
			stamper.setOutlines(outlines);
			stamper.close();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null){
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stamper != null){
				try {
					stamper.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static byte[] createPdfOutlines4Byte(byte[] bytes) {
		ByteArrayOutputStream bos = null;
		PdfStamper stamper = null;
		try {
			PdfReader reader = new PdfReader(bytes); // 读取pdf所使用的输出流
			Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
			f.setAccessible(true);
			f.set(reader, Boolean.TRUE);
			int pageNum = reader.getNumberOfPages();// 获得页数
			StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
			boolean isArrive = false;
			for (int i = 1; i <= pageNum; i++) {
				String pageContent = PdfTextExtractor
						.getTextFromPage(reader, i);
				if (pageContent.contains(".........")) {
					content.append(pageContent); // 读取第i页的文档内容
					isArrive = true;
				} else if (isArrive) {
					break;
				}
			}
			String catelogContent = content.toString();
			String[] catelogs = catelogContent.split("\n");
			List<HashMap<String, Object>> outlines = new ArrayList<HashMap<String, Object>>();
			for (String catelog : catelogs) {
				if (catelog.contains(".........")) {
					try {
						HashMap<String, Object> map = new HashMap<String, Object>();
						String title = catelog.substring(0, catelog
								.indexOf("........."));
						String numStr = catelog.substring(catelog
								.lastIndexOf(".") + 1);
						int num = Integer.parseInt(numStr.trim());
						map.put("Title", title);
						map.put("Action", "GoTo");
						map.put("Page", num + " Fit");
						outlines.add(map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			bos = new ByteArrayOutputStream();// 新建一个输出流
			stamper = new PdfStamper(reader, bos);
			stamper.setOutlines(outlines);
			stamper.close();
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null){
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stamper != null){
				try {
					stamper.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		}
		return null;
	}

	public static byte[] createPdfOutlines2(String fileName) {
		ByteArrayOutputStream bos = null;
		PdfStamper stamper = null;
		try {

			PdfReader reader = new PdfReader(fileName); // 读取pdf所使用的输出流
			Field f = PdfReader.class.getDeclaredField("ownerPasswordUsed");
			f.setAccessible(true);
			f.set(reader, Boolean.TRUE);
			int pageNum = reader.getNumberOfPages();// 获得页数

			PdfDictionary pdfDictionary = reader.getCatalog();
			System.out.println("###########");

			// List<Integer> needPageIndexList = new ArrayList<Integer>();
			// boolean isArrive = false;
			// for (int i = 1; i <= pageNum; i++) {
			// String pageContent = PdfTextExtractor
			// .getTextFromPage(reader, i);
			// if (pageContent.contains(".........")) {
			// needPageIndexList.add(i);
			// isArrive = true;
			// } else if (isArrive) {
			// break;
			// }
			// }

			// for (int i : needPageIndexList) {

			// PdfDictionary pdfDictionary = reader.getPageN(i);
			// }

			// List<HashMap<String, Object>> outlines = new
			// ArrayList<HashMap<String, Object>>();
			// for (String catelog : catelogs) {
			// if (catelog.contains(".........")) {
			// try {
			// HashMap<String, Object> map = new HashMap<String, Object>();
			// String title = catelog.substring(0, catelog
			// .indexOf("........."));
			// String numStr = catelog.substring(catelog
			// .lastIndexOf(".") + 1);
			// int num = Integer.parseInt(numStr.trim());
			// map.put("Title", title);
			// map.put("Action", "GoTo");
			// map.put("Page", num + " Fit");
			// outlines.add(map);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// }
			// bos = new ByteArrayOutputStream();// 新建一个输出流
			// stamper = new PdfStamper(reader, bos);
			// stamper.setOutlines(outlines);
			// stamper.close();
			// return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null){
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (stamper != null){
				try {
					stamper.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 按流解析pdf文档
	 * 
	 * @param file
	 * @return
	 */
	public static String getPdfContent(InputStream ins) {
		try {
			PdfReader reader = new PdfReader(ins); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
			}
			return content.toString();
		} catch (Throwable e) {
			return "";
		}

	}

	/**
	 * 按字节解析pdf文档
	 * 
	 * @param file
	 * @return
	 */
	public static String getPdfContent(byte[] bytes) {
		try {
			PdfReader reader = new PdfReader(bytes); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
			}
			return content.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}

	}

}

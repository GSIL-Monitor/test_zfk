package itext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class PDFCreater {

	public static byte[] generatePdf(java.util.List<ITextObject> p) {
		ByteArrayOutputStream bao = null;
		try {
			String auther = null;
			String date = null;
			String title = null;
			BaseFont helvetica = null;
			bao = new ByteArrayOutputStream();// 新建一个输出流
			Float width = (float) (PageSize.A4.getWidth());// pdf的宽度
			Font font = null;
			int fontSize = 12;
			int fontType = Font.NORMAL;// 字体类型
			try {
				helvetica = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H,
						BaseFont.NOT_EMBEDDED);
				font = new Font(helvetica, fontSize, fontType);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Document doc = new Document();
			final PdfWriter writer = PdfWriter.getInstance(doc, bao);
			doc.setPageSize(PageSize.A4);
			doc.setMargins(50, 50, 50, 50);
			doc.open();
			// boolean flag = dealWithIlligalSymbol(p);
			/*
			 * 先添加标题，然后添加作者与日期
			 */
			for (int i = 0; i < p.size(); i++) {
				if (p.get(i).getType().equals("title")) {
					if (p.get(i).getType().equals("title")) {
						title = p.get(i).getContent();
						Paragraph titlePar = new Paragraph(title, new Font(helvetica, 25, Font.BOLD));
						titlePar.setAlignment(Element.ALIGN_CENTER);
						doc.add(titlePar);
						doc.add(new Paragraph(""));// 添加一行以便添加作者与日期
						break;
					}
				}
			}
			for (int i = 0; i < p.size(); i++) {
				if (p.get(i).getType().equals("auther")) {
					auther = p.get(i).getContent();
				} else if (p.get(i).getType().equals("date")) {
					date = p.get(i).getContent();
				}
				if (auther != null && date != null) {
					Paragraph aAndd = new Paragraph("作者：" + auther + "                日期：" + date,
							new Font(helvetica, 12, Font.BOLD));
					aAndd.setSpacingBefore(10);
					aAndd.setAlignment(Element.ALIGN_CENTER);
					doc.add(aAndd);
					break;
				}

			}
			/*
			 * 如果即非标题也非作者与日期时，添加页面中的其他元素
			 */
			for (int i = 0; i < p.size(); i++) {
				if (p.get(i).getType().contains("img") || p.get(i).getType().contains("imageByte")) {
					Image b = (p.get(i).getType().contains("img") ? Image.getInstance(p.get(i).getContent())
							: Image.getInstance(p.get(i).getImagByte()));
					float widthPerPercenta = (p.get(i).getImgWidth() == 0 ? 0.7f : p.get(i).getImgWidth());
					float newWidth = width * widthPerPercenta;
					float newHeight = b.getHeight() / b.getWidth() * width * widthPerPercenta;
					b.scaleAbsolute(newWidth, newHeight);
					b.setBottom(5.0f);
					b.setAlignment(Element.ALIGN_CENTER);
					doc.add(b);
				} else if (p.get(i).getType().contains("p")) {
					Paragraph para = new Paragraph(p.get(i).getContent(), new Font(helvetica, 13, Font.NORMAL));
					font.setColor(BaseColor.GRAY);
					para.setFirstLineIndent(26);
					para.setSpacingAfter(10);
					para.setSpacingBefore(10);
					doc.add(para);// 设置缩进为2
				} else if (p.get(i).getType().contains("h1")) {
					Paragraph para = new Paragraph(p.get(i).getContent(), new Font(helvetica, 15, Font.BOLD));
					para.setSpacingAfter(10);
					para.setSpacingBefore(10);
					font.setColor(BaseColor.BLACK);
					doc.add(para);
				} else if (p.get(i).getType().contains("list")) {
					// List<Paragraph> list = new ArrayList<Paragraph>();
					for (int j = 0; j < p.get(i).getCells().size(); j++) {
						Paragraph para = new Paragraph(p.get(i).getCells().get(j),
								new Font(helvetica, 13, Font.NORMAL, BaseColor.DARK_GRAY));
						// list.add(para);
						doc.add(para);
					}
					// doc.add(list);// 设置缩进为2
				} else if (p.get(i).getType().contains("table")) {
					int tableColum = p.get(i).getCellColum();
					PdfPTable table = new PdfPTable(tableColum);
					for (int k = 0; k < p.get(i).getCells().size(); k++) {
						if (k % tableColum == 0)// 如果是首列字体为粗体
						{
							Paragraph para = new Paragraph(p.get(i).getCells().get(k),
									new Font(helvetica, 13, Font.BOLD, BaseColor.DARK_GRAY));
							para.setAlignment(Element.ALIGN_CENTER);
							table.addCell(para);
						} else {
							Paragraph para = new Paragraph(p.get(i).getCells().get(k),
									new Font(helvetica, 13, Font.NORMAL, BaseColor.DARK_GRAY));
							para.setAlignment(Element.ALIGN_CENTER);
							table.addCell(new Paragraph(p.get(i).getCells().get(k),
									new Font(helvetica, 13, Font.BOLD, BaseColor.DARK_GRAY)));
						}
					}
					table.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.setTotalWidth((doc.right() + doc.left()) * 0.6f);
					table.setSpacingBefore(10f);
					doc.add(table);
				}
				/*
				 * 添加文档页数,如果pdf页面有添加的话(只添加一次)
				 */
				writer.setPageEvent(new PdfPageEventHelper() {
					@Override
					public void onEndPage(PdfWriter writer, Document document) {

						PdfContentByte cb = writer.getDirectContent();
						cb.saveState();
						cb.beginText();
						BaseFont bf = null;
						try {
							bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
						} catch (Exception e) {
							e.printStackTrace();
						}
						cb.setFontAndSize(bf, 12);

						// Footer
						float x = document.bottom(-20);

						// 中
						cb.showTextAligned(PdfContentByte.ALIGN_CENTER, String.format("-%s-", writer.getPageNumber()),
								(document.right() + document.left()) / 2, x, 0);
						cb.endText();
						cb.restoreState();
					}
				});
			}
			/*
			 * 设置文档信息
			 */
			doc.addTitle(title);
			doc.addAuthor("能研院");
			doc.addCreator("全球互联网研究系统");
			doc.close();
			byte[] bs = bao.toByteArray();
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bao != null) {
				try {
					bao.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getContentFromPdfByPath(String fileName) {
		try {
			PdfReader reader = new PdfReader(fileName); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			String content = ""; // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content += PdfTextExtractor.getTextFromPage(reader, i); // 读取第i页的文档内容
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getContentFromPdfByByte(byte[] file) {
		try {
			PdfReader reader = new PdfReader(file); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			String content = ""; // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content += PdfTextExtractor.getTextFromPage(reader, i); // 读取第i页的文档内容
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String getContentFromPdfByInputStream(InputStream file) {
		try {
			PdfReader reader = new PdfReader(file); // 读取pdf所使用的输出流
			int num = reader.getNumberOfPages();// 获得页数
			String content = ""; // 存放读取出的文档内容
			for (int i = 1; i <= num; i++) {
				content += PdfTextExtractor.getTextFromPage(reader, i); // 读取第i页的文档内容
			}
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static byte[] appendPdf(java.util.List<byte[]> pdfs) {

		ByteArrayOutputStream bao = null;
		byte[] bs = null;
		try {
			bao = new ByteArrayOutputStream();// 新建一个输出流
			java.util.List<PdfReader> pdfReaders = new ArrayList<PdfReader>();
			for (int i = 0; i < pdfs.size(); i++) {
				try {
					pdfReaders.add(new PdfReader(pdfs.get(i)));
				} catch (Throwable e) {
					continue;
				}
			}

			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, bao);

			document.open();
			PdfContentByte cb = writer.getDirectContent();

			int totalPages = 0;
			for (int i = 0; i < pdfReaders.size(); i++) {
				totalPages += pdfReaders.get(i).getNumberOfPages();
			}

			int pageOfCurrentReaderPDF = 0;
			Iterator<PdfReader> iteratorPDFReader = pdfReaders.iterator();

			// Loop through the PDF files and add to the output.
			while (iteratorPDFReader.hasNext()) {
				PdfReader pdfReader = iteratorPDFReader.next();
				// Create a new page in the target for each source page.
				while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
					document.newPage();
					pageOfCurrentReaderPDF++;
					PdfImportedPage page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);
				}
				pageOfCurrentReaderPDF = 0;
			}
			bao.flush();
			document.close();
			bs = bao.toByteArray();
			return bs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		} finally {
			if (bao != null) {
				try {
					bao.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean dealWithIlligalSymbol(java.util.List<ITextObject> p) {
		boolean flag = false;
		try {
			for (int i = 0; i < p.size(); i++) {
				if (p.get(i).getContent() == null) {
					continue;
				}
				if (p.get(i).getContent().contains("·")) {
					p.get(i).setContent(p.get(i).getContent().replaceAll("·", ""));
				}
				if (p.get(i).getContent().contains("……")) {
					p.get(i).setContent(p.get(i).getContent().replaceAll("……", "......"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}

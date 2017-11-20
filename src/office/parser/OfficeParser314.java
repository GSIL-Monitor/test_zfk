package office.parser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hslf.usermodel.HSLFPictureShape;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFTable;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFGraphicFrame;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class OfficeParser314
{

    public static void main(String[] args) throws IOException
    {
        // InputStream is = new FileInputStream(new
        // File("C:\\Users\\ZHUFUKUN\\Desktop\\中华人民共和国消费税暂行条例.txt"));
        // ByteArrayOutputStream os = new ByteArrayOutputStream();
        // byte[] buffer = new byte[1024 * 1024];
        // int len = -1;
        // while ((len = is.read(buffer)) != -1)
        // {
        // os.write(buffer, 0, len);
        // }
        // byte[] bytes = os.toByteArray();
        //
        // System.out.println(new String(bytes, "gbk"));
        //
        // is.close();
        // os.close();

        // System.out.println(getTxtContent("C:\\Users\\ZHUFUKUN\\Desktop\\中华人民共和国消费税暂行条例.txt",
        // "utf-8"));

        // getExcelContent("C:\\Users\\ZHUFUKUN\\Desktop\\全球能源研究系统菜单明细.xlsx");

        // getWordContent("C:\\Users\\ZHUFUKUN\\Desktop\\国际电力同业概况.doc");
        // getWordContent(new FileInputStream(new
        // File("C:\\Users\\ZHUFUKUN\\Desktop\\国际电力同业概况.docx")),"docx");

    }

    /**
     * 根据文件字节获取文件内容
     * 
     * @param bytes
     * @param fileSuffix
     * @return
     */
    public static String getDocumentContent(byte[] bytes, String fileSuffix)
    {
        try
        {
            if ("txt".equalsIgnoreCase(fileSuffix))
            {
                return new String(bytes, "gbk");
            } else if ("doc".equalsIgnoreCase(fileSuffix) || "docx".equalsIgnoreCase(fileSuffix))
            {
                return getWordContent(bytes, fileSuffix);
            } else if ("xls".equalsIgnoreCase(fileSuffix) || "xlsx".equalsIgnoreCase(fileSuffix))
            {
                return getExcelContent(bytes, fileSuffix);
            } else if ("ppt".equalsIgnoreCase(fileSuffix) || "pptx".equalsIgnoreCase(fileSuffix))
            {
                return getPptContent(bytes, fileSuffix);
            } else if ("pdf".equalsIgnoreCase(fileSuffix))
            {
                return getPdfContent(bytes);
            } else
            {
                return "";
            }
        } catch (Exception e)
        {
            return "";
        }

    }

    /**
     * 根据流获取内容
     * 
     * @param in
     * @param fileSuffix
     * @return
     */
    public static String getDocumentContent(InputStream in, String fileSuffix)
    {
        try
        {
            if ("txt".equalsIgnoreCase(fileSuffix))
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                // 对input进行解析
                byte[] bs = new byte[1024 * 1024];
                int len = 0;
                while ((len = in.read(bs)) != -1)
                {
                    bos.write(bs, 0, len);
                }
                return new String(bos.toByteArray());
            } else if ("doc".equalsIgnoreCase(fileSuffix) || "docx".equalsIgnoreCase(fileSuffix))
            {
                return getWordContent(in, fileSuffix);
            } else if ("xls".equalsIgnoreCase(fileSuffix) || "xlsx".equalsIgnoreCase(fileSuffix))
            {
                return getExcelContent(in, fileSuffix);
            } else if ("ppt".equalsIgnoreCase(fileSuffix) || "pptx".equalsIgnoreCase(fileSuffix))
            {
                return getPptContent(in, fileSuffix);
            } else if ("pdf".equalsIgnoreCase(fileSuffix))
            {
                return getPdfContent(in);
            } else
            {
                return "";
            }
        } catch (Exception e)
        {
            return "";
        }
    }

    /**
     * 按路径解析txt文档
     * 
     * @param filePath
     * @return
     */
    public static String getTxtContent(String filePath, String charset)
    {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try
        {
            is = new FileInputStream(new File(filePath));
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, len);
            }
            String str = new String(os.toByteArray(), charset);
            // System.out.println(str);// /////
            return str;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            if (os != null)
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按流解析txt文档
     * 
     * @param filePath
     * @return
     */
    public static String getTxtContent(InputStream is, String charset)
    {
        ByteArrayOutputStream os = null;
        try
        {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, len);
            }
            String str = new String(os.toByteArray(), charset);
            // System.out.println(str);// ////
            return str;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (os != null)
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按路径解析word文档
     * 
     * @param filePath
     * @return
     */
    public static String getWordContent(String filePath)
    {
        InputStream is = null;
        try
        {
            String fileSuffix = getFileSuffix(filePath);
            if ("doc".equalsIgnoreCase(fileSuffix))
            {
                // word 2003： 图片不会被读取
                is = new FileInputStream(new File(filePath));
                WordExtractor ex = new WordExtractor(is);
                String text2003 = ex.getText();
                // System.out.println(text2003);// ////////
                return text2003;
            } else if ("docx".equalsIgnoreCase(fileSuffix))
            {
                // word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                String text2007 = extractor.getText();
                // System.out.println(text2007);// /////////////
                return text2007;
            } else
            {
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按流解析word文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getWordContent(InputStream is, String fileSuffix)
    {
        try
        {
            if ("doc".equalsIgnoreCase(fileSuffix))
            {
                // word 2003： 图片不会被读取
                WordExtractor ex = new WordExtractor(is);
                String text2003 = ex.getText();
                // System.out.println(text2003);// //////
                return text2003;
            } else if ("docx".equalsIgnoreCase(fileSuffix))
            {
                // word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                String text2007 = extractor.getText();
                // System.out.println(text2007);// /////
                return text2007;
            } else
            {
                return "";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 按字节解析word文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getWordContent(byte[] bytes, String fileSuffix)
    {
        ByteArrayInputStream is = null;
        try
        {
            is = new ByteArrayInputStream(bytes);
            if ("doc".equalsIgnoreCase(fileSuffix))
            {
                // word 2003： 图片不会被读取
                WordExtractor ex = new WordExtractor(is);
                String text2003 = ex.getText();
                // System.out.println(text2003);// //////
                return text2003;
            } else if ("docx".equalsIgnoreCase(fileSuffix))
            {
                // word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                String text2007 = extractor.getText();
                // System.out.println(text2007);// /////
                return text2007;
            } else
            {
                return "";
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按路径解析excel文档
     * 
     * @param filePath
     * @return
     */
    public static String getExcelContent(String filePath)
    {
        InputStream is = null;
        try
        {
            String fileSuffix = getFileSuffix(filePath);
            is = new FileInputStream(new File(filePath));
            Workbook wb = null;
            if ("xls".equalsIgnoreCase(fileSuffix))
            {
                wb = new HSSFWorkbook(is);
            } else if ("xlsx".equalsIgnoreCase(fileSuffix))
            {
                wb = new XSSFWorkbook(is);
            }
            Iterator<Sheet> sheets = wb.sheetIterator();
            if (sheets.hasNext())
            {
                Sheet sheet = sheets.next();
                StringBuffer sb = new StringBuffer();
                for (Row row : sheet)
                {
                    for (Cell cell : row)
                    {
                        String cellValue = getCellString(cell);
                        if (cellValue.length() != 0)
                        {
                            sb.append("[" + cellValue + "]");
                        }
                    }
                }
                // System.out.println(sb.toString());// ///////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按流解析excel文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getExcelContent(InputStream is, String fileSuffix)
    {
        try
        {
            Workbook wb = null;
            if ("xls".equalsIgnoreCase(fileSuffix))
            {
                wb = new HSSFWorkbook(is);
            } else if ("xlsx".equalsIgnoreCase(fileSuffix))
            {
                wb = new XSSFWorkbook(is);
            }
            Iterator<Sheet> sheets = wb.sheetIterator();
            if (sheets.hasNext())
            {
                Sheet sheet = sheets.next();
                StringBuffer sb = new StringBuffer();
                for (Row row : sheet)
                {
                    for (Cell cell : row)
                    {
                        String cellValue = getCellString(cell);
                        if (cellValue.length() != 0)
                        {
                            sb.append("[" + cellValue + "]");
                        }
                    }
                }
                // System.out.println(sb.toString());// /////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 按字节解析excel文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getExcelContent(byte[] bytes, String fileSuffix)
    {
        ByteArrayInputStream is = null;
        try
        {
            is = new ByteArrayInputStream(bytes);
            Workbook wb = null;
            if ("xls".equalsIgnoreCase(fileSuffix))
            {
                wb = new HSSFWorkbook(is);
            } else if ("xlsx".equalsIgnoreCase(fileSuffix))
            {
                wb = new XSSFWorkbook(is);
            }
            Iterator<Sheet> sheets = wb.sheetIterator();
            if (sheets.hasNext())
            {
                Sheet sheet = sheets.next();
                StringBuffer sb = new StringBuffer();
                for (Row row : sheet)
                {
                    for (Cell cell : row)
                    {
                        String cellValue = getCellString(cell);
                        if (cellValue.length() != 0)
                        {
                            sb.append("[" + cellValue + "]");
                        }
                    }
                }
                // System.out.println(sb.toString());// ///////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 得到单元格内容
     * 
     * @param cell
     * @return
     */
    private static String getCellString(Cell cell)
    {
        try
        {
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
            {
                return "";
            } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
            {
                return cell.getBooleanCellValue() + "";
            } else if (cell.getCellType() == Cell.CELL_TYPE_ERROR)
            {
                return cell.getErrorCellValue() + "";
            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
            {
                return cell.getNumericCellValue() + "";
            } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA)
            {// 公式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue() + "";
            } else
            {
                return cell.getStringCellValue() + "";
            }
        } catch (Exception e)
        {
            // e.printStackTrace();
            return "";
        }

    }

    /**
     * 按路径解析ppt文档
     * 
     * @param filePath
     * @return
     */
    public static String getPptContent(String filePath)
    {
        InputStream is = null;
        try
        {
            String fileSuffix = getFileSuffix(filePath);
            is = new FileInputStream(new File(filePath));
            if ("ppt".equalsIgnoreCase(fileSuffix))
            {
                HSLFSlideShow show = new HSLFSlideShow(is);
                List<HSLFSlide> slides = show.getSlides();
                StringBuffer sb = new StringBuffer();
                for (HSLFSlide slide : slides)
                {
                    List<HSLFShape> shapes = slide.getShapes();
                    for (HSLFShape shape : shapes)
                    {
                        if (shape instanceof HSLFTextShape)
                        {
                            // 文本
                            HSLFTextShape textShape = (HSLFTextShape) shape;
                            String textValue = textShape.getText();
                            // //System.out.println(textValue);////////
                            sb.append(textValue);
                        } else if (shape instanceof HSLFTable)
                        {
                            // 表格
                            HSLFTable hslfTable = (HSLFTable) shape;
                            int rowSize = hslfTable.getNumberOfRows();
                            int columnSize = hslfTable.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = hslfTable.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof HSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            } else if ("pptx".equalsIgnoreCase(fileSuffix))
            {
                XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
                List<XSLFSlide> slides = xmlSlideShow.getSlides();
                StringBuffer sb = new StringBuffer();
                for (XSLFSlide slide : slides)
                {
                    List<XSLFShape> shapes = slide.getShapes();
                    for (XSLFShape shape : shapes)
                    {
                        if (shape instanceof XSLFTextShape)
                        {
                            // 文本
                            XSLFTextShape textShape = (XSLFTextShape) shape;
                            String textValue = textShape.getText();
                            sb.append(textValue);
                        } else if (shape instanceof XSLFTable)
                        {
                            // 表格
                            XSLFTable table = (XSLFTable) shape;
                            int rowSize = table.getNumberOfRows();
                            int columnSize = table.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = table.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof XSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else if (shape instanceof XSLFGraphicFrame)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按流解析ppt文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getPptContent(InputStream is, String fileSuffix)
    {
        try
        {
            if ("ppt".equalsIgnoreCase(fileSuffix))
            {
                HSLFSlideShow show = new HSLFSlideShow(is);
                List<HSLFSlide> slides = show.getSlides();
                StringBuffer sb = new StringBuffer();
                for (HSLFSlide slide : slides)
                {
                    List<HSLFShape> shapes = slide.getShapes();
                    for (HSLFShape shape : shapes)
                    {
                        if (shape instanceof HSLFTextShape)
                        {
                            // 文本
                            HSLFTextShape textShape = (HSLFTextShape) shape;
                            String textValue = textShape.getText();
                            sb.append(textValue);
                        } else if (shape instanceof HSLFTable)
                        {
                            // 表格
                            HSLFTable hslfTable = (HSLFTable) shape;
                            int rowSize = hslfTable.getNumberOfRows();
                            int columnSize = hslfTable.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = hslfTable.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof HSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            } else if ("pptx".equalsIgnoreCase(fileSuffix))
            {
                XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
                List<XSLFSlide> slides = xmlSlideShow.getSlides();
                StringBuffer sb = new StringBuffer();
                for (XSLFSlide slide : slides)
                {
                    List<XSLFShape> shapes = slide.getShapes();
                    for (XSLFShape shape : shapes)
                    {
                        if (shape instanceof XSLFTextShape)
                        {
                            // 文本
                            XSLFTextShape textShape = (XSLFTextShape) shape;
                            String textValue = textShape.getText();
                            sb.append(textValue);
                        } else if (shape instanceof XSLFTable)
                        {
                            // 表格
                            XSLFTable table = (XSLFTable) shape;
                            int rowSize = table.getNumberOfRows();
                            int columnSize = table.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = table.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof XSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else if (shape instanceof XSLFGraphicFrame)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 按字节解析ppt文档
     * 
     * @param is
     * @param fileSuffix
     * @return
     */
    public static String getPptContent(byte[] bytes, String fileSuffix)
    {
        ByteArrayInputStream is = null;
        try
        {
            is = new ByteArrayInputStream(bytes);
            if ("ppt".equalsIgnoreCase(fileSuffix))
            {
                HSLFSlideShow show = new HSLFSlideShow(is);
                List<HSLFSlide> slides = show.getSlides();
                StringBuffer sb = new StringBuffer();
                for (HSLFSlide slide : slides)
                {
                    List<HSLFShape> shapes = slide.getShapes();
                    for (HSLFShape shape : shapes)
                    {
                        if (shape instanceof HSLFTextShape)
                        {
                            // 文本
                            HSLFTextShape textShape = (HSLFTextShape) shape;
                            String textValue = textShape.getText();
                            sb.append(textValue);
                        } else if (shape instanceof HSLFTable)
                        {
                            // 表格
                            HSLFTable hslfTable = (HSLFTable) shape;
                            int rowSize = hslfTable.getNumberOfRows();
                            int columnSize = hslfTable.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = hslfTable.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof HSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            } else if ("pptx".equalsIgnoreCase(fileSuffix))
            {
                XMLSlideShow xmlSlideShow = new XMLSlideShow(is);
                List<XSLFSlide> slides = xmlSlideShow.getSlides();
                StringBuffer sb = new StringBuffer();
                for (XSLFSlide slide : slides)
                {
                    List<XSLFShape> shapes = slide.getShapes();
                    for (XSLFShape shape : shapes)
                    {
                        if (shape instanceof XSLFTextShape)
                        {
                            // 文本
                            XSLFTextShape textShape = (XSLFTextShape) shape;
                            String textValue = textShape.getText();
                            sb.append(textValue);
                        } else if (shape instanceof XSLFTable)
                        {
                            // 表格
                            XSLFTable table = (XSLFTable) shape;
                            int rowSize = table.getNumberOfRows();
                            int columnSize = table.getNumberOfColumns();
                            for (int j = 0; j < rowSize; j++)
                            {
                                for (int k = 0; k < columnSize; k++)
                                {
                                    String cellValue = table.getCell(j, k).getText();
                                    sb.append("[" + cellValue + "]");
                                }
                            }
                        } else if (shape instanceof XSLFPictureShape)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else if (shape instanceof XSLFGraphicFrame)
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        } else
                        {
                            // System.out.println(shape.getClass() +
                            // "##########");
                        }
                    }
                }
                // System.out.println(sb.toString());// //////
                return sb.toString();
            }
            return "";
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        } finally
        {
            if (is != null)
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    /**
     * 按路径解析pdf文档
     * 
     * @param fileName
     * @return
     */
    public static String getPdfContent(String fileName)
    {
        try
        {
            PdfReader reader = new PdfReader(fileName); // 读取pdf所使用的输出流
            int num = reader.getNumberOfPages();// 获得页数
            StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
            for (int i = 1; i <= num; i++)
            {
                content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
            }
            return content.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 按流解析pdf文档
     * 
     * @param file
     * @return
     */
    public static String getPdfContent(InputStream file)
    {
        try
        {
            PdfReader reader = new PdfReader(file); // 读取pdf所使用的输出流
            int num = reader.getNumberOfPages();// 获得页数
            StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
            for (int i = 1; i <= num; i++)
            {
                content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
            }
            return content.toString();
        } catch (Throwable e)
        {
            return "";
        }

    }

    /**
     * 按字节解析pdf文档
     * 
     * @param file
     * @return
     */
    public static String getPdfContent(byte[] bytes)
    {
        try
        {
            PdfReader reader = new PdfReader(bytes); // 读取pdf所使用的输出流
            int num = reader.getNumberOfPages();// 获得页数
            StringBuffer content = new StringBuffer(); // 存放读取出的文档内容
            for (int i = 1; i <= num; i++)
            {
                content.append(PdfTextExtractor.getTextFromPage(reader, i)); // 读取第i页的文档内容
            }
            return content.toString();
        } catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取文件类型
     * 
     * @param fileName
     * @return
     */
    public static String getFileSuffix(String fileName)
    {
        try
        {
            int splitIndex = fileName.lastIndexOf(".");
            return fileName.substring(splitIndex + 1);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 过滤换行符，回头符
     * 
     * @param str
     * @return
     */
    public static String rnFilter(String str)
    {
        try
        {
            String reStr = str.replaceAll("\r|\n", "");
            return reStr;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 过滤空白
     * 
     * @param str
     * @return
     */
    public static String blankFilter(String str)
    {
        try
        {
            String reStr = str.replaceAll(" |　", "");
            return reStr;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 过滤数字
     * 
     * @param str
     * @return
     */
    public static String numFilter(String str)
    {
        try
        {
            String reStr = str.replaceAll("-?[0-9]+.?[0-9]+|[0-9]", "");
            return reStr;
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

}

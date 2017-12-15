package office.jacob;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * office文档转换工具
 * 
 * @author ZHUFUKUN
 * 
 */
public class JacobUtil
{
    private final static Log log = LogFactory.getLog(JacobUtil.class);

    private static final int wdFormatPDF = 17;
    private static final int xlTypePDF = 0;
    private static final int ppSaveAsPDF = 32;
    private static final int msoTrue = -1;
    private static final int msofalse = 0;

    private static JacobUtil instance = null;

    private JacobUtil()
    {
    }

    public synchronized static JacobUtil getInstance()
    {
        if (instance == null)
        {
            instance = new JacobUtil();
        }
        return instance;
    }

    /**
     * 直接调用这个方法即可
     */
    public boolean convert2PDF(String inputFile, String pdfFile)
    {
        String suffix = getFileSufix(inputFile);
        if (suffix == null)
        {
            log.error("JacobUtil.convert2PDF() 文件没有对应格式！");
            return false;
        }
        File file = new File(inputFile);
        if (!file.exists())
        {
            log.error("JacobUtil.convert2PDF() 文件不存在！");
            return false;
        }
        if (suffix.equals("pdf"))
        {
            return false;
        }
        if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt"))
        {
            return word2PDF(inputFile, pdfFile);
        } else if (suffix.equals("ppt") || suffix.equals("pptx"))
        {
            return ppt2PDF(inputFile, pdfFile);
        } else if (suffix.equals("xls") || suffix.equals("xlsx"))
        {
            return excel2PDF(inputFile, pdfFile);
        } else
        {
            log.error("JacobUtil.convert2PDF() 文件格式不支持转换!");
            return false;
        }
    }

    /**
     * 获取文件后缀
     * 
     * @param fileName
     * @return
     */
    public String getFileSufix(String fileName)
    {
        try
        {
            int splitIndex = fileName.lastIndexOf(".");
            return fileName.substring(splitIndex + 1);
        } catch (Exception e)
        {
            return null;
        }
    }

    public boolean word2PDF(String inputFile, String pdfFile)
    {
        try
        {
            // 打开word应用程序
            ActiveXComponent app = new ActiveXComponent("Word.Application");
            // 设置word不可见
            app.setProperty("Visible", false);
            // 获得word中所有打开的文档,返回Documents对象
            Dispatch docs = app.getProperty("Documents").toDispatch();
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
            // 调用Document对象的SaveAs方法，将文档保存为pdf格式
            /*
             * Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF
             * //word保存为pdf格式宏，值为17 );
             */
            Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF // word保存为pdf格式宏，值为17
            );
            // 关闭文档
            Dispatch.call(doc, "Close", false);
            // 关闭word应用程序
            app.invoke("Quit", 0);
            return true;
        } catch (Exception e)
        {
            log.error("JacobUtil.word2PDF()  " + e.getMessage());
            return false;
        } catch (Throwable e)
        {
            log.error("JacobUtil.word2PDF()  系统没有officec插件");
            return false;
        }
    }

    public boolean excel2PDF(String inputFile, String pdfFile)
    {
        try
        {
            ActiveXComponent app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            Dispatch excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
            Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
            Dispatch.call(excel, "Close", false);
            app.invoke("Quit");
            return true;
        } catch (Exception e)
        {
            log.error("JacobUtil.excel2PDF()  " + e.getMessage());
            return false;
        } catch (Throwable e)
        {
            log.error("JacobUtil.excel2PDF()  系统没有officec插件");
            return false;
        }

    }

    public boolean ppt2PDF(String inputFile, String pdfFile)
    {
        try
        {
            ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
            // app.setProperty("Visible", msofalse);
            Dispatch ppts = app.getProperty("Presentations").toDispatch();
            Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
                    ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFile, ppSaveAsPDF);
            Dispatch.call(ppt, "Close");
            app.invoke("Quit");
            return true;
        } catch (Exception e)
        {
            log.error("JacobUtil.ppt2PDF()  " + e.getMessage());
            return false;
        } catch (Throwable e)
        {
            log.error("JacobUtil.ppt2PDF()  系统没有officec插件");
            return false;
        }
    }
}

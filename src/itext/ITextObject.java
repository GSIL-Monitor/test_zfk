package itext;

import java.util.ArrayList;
import java.util.List;

/**
 * itext生成成员类，用来给pdf中的各种元素赋值并加以初始化。
 * 
 * @author longy
 * 
 */
public class ITextObject
{
    /**
     * 类型
     */
    public static final String TITLE = "title";
    public static final String IMAGE = "img";
    public static final String TABLE = "table";
    public static final String AUTHER = "auther";
    public static final String DATE = "date";
    public static final String H1 = "h1";
    public static final String PARAGRAPH = "p";
    public static final String LIST = "list";
    public static final String IMAGEBYTE = "imageByte";

    // 类型：tile/p/img/table
    private String type;// 类型
    private String content;// 内容
    private byte[] imagByte;
    private List<String> cells = new ArrayList<String>();// 表格或者列表内容
    private float imgWidth;// 图片大小百分比，a4纸张的宽度为1
    private int cellColum;// 表格列数

    public int getCellColum()
    {
        return cellColum;
    }

    public void setCellColum(int cellColum)
    {
        this.cellColum = cellColum;
    }

    public float getImgWidth()
    {
        return imgWidth;
    }

    public void setImgWidth(float imgWidth)
    {
        this.imgWidth = imgWidth;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public List<String> getCells()
    {
        return cells;
    }

    public void setCells(List<String> cells)
    {
        this.cells = cells;
    }

    public ITextObject()
    {

    }

    /**
     * 文本类构造函数(包括链接指向的默认宽度的图片)
     * 
     * @param type
     * @param content
     * @param cells
     * @throws unDefineTypeException
     */
    public ITextObject(String type, String content, List<String> cells, int cellColum) throws Exception
    {
        this.cells = cells;
        this.content = content;
        this.type = type;
        this.imgWidth = 0;
        this.cellColum = cellColum;
        if (!(AUTHER + DATE + IMAGE + TABLE + TITLE + PARAGRAPH + LIST + H1).contains(type))
        {
            throw new Exception("没有此类型，请重新设定类型");
        }
    }

    /**
     * 文本类构造函数(包括链接指向的默认宽度的图片)
     * 
     * @param type
     * @param content
     * @param cells
     * @throws unDefineTypeException
     */
    public ITextObject(String type, String content, List<String> cells) throws Exception
    {
        this.cells = cells;
        this.content = content;
        this.type = type;
        this.imgWidth = 0;
        if (!(AUTHER + DATE + IMAGE + TABLE + TITLE + PARAGRAPH + LIST + H1).contains(type))
        {
            throw new Exception("没有此类型，请重新设定类型");
        }
    }

    /**
     * 公共构造函数
     * 
     * @param type
     * @param content
     * @param cells
     * @param imgWidth
     * @throws unDefineTypeException
     */
    public ITextObject(String type, String content, float imgWidth) throws Exception
    {
        super();
        this.type = type;
        this.content = content;
        this.imgWidth = imgWidth;
        if (!(IMAGE).contains(type))
        {
            throw new Exception("没有此类型，请重新设定类型");
        }
    }

    /**
     * 图片byte数组构造函数
     * 
     * @param type
     * @param imagByte
     * @throws unDefineTypeException
     */
    public ITextObject(String type, byte[] imagByte, float imgWidth) throws Exception
    {
        super();
        this.type = type;
        this.imagByte = imagByte;
        this.imgWidth = imgWidth;
        if (!(IMAGEBYTE).contains(type))
        {
            throw new Exception("没有此类型，请重新设定类型");
        }
    }

    public byte[] getImagByte()
    {
        return imagByte;
    }

    public void setImagByte(byte[] imagByte)
    {
        this.imagByte = imagByte;
    }

}

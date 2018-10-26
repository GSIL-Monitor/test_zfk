package regex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FileUtil {


    /**
     * 读取文档
     *
     * @param filePath 文档在src的相对路径
     * @return 包含文档内容的字符串
     */
    public static String readTxtFileFromSRC(String filePath,String charset) {
        StringBuilder stringBuilder=new StringBuilder();
        try {
            //返回读取指定资源的输入流
            InputStream is=FileUtil.class.getResourceAsStream("/"+filePath);
            BufferedReader br=new BufferedReader(new InputStreamReader(is,charset));
            String s="";
            while((s=br.readLine())!=null)
                stringBuilder.append(s);
        }catch (Exception e){
        	return null;
        }
        return stringBuilder.toString();
    }
    
    public static String readTxtFile(String filePath,String charset) {
        StringBuilder stringBuilder=new StringBuilder();
        try {
        	File filename = new File(filePath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言 
            String s="";
            while((s=br.readLine())!=null)
                stringBuilder.append(s);
        }catch (Exception e){
            return null;
        }
        return stringBuilder.toString();
    }
    
    public static String[] readTxtFileReturnLine(String filePath,String charset) {
    	 List<String> returnLine=new LinkedList<>();
        try {
        	File filename = new File(filePath);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言 
            String s="";
            while((s=br.readLine())!=null)
            	returnLine.add(s);
        }catch (Exception e){
            return null;
        }
        return returnLine.toArray(new String[0]);
    }
    
    /**
     * 读取文档,默认utf-8编码
     * @param filePath 文档在src的相对路径
     * @return 包含文档内容的字符串
     */
    public static String readTxtFileFromSRC(String filePath) {
    	return readTxtFileFromSRC(filePath,"UTF-8");
    }
    
    /**
     * 读取文档
     * @param filePath 文档在src的相对路径
     * @return 字符串列表，每个都是一行
     */
    public static String [] readTxtFileReturnLineFromSRC(String filePath,String charset){
    	 List<String> returnLine=new LinkedList<>();
         try {
             //返回读取指定资源的输入流
             InputStream is=FileUtil.class.getResourceAsStream("/"+filePath);
             BufferedReader br=new BufferedReader(new InputStreamReader(is,charset));
             String s="";
             while((s=br.readLine())!=null)
                 returnLine.add(s);
         }catch (Exception e){
        	 return null;
         }
         return returnLine.toArray(new String[0]);
    }
    /**
     * 读取文档,默认GBK编码
     * @param filePath 文档在src的相对路径
     * @return 字符串列表，每个都是一行
     */
    public static String [] readTxtFileReturnLineFromSRC(String filePath){
    	 return readTxtFileReturnLineFromSRC(filePath, "GBK");
    }
    
    
    /**
	 * write content to file
	 * @param file
	 * @param content
	 * @param append
	 * @return
	 */
	public static void WriteFile(File file,String content,boolean append){
		if(!file.exists()){
			CreateFile(true, file.getAbsolutePath());
		}
		FileWriter fw;
		try {
			fw = new FileWriter(file,append);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * write content to file
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static void WriteFile(String filePath,String content,boolean append){
        File file = new File(filePath);
        WriteFile(file,content,append);
	}
	
	/**
	 * create file
	 * @param isFile true：file ,false: fileSet
	 * @param path file path
	 * @return
	 */
	public static void CreateFile(boolean isFile,String path){
		File file =new File(path);
		try {
			if(isFile==false){
				file.mkdirs();
			}else{
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

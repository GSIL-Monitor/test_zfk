package zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * <p>Description: </p>
 *
 * @author evan
 * @version 1.0.0
 * @date 2018/11/30
 */
public class ZipUtils {
    private ZipUtils() {
    }

    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    public static void doCompress(String fileName, ZipOutputStream out) throws IOException {
        doCompress(new File(fileName), out);
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtils.doCompress(file, out, name);
                }
            }
        } else {
            ZipUtils.doZip(inFile, out, dir);
        }
    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        //  doCompress("D:\\data", "D:/java.zip");
        String fileNameArr = "D:\\data\\node_root_template.xlsx,D:\\data\\node_root_template1.xlsx,D:\\data\\node_sentences.xlsx,D:\\data\\node_template.xlsx,D:\\data\\sentences_template34.xlsx";
        String[] files = fileNameArr.split(",");
        String zipName =  RandomStringUtils.random(4, "0123456789") + ".zip";

        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream("D:\\" + zipName));
            for (String fileName : files) {
                ZipUtils.doCompress(new File(fileName), out);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException ex) {

            }
        }
    }
}

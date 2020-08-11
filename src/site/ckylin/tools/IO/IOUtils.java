/**
 * @Package: site.ckylin.tools.IO
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:54
 */
package site.ckylin.tools.IO;

import java.io.*;

public class IOUtils {
    /**
     * 创建文件夹
     *
     * @param name 文件夹路径
     * @return 是否成功
     */
    public static boolean mkdirs(String name) {
        return new File(name).mkdirs();
    }

    /**
     * 创建空白文件
     *
     * @param path 文件路径
     * @return 返回是否执行了创建
     */
    public static synchronized boolean touch(String path) {
        File f = new File(path);
        if (f.exists()) {
            return false;
        }
        try {
            return f.createNewFile();
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * 读取文件内容
     *
     * @param path 文件路径
     * @return 文件内容
     */
    public static synchronized String readFile(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            //Utils.error("File not found.");
            return null;
        }
        StringBuilder contents = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
            String line;
            boolean firstLine = true;
            while ((line = bufferedReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    contents.append("\n");
                }
                contents.append(line);
            }
        } catch (IOException e) {
            //Utils.error("Errored while opening file.");
            e.printStackTrace();
        }
        return contents.toString();
    }

    /**
     * 写入文件
     *
     * @param path  文件路径
     * @param lines 每行内容组成的数组
     */
    public static synchronized void writeFile(String path, String[] lines) {
        touch(path);
        try (PrintStream ps = new PrintStream(path)) {
            for (String line : lines) {
                ps.println(line);
            }
        } catch (FileNotFoundException ignored) {
        }
    }
}

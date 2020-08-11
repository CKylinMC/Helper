/**
 * @Package: site.ckylin.tools.IO
 * @author: CKylinMC
 * @description:
 * @date: 2020-08-07 18:55
 */
package site.ckylin.tools.IO;

import java.io.*;
import java.util.Properties;

public class PropUtils {
    /**
     * 加载配置
     *
     * @param path 配置文件路径
     * @return 配置对象
     */
    public static synchronized Properties loadProperties(String path) {
        File f = new File(path);
        if (!f.exists() || !f.isFile()) {
            //Utils.error("Properties file not found.");
            return null;
        }
        Properties properties = new Properties();
        try (FileInputStream fs = new FileInputStream(f)) {
            properties.load(fs);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 从类资源加载配置
     *
     * @param path   配置文件路径
     * @param loader 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 配置对象
     */
    public static synchronized Properties loadPropertiesAsClassResource(String path, ClassLoader loader) {
        Properties properties = new Properties();
        try (InputStream is = loader.getResourceAsStream(path)) {
            properties.load(is);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 从类资源加载配置
     *
     * @param path  配置文件路径
     * @param clazz 对应类的ClassLoader <br> <b>示范：</b><br>Static: <code> ClassName.class.getClassLoader() </code><br>Instance: <code> this.getClass() </code>
     * @return 配置对象
     */
    public static synchronized Properties loadPropertiesAsClassResource(String path, Class<?> clazz) {
        Properties properties = new Properties();
        try (InputStream is = clazz.getResourceAsStream(path)) {
            properties.load(is);
        } catch (IOException e) {
            //Utils.error("Errored while loading properties");
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 保存配置
     *
     * @param properties 配置对象
     * @param path       配置文件位置
     */
    public static synchronized void saveProperties(Properties properties, String path) {
        saveProperties(properties, path, "Properties file");
    }

    /**
     * 保存配置
     *
     * @param properties 配置对象
     * @param path       配置文件位置
     * @param comment    配置文件注释
     */
    public static synchronized void saveProperties(Properties properties, String path, String comment) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            properties.store(fos, comment);
        } catch (IOException e) {
            //Utils.debug("Errored while saving properties.");
            e.printStackTrace();
        }
    }
}

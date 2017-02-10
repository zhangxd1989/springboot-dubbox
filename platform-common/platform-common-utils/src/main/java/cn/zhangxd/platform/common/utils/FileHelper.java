package cn.zhangxd.platform.common.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;

/**
 * 文件操作工具类
 * 实现文件的创建、删除、复制以及目录的创建、删除、复制等功能
 *
 * @author zhangxd
 */
public class FileHelper extends FileUtils {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    /**
     * 文件未发现
     */
    private static final String FILE_NOT_FIND = "%s 文件不存在!";

    /**
     * Buff Size
     */
    private static final int BUF_SIZE = 1024 * 100;

    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODING = "utf8";

    /**
     * 复制文件，可以复制单个文件或文件夹
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功 ，则返回true，否是返回false
     * @throws IOException the io exception
     */
    public static boolean copy(String srcFileName, String descFileName) throws IOException {
        File file = new File(srcFileName);
        if (!file.exists()) {
            LOGGER.debug(String.format(FILE_NOT_FIND, srcFileName));
            return false;
        } else {
            if (file.isFile()) {
                return !copyFile(srcFileName, descFileName);
            } else {
                return !copyDirectory(srcFileName, descFileName);
            }
        }
    }

    /**
     * 复制单个文件，如果目标文件存在，则不覆盖
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果复制成功 ，则返回true，否则返回false
     * @throws IOException the io exception
     */
    public static boolean copyFile(String srcFileName, String descFileName) throws IOException {
        return copyFileCover(srcFileName, descFileName, false);
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @param coverlay     如果目标文件已存在，是否覆盖
     * @return 如果复制成功 ，则返回true，否则返回false
     * @throws IOException the io exception
     */
    public static boolean copyFileCover(String srcFileName,
                                        String descFileName, boolean coverlay) throws IOException {
        File srcFile = new File(srcFileName);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new FileNotFoundException(String.format("复制文件失败，源文件 %s 不存在!", srcFileName));
        } else if (!srcFile.isFile()) { // 判断源文件是否是合法的文件
            throw new FileNotFoundException(String.format("复制文件失败，%s 不是一个文件!", srcFileName));
        }
        File descFile = new File(descFileName);
        // 判断目标文件是否存在
        if (descFile.exists()) {
            // 如果目标文件存在，并且允许覆盖
            if (coverlay) {
                if (delFile(descFileName)) {
                    throw new IOException(String.format("删除目标文件 %s 失败!", descFileName));
                }
            } else {
                throw new FileAlreadyExistsException(String.format("复制文件失败，目标文件 %s 已存在!", descFileName));
            }
        } else {
            if (!descFile.getParentFile().exists() && !mkParentDirs(descFile)) {
                throw new IOException("创建目标文件所在的目录失败!");
            }
        }

        // 准备复制文件
        try (
            InputStream ins = new FileInputStream(srcFile);
            OutputStream outs = new FileOutputStream(descFile);
        ) {
            copy(ins, outs);
            return true;
        } catch (Exception e) {
            LOGGER.warn("复制文件失败：", e);
            return false;
        }
    }

    /**
     * 复制整个目录的内容，如果目标目录存在，则不覆盖
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @return 如果复制成功返回true ，否则返回false
     * @throws IOException the io exception
     */
    public static boolean copyDirectory(String srcDirName, String descDirName) throws IOException {
        return copyDirectoryCover(srcDirName, descDirName, false);
    }

    /**
     * 复制整个目录的内容
     *
     * @param srcDirName  源目录名
     * @param descDirName 目标目录名
     * @param coverlay    如果目标目录存在，是否覆盖
     * @return 如果复制成功返回true ，否则返回false
     * @throws IOException the io exception
     */
    public static boolean copyDirectoryCover(String srcDirName,
                                             String descDirName, boolean coverlay) throws IOException {
        File srcDir = new File(srcDirName);
        // 判断源目录是否存在
        if (!srcDir.exists()) {
            throw new FileNotFoundException(String.format("复制目录失败，源目录 %s 不存在!", srcDirName));
        } else if (!srcDir.isDirectory()) { // 判断源目录是否是目录
            throw new FileNotFoundException(String.format("复制目录失败，%s 不是一个目录!", srcDirName));
        }
        // 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        // 如果目标文件夹存在
        if (descDir.exists()) {
            if (coverlay) {
                // 允许覆盖目标目录
                if (delFile(descDirNames)) {
                    throw new IOException(String.format("删除目录 %s 失败!", descDirNames));
                }
            } else {
                throw new FileAlreadyExistsException(String.format("目标目录复制失败，目标目录 %s 已存在!", descDirNames));
            }
        } else {
            if (!descDir.mkdirs()) {
                throw new IOException("创建目标目录失败!");
            }
        }

        return copyFolder(srcDir, descDirName);
    }

    /**
     * 复制整个目录的内容
     *
     * @param folder      源目录
     * @param descDirName 目的地址
     * @return boolean boolean
     * @throws IOException the io exception
     */
    public static boolean copyFolder(File folder, String descDirName) throws IOException {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                // 如果是一个单个文件，则直接复制
                if ((file.isFile() && !copyFile(file.getAbsolutePath(), descDirName + file.getName()))
                    || (file.isDirectory() && !copyDirectory(file.getAbsolutePath(), descDirName + file.getName()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Stream copy, use default buf_size.
     *
     * @param is InputStream
     * @param os OutputStream
     * @throws IOException IO异常
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        copy(is, os, BUF_SIZE);
    }

    /**
     * copy data from reader to writer.
     *
     * @param reader Reader
     * @param writer Writer
     * @throws IOException IO异常
     */
    public static void copy(Reader reader, Writer writer) throws IOException {
        char[] buf = new char[BUF_SIZE];
        int len;
        try {
            while ((len = reader.read(buf)) != -1) {
                writer.write(buf, 0, len);
            }
        } finally {
            close(reader);
        }
    }

    /**
     * Stream copy.
     *
     * @param is      InputStream
     * @param os      OutputStream
     * @param bufSize int
     * @throws IOException IO异常
     */
    public static void copy(InputStream is, OutputStream os, int bufSize) throws IOException {
        byte[] buf = new byte[bufSize];
        int c;
        try {
            while ((c = is.read(buf)) != -1) {
                os.write(buf, 0, c);
            }
        } finally {
            close(is);
        }
    }

    /**
     * 将目标的文件或目录移动到新位置上.
     *
     * @param srcFileName  待复制的文件名
     * @param descFileName 目标文件名
     * @return 如果移动成功 ，则返回true，否是返回false
     * @throws IOException the io exception
     */
    public static boolean move(String srcFileName, String descFileName) throws IOException {
        return copy(srcFileName, descFileName) && delFile(srcFileName);
    }

    /**
     * 删除文件，可以删除单个文件或文件夹
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功 ，则返回true，否是返回false
     */
    public static boolean delFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            LOGGER.debug(String.format(FILE_NOT_FIND, fileName));
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除的文件名
     * @return 如果删除成功 ，则返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LOGGER.debug("删除文件 {} 成功!", fileName);
                return true;
            } else {
                LOGGER.debug("删除文件 {} 失败!", fileName);
                return false;
            }
        } else {
            LOGGER.debug(String.format(FILE_NOT_FIND, fileName));
            return true;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dirName 被删除的目录所在的文件路径
     * @return 如果目录删除成功 ，则返回true，否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        String dirNames = dirName;
        if (!dirNames.endsWith(File.separator)) {
            dirNames = dirNames + File.separator;
        }
        File dirFile = new File(dirNames);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            LOGGER.debug("{} 目录不存在!", dirNames);
            return true;
        }
        if (clearFolder(dirFile) && dirFile.delete()) {
            LOGGER.debug("删除目录 {} 成功!", dirName);
            return true;
        } else {
            LOGGER.debug("删除目录 {} 失败!", dirName);
            return false;
        }

    }

    /**
     * 清空一个目录.
     *
     * @param dirName 需要清除的目录.如果该参数实际上是一个file,不处理,返回true,
     * @return 是否清除成功 boolean
     */
    public static boolean clearFolder(String dirName) {
        File file = new File(dirName);
        return file.isFile() || clearFolder(file);
    }

    /**
     * 清空目录
     *
     * @param folder 目标目录
     * @return 是否清除成功
     */
    private static boolean clearFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if ((file.isFile() && !deleteFile(file.getAbsolutePath()))
                    || (file.isDirectory() && !deleteDirectory(file.getAbsolutePath()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 创建单个文件
     *
     * @param descFileName 文件名，包含路径
     * @return 如果创建成功 ，则返回true，否则返回false
     * @throws IOException the io exception
     */
    public static boolean createFile(String descFileName) throws IOException {
        File file = new File(descFileName);
        if (file.exists()) {
            throw new FileAlreadyExistsException(String.format("文件 %s 已存在!", descFileName));
        }
        if (descFileName.endsWith(File.separator)) {
            throw new IOException(String.format("%s 为目录，不能创建目录!", descFileName));
        }
        if (!file.getParentFile().exists() && !mkParentDirs(file)) {
            throw new IOException("创建文件所在的目录失败!");
        }

        // 创建文件
        try {
            if (file.createNewFile()) {
                LOGGER.debug("{} 文件创建成功!", descFileName);
                return true;
            } else {
                LOGGER.debug("{} 文件创建失败!", descFileName);
                return false;
            }
        } catch (IOException e) {
            LOGGER.debug(String.format("%s 文件创建失败!", descFileName), e);
            return false;
        }
    }

    /**
     * 创建目录
     *
     * @param descDirName 目录名,包含路径
     * @return 如果创建成功 ，则返回true，否则返回false
     * @throws IOException the io exception
     */
    public static boolean createDirectory(String descDirName) throws IOException {
        String descDirNames = descDirName;
        if (!descDirNames.endsWith(File.separator)) {
            descDirNames = descDirNames + File.separator;
        }
        File descDir = new File(descDirNames);
        if (descDir.exists()) {
            throw new FileAlreadyExistsException(String.format("目录 %s 已存在!", descDirNames));
        }
        // 创建目录
        if (descDir.mkdirs()) {
            LOGGER.debug("目录 {} 创建成功!", descDirNames);
            return true;
        } else {
            LOGGER.debug("目录 {} 创建失败!", descDirNames);
            return false;
        }

    }

    /**
     * 从指定Reader中读取数据字符串.
     *
     * @param reader Reader
     * @return String string
     * @throws IOException IO异常
     */
    public static String read(Reader reader) throws IOException {
        CharArrayWriter writer = new CharArrayWriter();
        copy(reader, writer);
        return writer.toString();
    }

    /**
     * 保存一个数据到指定文件中.
     *
     * @param file 文件
     * @param data 内容
     * @throws IOException IO异常
     */
    public static void saveData(File file, String data) throws IOException {
        if (!file.getParentFile().exists() && !mkParentDirs(file)) {
            return;
        }
        saveData(new FileOutputStream(file), data);
    }

    /**
     * 创建父目录
     *
     * @param file 文件
     * @return boolean
     */
    private static boolean mkParentDirs(File file) {
        return file.getParentFile().mkdirs();
    }

    /**
     * 将数据保存到指定位置上.
     *
     * @param file   文件
     * @param data   内容
     * @param append 是否追加
     * @throws IOException IO异常
     */
    public static void saveData(String file, String data, Boolean append) throws IOException {
        saveData(new File(file), data, append);
    }

    /**
     * 保存一个数据到指定文件中
     *
     * @param file   文件
     * @param data   内容
     * @param append 是否追加
     * @throws IOException IO异常
     */
    public static void saveData(File file, String data, Boolean append) throws IOException {
        if (!file.getParentFile().exists() && !mkParentDirs(file)) {
            return;
        }
        saveData(new FileOutputStream(file, append), data);
    }

    /**
     * 保存bytes到一个输出流中并且关闭它
     *
     * @param os   输出流
     * @param data 内容
     * @throws IOException IO异常
     */
    public static void saveData(OutputStream os, byte[] data) throws IOException {
        try {
            os.write(data);
        } finally {
            close(os);
        }
    }

    /**
     * 保存String到指定输出流中.
     *
     * @param os   输出流
     * @param data 内容
     * @throws IOException IO异常
     */
    public static void saveData(OutputStream os, String data) throws IOException {

        try (
            BufferedOutputStream bos = new BufferedOutputStream(os, BUF_SIZE);
        ) {
            byte[] bs = data.getBytes(DEFAULT_ENCODING);
            bos.write(bs);
        }
    }

    /**
     * 将数据保存到指定位置上.
     *
     * @param file 文件路径
     * @param data 内容
     * @throws IOException IO异常
     */
    public static void saveData(String file, String data) throws IOException {
        saveData(new File(file), data);
    }

    /**
     * 修复路径，将 \\ 或 / 等替换为 File.separator
     *
     * @param path 路径
     * @return 路径 string
     */
    public static String path(String path) {
        String p = StringHelper.replace(path, "\\", "/");
        p = StringHelper.join(StringHelper.split(p, "/"), "/");
        if (!StringHelper.startsWithAny(p, "/") && StringHelper.startsWithAny(path, "\\", "/")) {
            p += "/";
        }
        if (!StringHelper.endsWithAny(p, "/") && StringHelper.endsWithAny(path, "\\", "/")) {
            p = p + "/";
        }
        return p;
    }

    /**
     * 关闭流.
     *
     * @param closeable 流
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOGGER.error("数据流关闭失败.", e);
            }
        }
    }

    /**
     * 增加文件结尾/
     *
     * @param name 文件路径
     * @return 文件路径 string
     */
    public static String addEndSlash(String name) {
        return StringHelper.isEmpty(name) || name.endsWith("/") ? name : name + "/";
    }

    /**
     * 移除文件结尾/
     *
     * @param name 文件路径
     * @return 文件路径 string
     */
    public static String clearEndSlash(String name) {
        return StringHelper.isEmpty(name) || !name.endsWith("/") ? name : name.substring(0, name.length() - 1);
    }

}

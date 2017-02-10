package cn.zhangxd.platform.common.upload.util;

import cn.zhangxd.platform.common.upload.FileOperator;
import cn.zhangxd.platform.common.utils.RandomHelper;
import cn.zhangxd.platform.common.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件管理助手类.
 * 提供文件的保存，获取，删除等与文件管理相关的方法.
 * 为了实现统一的文件处理方案，所有要交由系统保存的文件均通过此方式处理.
 *
 * @author zhangxd
 */
public class FileManager {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);

    /**
     * 文件操作类
     */
    private FileOperator fileOperator;

    /**
     * 删除文件,包括数据库中的记录和实际文件.
     *
     * @param filePath 文件路径
     */
    public void delete(String filePath) {

        // 调用文件存储器删除文件.
        getFileOperator().deleteFile(filePath);
    }

    /**
     * 删除文件集合,包括数据库中的记录和实际文件.
     *
     * @param filePathList 文件路径列表
     */
    public void deleteList(final List<String> filePathList) {
        if (filePathList == null || filePathList.isEmpty()) {
            throw new IllegalArgumentException("Parameter filePathList must not null");
        }

        if (filePathList.size() == 1) {
            delete(filePathList.get(0));
            return;
        }

        for (String path : filePathList) {
            // 调用文件存储器删除文件.
            getFileOperator().deleteFile(path);
        }
    }

    /**
     * 将一个"未保存"的文件保存,返回保存后的信息.
     *
     * @param ufi 未保存文件对象
     * @return 文件对象
     */
    public FileIndex save(FileIndex ufi) {
        FileIndex[] fs = saves(ufi);
        return fs[0];
    }

    /**
     * 保存文件到文件管理系统.
     *
     * @param ufis 未保存文件对象
     * @return 文件对象
     */
    public FileIndex[] saves(FileIndex... ufis) {

        List<FileIndex> arr = new ArrayList<>();
        for (FileIndex ufi : ufis) {

            String trueName = Objects.toString(ufi.getTrueName());

            if (StringHelper.isEmpty(trueName)) {
                continue;
            }

            String id = RandomHelper.uuid();

            ufi.setFileId(id);

            String folderPath = ufi.getModule() + "/" + id.substring(0, 2);
            String suffix = "";
            int pos = trueName.lastIndexOf('.');
            if (pos > -1) {
                suffix = trueName.substring(pos + 1);
            }
            String saveFile = id + (StringHelper.isNotEmpty(suffix) ? ("." + suffix) : "");
            String filePath = folderPath + "/" + saveFile;
            ufi.setPath(filePath);

            File upFile = ufi.getUpFile();

            getFileOperator().saveFile(upFile, filePath);
            if (!upFile.delete()) {
                LOGGER.warn("上传临时文件删除失败.");
            }

            arr.add(ufi);
        }
        return arr.toArray(new FileIndex[0]);
    }


    /**
     * 获得系统当前配置的文件保存方式.
     * 系统当前支持的文件获取方式有：硬盘(相对或绝对路径)，http.
     * 硬盘方式支持 局域网通过共享文件夹的方式.
     *
     * @return 文件操作类
     */
    public FileOperator getFileOperator() {
        if (fileOperator == null) {
            throw new NullPointerException("File Operator cannot be null.");
        }
        return fileOperator;
    }

    /**
     * 获得文件的访问路径.
     *
     * @param realPath 文件路径
     * @return 文件访问路径
     */
    public String getFileUrlByRealPath(String realPath) {
        return getFileOperator().getFileUrl(realPath);
    }


    /**
     * 设置(修改)文件保存方式.
     *
     * @param fileOperator 文件操作类
     */
    public void setFileOperator(FileOperator fileOperator) {
        this.fileOperator = fileOperator;
    }

}

package cn.zhangxd.platform.common.upload;


import cn.zhangxd.platform.common.utils.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 文件硬盘操作
 *
 * @author zhangxd
 */
public class DiskFileOperator extends AbstractFileOperator implements FileOperator {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DiskFileOperator.class);

    /**
     * 文件目录
     */
    private String workFolderName;

    @Override
    public void deleteFile(String realName) {
        FileHelper.delFile(FileHelper.addEndSlash(workFolderName) + realName);
    }

    /**
     * 设置文件目录
     *
     * @return the work folder name
     */
    public String getWorkFolderName() {
        return workFolderName;
    }

    @Override
    public void saveFile(File file, String realName) {
        try {
            FileHelper.copy(file.getAbsolutePath(), FileHelper.addEndSlash(workFolderName) + realName);
        } catch (IOException e) {
            LOGGER.error(String.format("文件 %s 复制失败", realName), e);
        }
    }

    /**
     * 设置存放文件的目录名称,可以是相对于当前appPath的目录(例如../files, d:/tmp, /tmp).
     *
     * @param workFolderName 文件存放目录
     */
    public void setWorkFolderName(String workFolderName) {
        this.workFolderName = workFolderName;
    }

}

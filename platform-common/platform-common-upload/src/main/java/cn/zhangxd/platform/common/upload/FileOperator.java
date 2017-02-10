package cn.zhangxd.platform.common.upload;

import java.io.File;

/**
 * 文件处理器.
 *
 * @author zhangxd
 */
public interface FileOperator {

    /**
     * 删除一个文件
     *
     * @param realName 相对路径名.
     */
    void deleteFile(String realName);

    /**
     * @param file     文件
     * @param realName 相对路径名(访问时候使用,也是存放在数据库中的字段)
     */
    void saveFile(File file, String realName);

    /**
     * 获得一个文件的web访问url
     *
     * @param realPath 文件的存放路径,在数据库中保存该信息.
     * @return 文件访问路径
     */
    String getFileUrl(String realPath);

}



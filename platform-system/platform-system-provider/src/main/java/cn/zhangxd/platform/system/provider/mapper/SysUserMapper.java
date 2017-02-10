package cn.zhangxd.platform.system.provider.mapper;


import cn.zhangxd.platform.common.service.dao.CrudDao;
import cn.zhangxd.platform.system.api.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户DAO接口
 *
 * @author zhangxd
 */
@Mapper
public interface SysUserMapper extends CrudDao<SysUser> {

    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return SysUser by login name
     */
    SysUser getByLoginName(String loginName);

    /**
     * 更新用户密码
     *
     * @param user the user
     * @return the int
     */
    int updatePasswordById(SysUser user);

    /**
     * 删除用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int deleteUserRole(SysUser user);

    /**
     * 插入用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int insertUserRole(SysUser user);

    /**
     * 保存用户信息
     *
     * @param user the user
     */
    void updateInfo(SysUser user);
}

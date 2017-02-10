package cn.zhangxd.platform.system.provider.mapper;


import cn.zhangxd.platform.common.service.dao.CrudDao;
import cn.zhangxd.platform.system.api.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单DAO接口
 *
 * @author zhangxd
 */
@Mapper
public interface SysMenuMapper extends CrudDao<SysMenu> {

    /**
     * 根据用户查询菜单
     *
     * @param userId the user id
     * @return the list
     */
    List<SysMenu> findListByUserId(String userId);

    /**
     * 根据角色查询菜单
     *
     * @param roleId the role id
     * @return the list
     */
    List<SysMenu> findListByRoleId(String roleId);

    /**
     * 根据父ID查询菜单
     *
     * @param menu the menu
     * @return the list
     */
    List<SysMenu> findByParentIdsLike(SysMenu menu);

    /**
     * 更新父ID
     *
     * @param menu the menu
     * @return the int
     */
    int updateParentIds(SysMenu menu);

}

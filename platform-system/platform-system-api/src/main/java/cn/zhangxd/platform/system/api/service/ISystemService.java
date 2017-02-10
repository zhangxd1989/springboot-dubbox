package cn.zhangxd.platform.system.api.service;


import cn.zhangxd.platform.system.api.entity.SysMenu;
import com.github.pagehelper.PageInfo;
import cn.zhangxd.platform.common.api.Paging;
import cn.zhangxd.platform.system.api.entity.SysRole;
import cn.zhangxd.platform.system.api.entity.SysUser;

import java.util.List;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author zhangxd
 */
public interface ISystemService {

    /**
     * 根据登录名获取用户
     *
     * @param loginName 登录名
     * @return SysUser user by login name
     */
    SysUser getUserByLoginName(String loginName);

    /**
     * 查询用户列表
     *
     * @param page 分页信息
     * @param user 用户
     * @return 分页数据 page info
     */
    PageInfo<SysUser> findUserPage(Paging page, SysUser user);

    /**
     * 查询用户
     *
     * @param userId 用户ID
     * @return 用户 user by id
     */
    SysUser getUserById(String userId);

    /**
     * 保存用户
     *
     * @param user 用户
     * @return the sys user
     */
    SysUser saveUser(SysUser user);

    /**
     * 更新用户信息
     *
     * @param user 用户
     */
    void updateUserInfo(SysUser user);

    /**
     * 删除用户
     *
     * @param userId 用户ID
     */
    void deleteUserById(String userId);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param newPassword 密码
     */
    void updateUserPasswordById(String userId, String newPassword);


    /**
     * 查询用户导航菜单
     *
     * @param userId 用户ID
     * @return 菜单列表 menu nav
     */
    List<SysMenu> getMenuNav(String userId);

    /**
     * 查询用户菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表 menu list
     */
    List<SysMenu> getMenuList(String userId);

    /**
     * 查询用户菜单
     *
     * @param userId 用户ID
     * @return 菜单列表 menu tree
     */
    List<SysMenu> getMenuTree(String userId);

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    void deleteMenuById(String menuId);

    /**
     * 查询菜单
     *
     * @param menuId 菜单ID
     * @return 菜单 menu by id
     */
    SysMenu getMenuById(String menuId);

    /**
     * 保存菜单
     *
     * @param menu 菜单
     * @return the sys menu
     */
    SysMenu saveMenu(SysMenu menu);


    /**
     * 查询角色列表
     *
     * @param page 分页信息
     * @param role 角色
     * @return 角色 page info
     */
    PageInfo<SysRole> findRolePage(Paging page, SysRole role);

    /**
     * 获得所有角色列表
     *
     * @return 角色 list
     */
    List<SysRole> findAllRoleList();

    /**
     * 查询用户
     *
     * @param roleId 角色ID
     * @return 角色 role by id
     */
    SysRole getRoleById(String roleId);

    /**
     * 保存角色
     *
     * @param role 角色
     * @return the sys role
     */
    SysRole saveRole(SysRole role);

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     */
    void deleteRoleById(String roleId);

}

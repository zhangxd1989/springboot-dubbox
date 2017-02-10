package cn.zhangxd.platform.admin.web.security.model;


import cn.zhangxd.platform.common.utils.StringHelper;
import cn.zhangxd.platform.system.api.entity.SysMenu;
import cn.zhangxd.platform.system.api.entity.SysRole;
import cn.zhangxd.platform.system.api.entity.SysUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Auth user factory.
 *
 * @author zhangxd
 */
public final class AuthUserFactory {

    private AuthUserFactory() {
    }

    /**
     * Create auth user.
     *
     * @param user the user
     * @return the auth user
     */
    public static AuthUser create(SysUser user) {
        AuthUser authUser = new AuthUser(user.getId());
        authUser.setLoginName(user.getLoginName());
        authUser.setName(user.getName());
        authUser.setEmail(user.getEmail());
        authUser.setPhone(user.getPhone());
        authUser.setMobile(user.getMobile());
        authUser.setPassword(user.getPassword());
        authUser.setEnabled(user.getEnabled());
        authUser.setAuthorities(mapToGrantedAuthorities(user.getRoles(), user.getMenus()));
        return authUser;
    }

    /**
     * 权限转换
     *
     * @param sysRoles 角色列表
     * @param sysMenus 菜单列表
     * @return 权限列表
     */
    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(List<SysRole> sysRoles, List<SysMenu> sysMenus) {

        List<SimpleGrantedAuthority> authorities =
            sysRoles.stream().filter(SysRole::getEnabled)
                .map(sysRole -> new SimpleGrantedAuthority(sysRole.getName())).collect(Collectors.toList());

        // 添加基于Permission的权限信息
        sysMenus.stream().filter(menu -> StringHelper.isNotBlank(menu.getPermission())).forEach(menu -> {
            // 添加基于Permission的权限信息
            for (String permission : StringHelper.split(menu.getPermission(), ",")) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        });

        return authorities;
    }

}

package cn.zhangxd.platform.admin.web.controller;

import com.github.pagehelper.PageInfo;
import cn.zhangxd.platform.admin.web.common.controller.BaseController;
import cn.zhangxd.platform.common.api.Paging;
import cn.zhangxd.platform.system.api.entity.SysRole;
import cn.zhangxd.platform.system.api.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * The type Sys role controller.
 *
 * @author zhangxd
 */
@Validated
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
    /**
     * 系统角色服务
     */
    @Autowired
    private ISystemService systemService;

    /**
     * List page info.
     *
     * @param role the role
     * @param page the page
     * @return the page info
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/list")
    public PageInfo<SysRole> list(SysRole role, Paging page) {

        return systemService.findRolePage(page, role);
    }

    /**
     * All list.
     *
     * @return the list
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/all")
    public List<SysRole> all() {
        return systemService.findAllRoleList();
    }

    /**
     * Gets role.
     *
     * @param roleId the role id
     * @return the role
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping(value = "/{roleId}")
    public SysRole getRole(@PathVariable("roleId") String roleId) {

        return systemService.getRoleById(roleId);

    }

    /**
     * Save role sys role.
     *
     * @param role the role
     * @return the sys role
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping(value = "")
    public SysRole saveRole(@Valid @RequestBody SysRole role) {

        return systemService.saveRole(role);
    }

    /**
     * Delete response entity.
     *
     * @param roleId the role id
     * @return the response entity
     */
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @DeleteMapping(value = "/{roleId}")
    public ResponseEntity delete(@PathVariable("roleId") String roleId) {

        systemService.deleteRoleById(roleId);

        return new ResponseEntity(HttpStatus.OK);
    }

}

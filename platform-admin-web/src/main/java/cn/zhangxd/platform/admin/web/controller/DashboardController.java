package cn.zhangxd.platform.admin.web.controller;

import cn.zhangxd.platform.admin.web.common.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Dashboard controller.
 *
 * @author zhangxd
 */
@Validated
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    /**
     * Get map.
     *
     * @return the map
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "")
    public Map<String, Object> get() {
        return new HashMap<>();
    }

}

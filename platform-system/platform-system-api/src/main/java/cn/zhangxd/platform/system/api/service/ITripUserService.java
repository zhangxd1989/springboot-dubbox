package cn.zhangxd.platform.system.api.service;


import com.github.pagehelper.PageInfo;
import cn.zhangxd.platform.common.api.Paging;
import cn.zhangxd.platform.system.api.entity.TripUser;

import java.util.Map;

/**
 * 用户接口
 *
 * @author zhangxd
 */
public interface ITripUserService {

    /**
     * 查询用户列表
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 用户 page info
     */
    PageInfo<TripUser> queryPage(Paging page, Map<String, Object> query);

    /**
     * 通过id查询用户信息
     *
     * @param id 用户id
     * @return 用户信息 trip user
     */
    TripUser get(String id);

    /**
     * 通过手机号查询用户信息
     *
     * @param mobile 手机号
     * @return 用户信息 by mobile
     */
    TripUser getByMobile(String mobile);

    /**
     * 保存用户
     *
     * @param entity 用户信息
     */
    void updateInfo(TripUser entity);

    /**
     * 注册用户
     *
     * @param mobile   用户手机
     * @param password 密码
     */
    void registryUser(String mobile, String password);

    /**
     * 更新用户密码
     *
     * @param mobile   用户手机号码
     * @param password 密码
     */
    void updatePasswordByMobile(String mobile, String password);

    /**
     * 更新用户头像
     *
     * @param userId 用户ID
     * @param photo  头像
     */
    void updatePhotoByUserId(String userId, String photo);

}

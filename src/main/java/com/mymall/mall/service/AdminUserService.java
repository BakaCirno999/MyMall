package com.mymall.mall.service;

import com.mymall.mall.entity.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface AdminUserService {

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);

    /**
     * 获取用户信息
     *
     * @param adminUserId
     * @return
     */
    AdminUser getUserDetailById(Long adminUserId);

    /**
     * 修改当前登录用户的密码
     *
     * @param adminUserId
     * @param originalPassword
     * @param newPassword
     * @return
     */
    Boolean updatePassword(Long adminUserId, String originalPassword, String newPassword);

    /**
     * 修改当前登录用户的名称信息
     *
     * @param adminUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Long adminUserId, String loginUserName, String nickName);

    /**
     * 登出接口
     * @param adminUserId
     * @return
     */
    Boolean logout(Long adminUserId);
}

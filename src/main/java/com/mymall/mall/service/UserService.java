package com.mymall.mall.service;

import com.mymall.mall.controller.mall.param.MallUserUpdateParam;
import com.mymall.mall.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mymall.mall.util.PageQueryUtil;
import com.mymall.mall.util.PageResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface UserService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getUsersPage(PageQueryUtil pageUtil);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Long[] ids, int lockStatus);

    /**
     * 登录
     *
     * @param loginName
     * @param passwordMd5
     * @return
     */
    String login(String loginName, String passwordMd5);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId);
}

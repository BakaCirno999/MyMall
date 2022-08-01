package com.mymall.mall.service.impl;

import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.entity.AdminUser;
import com.mymall.mall.entity.AdminUserToken;
import com.mymall.mall.mapper.AdminUserMapper;
import com.mymall.mall.mapper.AdminUserTokenMapper;
import com.mymall.mall.service.AdminUserService;
import com.mymall.mall.util.NumberUtil;
import com.mymall.mall.util.SystemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private AdminUserTokenMapper adminUserTokenMapper;

    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }

    @Override
    public String login(String userName, String password) {
        AdminUser loginAdminUser = adminUserMapper.login(userName, password);
        if (loginAdminUser != null) {
            //登录后即执行修改token的操作
            String token = getNewToken(System.currentTimeMillis() + "", loginAdminUser.getAdminUserId());
            AdminUserToken adminUserToken = adminUserTokenMapper.selectById(loginAdminUser.getAdminUserId());
            //当前时间
            Date now = new Date();
            //过期时间
            Date expireTime = new Date(now.getTime() + 2 * 24 * 3600 * 1000);//过期时间 48 小时
            if (adminUserToken == null) {
                adminUserToken = new AdminUserToken();
                adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                //新增一条token数据
                if (adminUserTokenMapper.insertSelective(adminUserToken) > 0) {
                    //新增成功后返回
                    return token;
                }
            } else {
                adminUserToken.setToken(token);
                adminUserToken.setUpdateTime(now);
                adminUserToken.setExpireTime(expireTime);
                //更新
                if (adminUserTokenMapper.updateByIdSelective(adminUserToken) > 0) {
                    //修改成功后返回
                    return token;
                }
            }
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }

    @Override
    public AdminUser getUserDetailById(Long adminUserId) {
        return adminUserMapper.selectById(adminUserId);
    }

    @Override
    public Boolean updatePassword(Long adminUserId, String originalPassword, String newPassword) {
        AdminUser adminUser = adminUserMapper.selectById(adminUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //比较原密码是否正确
            if (originalPassword.equals(adminUser.getLoginPassword())) {
                //设置新密码并修改
                adminUser.setLoginPassword(newPassword);
                if (adminUserMapper.updateByIdSelective(adminUser) > 0 && adminUserTokenMapper.deleteById(adminUserId) > 0) {
                    //修改成功且清空当前token则返回true
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean updateName(Long adminUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectById(adminUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新名称并修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByIdSelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean logout(Long adminUserId) {
        return adminUserTokenMapper.deleteById(adminUserId) > 0;
    }
}

package com.mymall.mall.controller.mall;

import com.mymall.mall.common.Constants;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.config.annotation.TokenToMallUser;
import com.mymall.mall.controller.mall.param.MallUserLoginParam;
import com.mymall.mall.controller.mall.param.MallUserRegisterParam;
import com.mymall.mall.controller.mall.param.MallUserUpdateParam;
import com.mymall.mall.controller.mall.vo.UserVO;
import com.mymall.mall.entity.User;
import com.mymall.mall.service.UserService;
import com.mymall.mall.util.BeanUtil;
import com.mymall.mall.util.NumberUtil;
import com.mymall.mall.util.Result;
import com.mymall.mall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "2.商城用户操作相关接口")
@RequestMapping("/api/v1")
public class MallPersonalController {

    @Resource
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MallPersonalController.class);

    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口", notes = "返回token")
    public Result<String> login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam) {
        if (!NumberUtil.isPhone(mallUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String loginResult = userService.login(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());

        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出接口", notes = "清除token")
    public Result<String> logout(@TokenToMallUser User loginMallUser) {
        Boolean logoutResult = userService.logout(loginMallUser.getUserId());

        logger.info("logout api,loginMallUser={}", loginMallUser.getUserId());

        //登出成功
        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        //登出失败
        return ResultGenerator.genFailResult("logout error");
    }


    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册", notes = "")
    public Result register(@RequestBody @Valid MallUserRegisterParam mallUserRegisterParam) {
        if (!NumberUtil.isPhone(mallUserRegisterParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = userService.register(mallUserRegisterParam.getLoginName(), mallUserRegisterParam.getPassword());

        logger.info("register api,loginName={},loginResult={}", mallUserRegisterParam.getLoginName(), registerResult);

        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Result updateInfo(@RequestBody @ApiParam("用户信息") MallUserUpdateParam mallUserUpdateParam, @TokenToMallUser User loginMallUser) {
        Boolean flag = userService.updateUserInfo(mallUserUpdateParam, loginMallUser.getUserId());
        if (flag) {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        } else {
            //返回失败
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        }
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result<UserVO> getUserDetail(@TokenToMallUser User loginMallUser) {
        //已登录则直接返回
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(loginMallUser, userVO);
        return ResultGenerator.genSuccessResult(userVO);
    }
}

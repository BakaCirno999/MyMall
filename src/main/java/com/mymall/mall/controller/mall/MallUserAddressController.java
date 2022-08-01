package com.mymall.mall.controller.mall;

import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.config.annotation.TokenToMallUser;
import com.mymall.mall.controller.mall.param.SaveUserAddressParam;
import com.mymall.mall.controller.mall.param.UpdateUserAddressParam;
import com.mymall.mall.controller.mall.vo.UserAddressVO;
import com.mymall.mall.entity.User;
import com.mymall.mall.entity.UserAddress;
import com.mymall.mall.service.UserAddressService;
import com.mymall.mall.util.BeanUtil;
import com.mymall.mall.util.Result;
import com.mymall.mall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "6.商城个人地址相关接口")
@RequestMapping("/api/v1")
public class MallUserAddressController {

    @Resource
    private UserAddressService userAddressService;

    @GetMapping("/address")
    @ApiOperation(value = "我的收货地址列表", notes = "")
    public Result<List<UserAddressVO>> addressList(@TokenToMallUser User loginUser) {
        return ResultGenerator.genSuccessResult(userAddressService.getMyAddresses(loginUser.getUserId()));
    }

    @PostMapping("/address")
    @ApiOperation(value = "添加地址", notes = "")
    public Result<Boolean> saveUserAddress(@RequestBody SaveUserAddressParam saveUserAddressParam,
                                           @TokenToMallUser User loginUser) {
        UserAddress userAddress = new UserAddress();
        BeanUtil.copyProperties(saveUserAddressParam, userAddress);
        userAddress.setUserId(loginUser.getUserId());
        Boolean saveResult = userAddressService.saveUserAddress(userAddress);
        //添加成功
        if (saveResult) {
            return ResultGenerator.genSuccessResult();
        }
        //添加失败
        return ResultGenerator.genFailResult("添加失败");
    }

    @PutMapping("/address")
    @ApiOperation(value = "修改地址", notes = "")
    public Result<Boolean> updateUserAddress(@RequestBody UpdateUserAddressParam updateUserAddressParam,
                                             @TokenToMallUser User loginUser) {
        UserAddress userAddressById = userAddressService.getUserAddressById(updateUserAddressParam.getAddressId());
        if (!loginUser.getUserId().equals(userAddressById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        UserAddress userAddress = new UserAddress();
        BeanUtil.copyProperties(updateUserAddressParam, userAddress);
        userAddress.setUserId(loginUser.getUserId());
        Boolean updateResult = userAddressService.updateUserAddress(userAddress);
        //修改成功
        if (updateResult) {
            return ResultGenerator.genSuccessResult();
        }
        //修改失败
        return ResultGenerator.genFailResult("修改失败");
    }

    @GetMapping("/address/{addressId}")
    @ApiOperation(value = "获取收货地址详情", notes = "传参为地址id")
    public Result<UserAddressVO> getUserAddress(@PathVariable("addressId") Long addressId,
                                                @TokenToMallUser User loginUser) {
        UserAddress userAddress = userAddressService.getUserAddressById(addressId);
        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtil.copyProperties(userAddress, userAddressVO);
        if (!loginUser.getUserId().equals(userAddress.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        return ResultGenerator.genSuccessResult(userAddressVO);
    }

    @GetMapping("/address/default")
    @ApiOperation(value = "获取默认收货地址", notes = "无传参")
    public Result getDefaultUserAddress(@TokenToMallUser User loginUser) {
        UserAddress userAddress = userAddressService.getMyDefaultAddressByUserId(loginUser.getUserId());
        return ResultGenerator.genSuccessResult(userAddress);
    }

    @DeleteMapping("/address/{addressId}")
    @ApiOperation(value = "删除收货地址", notes = "传参为地址id")
    public Result deleteAddress(@PathVariable("addressId") Long addressId,
                                @TokenToMallUser User loginUser) {
        UserAddress userAddressById = userAddressService.getUserAddressById(addressId);
        if (!loginUser.getUserId().equals(userAddressById.getUserId())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = userAddressService.deleteById(addressId);
        //删除成功
        if (deleteResult) {
            return ResultGenerator.genSuccessResult();
        }
        //删除失败
        return ResultGenerator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }
}

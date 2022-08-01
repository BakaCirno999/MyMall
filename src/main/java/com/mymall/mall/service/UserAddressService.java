package com.mymall.mall.service;

import com.mymall.mall.controller.mall.vo.UserAddressVO;
import com.mymall.mall.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 收货地址表 服务类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface UserAddressService {

    /**
     * 获取收货地址详情
     *
     * @param addressId
     * @return
     */
    UserAddress getUserAddressById(Long addressId);

    /**
     * 获取我的收货地址
     *
     * @param userId
     * @return
     */
    List<UserAddressVO> getMyAddresses(Long userId);

    /**
     * 保存收货地址
     *
     * @param userAddress
     * @return
     */
    Boolean saveUserAddress(UserAddress userAddress);

    /**
     * 修改收货地址
     *
     * @param userAddress
     * @return
     */
    Boolean updateUserAddress(UserAddress userAddress);

    /**
     * 获取我的默认收货地址
     *
     * @param userId
     * @return
     */
    UserAddress getMyDefaultAddressByUserId(Long userId);

    /**
     * 删除收货地址
     *
     * @param addressId
     * @return
     */
    Boolean deleteById(Long addressId);
}

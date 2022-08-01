package com.mymall.mall.mapper;

import com.mymall.mall.entity.UserAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 收货地址表 Mapper 接口
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
public interface UserAddressMapper {

    UserAddress selectById(Long addressId);

    List<UserAddress> findMyAddressList(Long userId);

    UserAddress getMyDefaultAddress(Long userId);

    int updateByIdSelective(UserAddress defaultAddress);

    int insertSelective(UserAddress userAddress);

    int deleteById(Long addressId);
}

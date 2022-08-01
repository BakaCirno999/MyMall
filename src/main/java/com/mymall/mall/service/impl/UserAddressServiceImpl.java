package com.mymall.mall.service.impl;

import com.mymall.mall.common.MallException;
import com.mymall.mall.common.ServiceResultEnum;
import com.mymall.mall.controller.mall.vo.UserAddressVO;
import com.mymall.mall.entity.UserAddress;
import com.mymall.mall.mapper.UserAddressMapper;
import com.mymall.mall.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymall.mall.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public UserAddress getUserAddressById(Long addressId) {

        UserAddress userAddress = userAddressMapper.selectById(addressId);
        if (userAddress == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return userAddress;
    }

    @Override
    public List<UserAddressVO> getMyAddresses(Long userId) {
        List<UserAddress> myAddressList = userAddressMapper.findMyAddressList(userId);
        List<UserAddressVO> userAddressVOS = BeanUtil.copyList(myAddressList, UserAddressVO.class);
        return userAddressVOS;
    }

    @Override
    public Boolean saveUserAddress(UserAddress userAddress) {
        Date now = new Date();
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //添加默认地址，需要将原有的默认地址修改掉
            UserAddress defaultAddress = userAddressMapper.getMyDefaultAddress(userAddress.getUserId());
            if (defaultAddress != null) {
                defaultAddress.setDefaultFlag((byte) 0);
                defaultAddress.setUpdateTime(now);
                int updateResult = userAddressMapper.updateByIdSelective(defaultAddress);
                if (updateResult < 1) {
                    //未更新成功
                    MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
                }
            }
        }
        return userAddressMapper.insertSelective(userAddress) > 0;
    }

    @Override
    public Boolean updateUserAddress(UserAddress userAddress) {
        UserAddress tempAddress = getUserAddressById(userAddress.getAddressId());
        Date now = new Date();
        if (userAddress.getDefaultFlag().intValue() == 1) {
            //修改为默认地址，需要将原有的默认地址修改掉
            UserAddress defaultAddress = userAddressMapper.getMyDefaultAddress(userAddress.getUserId());
            if (defaultAddress != null && !defaultAddress.getAddressId().equals(tempAddress)) {
                //存在默认地址且默认地址并不是当前修改的地址
                defaultAddress.setDefaultFlag((byte) 0);
                defaultAddress.setUpdateTime(now);
                int updateResult = userAddressMapper.updateByIdSelective(defaultAddress);
                if (updateResult < 1) {
                    //未更新成功
                    MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
                }
            }
        }
        userAddress.setUpdateTime(now);
        return userAddressMapper.updateByIdSelective(userAddress) > 0;
    }

    @Override
    public UserAddress getMyDefaultAddressByUserId(Long userId) {
        return userAddressMapper.getMyDefaultAddress(userId);
    }

    @Override
    public Boolean deleteById(Long addressId) {
        return userAddressMapper.deleteById(addressId) > 0;
    }
}

package com.mymall.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserAddress对象", description="收货地址表")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "address_id", type = IdType.AUTO)
    private Long addressId;

    @ApiModelProperty(value = "用户主键id")
    private Long userId;

    @ApiModelProperty(value = "收货人姓名")
    private String userName;

    @ApiModelProperty(value = "收货人手机号")
    private String userPhone;

    @ApiModelProperty(value = "是否为默认 0-非默认 1-是默认")
    private Byte defaultFlag;

    @ApiModelProperty(value = "省")
    private String provinceName;

    @ApiModelProperty(value = "城")
    private String cityName;

    @ApiModelProperty(value = "区")
    private String regionName;

    @ApiModelProperty(value = "收件详细地址(街道/楼宇/单元)")
    private String detailAddress;

    @ApiModelProperty(value = "删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}

package com.mymall.mall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 订单收货地址关联表
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OrderAddress对象", description="订单收货地址关联表")
public class OrderAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @ApiModelProperty(value = "收货人姓名")
    private String userName;

    @ApiModelProperty(value = "收货人手机号")
    private String userPhone;

    @ApiModelProperty(value = "省")
    private String provinceName;

    @ApiModelProperty(value = "城")
    private String cityName;

    @ApiModelProperty(value = "区")
    private String regionName;

    @ApiModelProperty(value = "收件详细地址(街道/楼宇/单元)")
    private String detailAddress;


}

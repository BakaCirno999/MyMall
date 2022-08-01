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
 * 
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单表主键id")
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "用户主键id")
    private Long userId;

    @ApiModelProperty(value = "订单总价")
    private Integer totalPrice;

    @ApiModelProperty(value = "支付状态:0.未支付,1.支付成功,-1:支付失败")
    private Byte payStatus;

    @ApiModelProperty(value = "0.无 1.支付宝支付 2.微信支付")
    private Byte payType;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭")
    private Byte orderStatus;

    @ApiModelProperty(value = "订单body")
    private String extraInfo;

    @ApiModelProperty(value = "删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最新修改时间")
    private Date updateTime;


}

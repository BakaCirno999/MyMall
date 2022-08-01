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
@ApiModel(value="OrderItem对象", description="")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单关联购物项主键id")
    @TableId(value = "order_item_id", type = IdType.AUTO)
    private Long orderItemId;

    @ApiModelProperty(value = "订单主键id")
    private Long orderId;

    @ApiModelProperty(value = "关联商品id")
    private Long goodsId;

    @ApiModelProperty(value = "下单时商品的名称(订单快照)")
    private String goodsName;

    @ApiModelProperty(value = "下单时商品的主图(订单快照)")
    private String goodsCoverImg;

    @ApiModelProperty(value = "下单时商品的价格(订单快照)")
    private Integer sellingPrice;

    @ApiModelProperty(value = "数量(订单快照)")
    private Integer goodsCount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;


}

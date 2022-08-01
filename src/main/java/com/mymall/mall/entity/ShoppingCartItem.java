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
@ApiModel(value="ShoppingCartItem对象", description="")
public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购物项主键id")
    @TableId(value = "cart_item_id", type = IdType.AUTO)
    private Long cartItemId;

    @ApiModelProperty(value = "用户主键id")
    private Long userId;

    @ApiModelProperty(value = "关联商品id")
    private Long goodsId;

    @ApiModelProperty(value = "数量(最大为5)")
    private Integer goodsCount;

    @ApiModelProperty(value = "删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最新修改时间")
    private Date updateTime;


}

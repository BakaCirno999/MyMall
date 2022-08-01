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
@ApiModel(value="Carousel对象", description="")
public class Carousel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "首页轮播图主键id")
    @TableId(value = "carousel_id", type = IdType.AUTO)
    private Integer carouselId;

    @ApiModelProperty(value = "轮播图")
    private String carouselUrl;

    @ApiModelProperty(value = "点击后的跳转地址(默认不跳转)")
    private String redirectUrl;

    @ApiModelProperty(value = "排序值(字段越大越靠前)")
    private Integer carouselRank;

    @ApiModelProperty(value = "删除标识字段(0-未删除 1-已删除)")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建者id")
    private Integer createUser;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改者id")
    private Integer updateUser;


}

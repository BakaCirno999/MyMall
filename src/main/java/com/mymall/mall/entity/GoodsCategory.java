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
@ApiModel(value="GoodsCategory对象", description="")
public class GoodsCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;

    @ApiModelProperty(value = "分类级别(1-一级分类 2-二级分类 3-三级分类)")
    private Byte categoryLevel;

    @ApiModelProperty(value = "父分类id")
    private Long parentId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "排序值(字段越大越靠前)")
    private Integer categoryRank;

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

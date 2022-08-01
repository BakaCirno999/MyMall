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
@ApiModel(value="AdminUserToken对象", description="")
public class AdminUserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键id")
    @TableId(value = "admin_user_id", type = IdType.AUTO)
    private Long adminUserId;

    @ApiModelProperty(value = "token值(32位字符串)")
    private String token;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "token过期时间")
    private Date expireTime;


}

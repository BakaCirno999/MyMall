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
 * 
 * </p>
 *
 * @author ganqian
 * @since 2022-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AdminUser对象", description="")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员id")
    @TableId(value = "admin_user_id", type = IdType.AUTO)
    private Long adminUserId;

    @ApiModelProperty(value = "管理员登陆名称")
    private String loginUserName;

    @ApiModelProperty(value = "管理员登陆密码")
    private String loginPassword;

    @ApiModelProperty(value = "管理员显示昵称")
    private String nickName;

    @ApiModelProperty(value = "是否锁定 0未锁定 1已锁定无法登陆")
    private Integer locked;


}

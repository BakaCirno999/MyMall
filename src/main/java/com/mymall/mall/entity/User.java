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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户主键id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "登陆名称(默认为手机号)")
    private String loginName;

    @ApiModelProperty(value = "MD5加密后的密码")
    private String passwordMd5;

    @ApiModelProperty(value = "个性签名")
    private String introduceSign;

    @ApiModelProperty(value = "注销标识字段(0-正常 1-已注销)")
    private Integer isDeleted;

    @ApiModelProperty(value = "锁定标识字段(0-未锁定 1-已锁定)")
    private Integer lockedFlag;

    @ApiModelProperty(value = "注册时间")
    private Date createTime;


}

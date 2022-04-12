package com.yong.login.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Table(name = "admin")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseEntity {

    private static final long serialVersionUID=1L;

    @Column(name = "username")
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "名字")
    private String username;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Column(name = "avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;

    @Column(name = "tel")
    @ApiModelProperty(value = "电话")
    private String tel;

    @Column(name = "location")
    @ApiModelProperty(value = "地址")
    private String location;

    @ApiModelProperty(value = "地址")
    private LocalDateTime lastLogin;

}

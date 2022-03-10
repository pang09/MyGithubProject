package com.yong.login.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Column(name = "username")
    @ApiModelProperty(value = "名字")
    private String username;

    @Column(name = "sex")
    @ApiModelProperty(value = "性别")
    private String sex;

    @Column(name = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    @Column(name = "location")
    @ApiModelProperty(value = "地址")
    private String location;

    @Column(name = "tel")
    @ApiModelProperty(value = "电话")
    private String tel;
}

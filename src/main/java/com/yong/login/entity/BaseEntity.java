package com.yong.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@ApiModelProperty(value = "创建时间")
	private LocalDateTime created;

	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updated;

	@ApiModelProperty(value = "状态")
	private Integer statu;
}

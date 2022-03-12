package com.yong.login.entity;

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
 * @author yong
 * @since 2022-03-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Lbooktype对象", description="")
public class Lbooktype implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer btId;

    private String btName;


}

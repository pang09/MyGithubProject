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
@ApiModel(value="Lbookcase对象", description="")
public class Lbookcase implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer bcId;

    private Integer bcTypeid;


}

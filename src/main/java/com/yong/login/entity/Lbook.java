package com.yong.login.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2022-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Lbook对象", description="")
public class Lbook implements Serializable {

    private static final long serialVersionUID = 1L;

      private String bId;

    private String bName;

    @TableField("ISBN")
    private String isbn;

    private String bbkcaseid;

    private BigDecimal bPrice;

    private String bAuthor;

    @TableField("b_typeId")
    private Integer bTypeid;

    private LocalDateTime bIntime;

    private String bSynopsis;

    private Boolean bState;

    private String bPhone;


}

package org.jeecg.modules.product.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品批量编辑VO
 */
@Data
@ApiModel(value="商品批量编辑VO", description="商品批量编辑VO")
public class ProductBatchEditVO {

    /**商品ids*/
    @ApiModelProperty(value = "商品ids")
    private String productInfoIds;

    /**仓库id*/
    @ApiModelProperty(value = "仓库id")
    private String waveHouseId;
}

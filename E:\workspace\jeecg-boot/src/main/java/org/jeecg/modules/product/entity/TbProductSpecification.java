package org.jeecg.modules.product.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 商品规格表
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@ApiModel(value="tb_product_specification对象", description="商品规格表")
@Data
@TableName("tb_product_specification")
public class TbProductSpecification implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**商品id*/
    @ApiModelProperty(value = "商品id")
    private java.lang.String productId;
	/**商品单位*/
	@Excel(name = "商品单位", width = 15)
    @ApiModelProperty(value = "商品单位")
    private java.lang.String productUnit;
	/**商品规格*/
	@Excel(name = "商品规格", width = 15)
    @ApiModelProperty(value = "商品规格")
    private java.lang.String productSpecification;
	/**商品单价*/
	@Excel(name = "商品单价", width = 15)
    @ApiModelProperty(value = "商品单价")
    private java.lang.String productPrice;
	/**是否上架*/
	@Excel(name = "是否上架", width = 15, dicCode = "yn")
    @ApiModelProperty(value = "是否上架")
    private java.lang.Integer isListing;
	/**商品附图*/
	@Excel(name = "商品附图", width = 15)
    @ApiModelProperty(value = "商品附图")
    private java.lang.String otherPicture;
	/**商品库存*/
	@Excel(name = "商品库存", width = 15)
    @ApiModelProperty(value = "商品库存")
    private java.lang.Integer productInventory;
}

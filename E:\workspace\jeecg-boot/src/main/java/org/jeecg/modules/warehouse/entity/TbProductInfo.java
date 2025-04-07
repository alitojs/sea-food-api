package org.jeecg.modules.warehouse.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 商品信息表
 * @Author: jeecg-boot
 * @Date:   2025-04-07
 * @Version: V1.0
 */
@ApiModel(value="tb_product_info对象", description="商品信息表")
@Data
@TableName("tb_product_info")
public class TbProductInfo implements Serializable {
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
	/**商品名称*/
	@Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private java.lang.String productName;
	/**商品分类*/
	@Excel(name = "商品分类", width = 15, dicCode = "product_category")
    @Dict(dicCode = "product_category")
    @ApiModelProperty(value = "商品分类")
    private java.lang.Integer productCategory;
	/**是否限购*/
	@Excel(name = "是否限购", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否限购")
    private java.lang.Integer isLimited;
	/**限购数量*/
	@Excel(name = "限购数量", width = 15)
    @ApiModelProperty(value = "限购数量")
    private java.lang.Integer limitedQuantity;
	/**是否上架*/
	@Excel(name = "是否上架", width = 15, dicCode = "yn")
    @Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否上架")
    private java.lang.Integer isListing;
	/**商品主图*/
	@Excel(name = "商品主图", width = 15)
    @ApiModelProperty(value = "商品主图")
    private java.lang.String mainPicture;
	/**商家id*/
	@Excel(name = "商家id", width = 15, dictTable = "tb_merchant_info", dicText = "name", dicCode = "id")
    @Dict(dictTable = "tb_merchant_info", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "商家id")
    private java.lang.String merchantId;
}

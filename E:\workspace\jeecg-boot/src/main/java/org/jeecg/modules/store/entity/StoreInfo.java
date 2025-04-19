package org.jeecg.modules.store.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
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
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 店铺表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Data
@TableName("tb_store_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_store_info对象", description="店铺表")
public class StoreInfo implements Serializable {
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
	/**店铺名称*/
	@Excel(name = "店铺名称", width = 15)
    @ApiModelProperty(value = "店铺名称")
    private java.lang.String storeName;
	/**店铺归属者*/
	@Excel(name = "店铺归属者", width = 15)
    @ApiModelProperty(value = "店铺归属者")
    private java.lang.String storeOwner;
	/**店铺所在地*/
	@Excel(name = "店铺所在地", width = 15)
    @ApiModelProperty(value = "店铺所在地")
    private java.lang.String storeAddress;
	/**店铺图片*/
	@Excel(name = "店铺图片", width = 15)
    @ApiModelProperty(value = "店铺图片")
    private java.lang.String storePicture;
	/**店铺类型*/
	@Excel(name = "店铺类型", width = 15, dictTable = "tb_store_type_info", dicText = "store_type_name", dicCode = "id")
	@Dict(dictTable = "tb_store_type_info", dicText = "store_type_name", dicCode = "id")
    @ApiModelProperty(value = "店铺类型")
    private java.lang.String storeType;
	/**是否上线*/
	@Excel(name = "是否上线", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否上线")
    private java.lang.Integer isOnline;
	/**是否优选*/
	@Excel(name = "是否优选", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否优选")
    private java.lang.Integer isPreferred;
}

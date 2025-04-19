package org.jeecg.modules.storeType.entity;

import java.io.Serializable;
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
import java.io.UnsupportedEncodingException;

/**
 * @Description: 店铺分类表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Data
@TableName("tb_store_type_info")
@ApiModel(value="tb_store_type_info对象", description="店铺分类表")
public class StoreTypeInfo implements Serializable {
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
	/**店铺分类名称*/
	@Excel(name = "店铺分类名称", width = 15)
    @ApiModelProperty(value = "店铺分类名称")
    private java.lang.String storeTypeName;
	/**店铺分类编码*/
	@Excel(name = "店铺分类编码", width = 15)
    @ApiModelProperty(value = "店铺分类编码")
    private java.lang.String storeTypeCode;
	/**父级id*/
	@Excel(name = "父级id", width = 15, dictTable = "tb_store_type_info", dicText = "store_type_name", dicCode = "id")
	@Dict(dictTable = "tb_store_type_info", dicText = "store_type_name", dicCode = "id")
    @ApiModelProperty(value = "父级id")
    private java.lang.String parentId;
	/**父级节点*/
	@Excel(name = "父级节点", width = 15)
    @ApiModelProperty(value = "父级节点")
    private java.lang.String pid;
	/**是否有子节点*/
	@Excel(name = "是否有子节点", width = 15, dicCode = "yn")
	@Dict(dicCode = "yn")
    @ApiModelProperty(value = "是否有子节点")
    private java.lang.String hasChild;
}

package org.jeecg.modules.storeProduct.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.storeProduct.entity.StoreProductInfo;
import org.jeecg.modules.storeProduct.service.IStoreProductInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 店铺货物关联表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Api(tags="店铺货物关联表")
@RestController
@RequestMapping("/storeProduct/storeProductInfo")
@Slf4j
public class StoreProductInfoController extends JeecgController<StoreProductInfo, IStoreProductInfoService> {
	@Autowired
	private IStoreProductInfoService storeProductInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param storeProductInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "店铺货物关联表-分页列表查询")
	@ApiOperation(value="店铺货物关联表-分页列表查询", notes="店铺货物关联表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StoreProductInfo>> queryPageList(StoreProductInfo storeProductInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StoreProductInfo> queryWrapper = QueryGenerator.initQueryWrapper(storeProductInfo, req.getParameterMap());
		Page<StoreProductInfo> page = new Page<StoreProductInfo>(pageNo, pageSize);
		IPage<StoreProductInfo> pageList = storeProductInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	 /**
	  * 根据店铺id 查询已上架的商品
	  *
	  * @param storeProductInfo
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 //@AutoLog(value = "店铺货物关联表-分页列表查询")
	 @ApiOperation(value="根据店铺id 查询已上架的商品", notes="根据店铺id 查询已上架的商品")
	 @GetMapping(value = "/list")
	 public Result<IPage<ProductInfo>> queryProductListByStoreId(StoreProductInfo storeProductInfo,
		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
		@RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
		HttpServletRequest req) {
		 Page<ProductInfo> page = new Page<ProductInfo>(pageNo, pageSize);
		 IPage<ProductInfo> pageList = storeProductInfoService.queryProductListByStoreId(page, storeProductInfo, req);
		 return Result.OK(pageList);
	 }
	
	/**
	 *   添加
	 *
	 * @param storeProductInfo
	 * @return
	 */
	@AutoLog(value = "店铺货物关联表-添加")
	@ApiOperation(value="店铺货物关联表-添加", notes="店铺货物关联表-添加")
	@RequiresPermissions("storeProduct:tb_store_product_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StoreProductInfo storeProductInfo) {
		storeProductInfoService.save(storeProductInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param storeProductInfo
	 * @return
	 */
	@AutoLog(value = "店铺货物关联表-编辑")
	@ApiOperation(value="店铺货物关联表-编辑", notes="店铺货物关联表-编辑")
	@RequiresPermissions("storeProduct:tb_store_product_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StoreProductInfo storeProductInfo) {
		storeProductInfoService.updateById(storeProductInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "店铺货物关联表-通过id删除")
	@ApiOperation(value="店铺货物关联表-通过id删除", notes="店铺货物关联表-通过id删除")
	@RequiresPermissions("storeProduct:tb_store_product_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		storeProductInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "店铺货物关联表-批量删除")
	@ApiOperation(value="店铺货物关联表-批量删除", notes="店铺货物关联表-批量删除")
	@RequiresPermissions("storeProduct:tb_store_product_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.storeProductInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "店铺货物关联表-通过id查询")
	@ApiOperation(value="店铺货物关联表-通过id查询", notes="店铺货物关联表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StoreProductInfo> queryById(@RequestParam(name="id",required=true) String id) {
		StoreProductInfo storeProductInfo = storeProductInfoService.getById(id);
		if(storeProductInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(storeProductInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param storeProductInfo
    */
    @RequiresPermissions("storeProduct:tb_store_product_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StoreProductInfo storeProductInfo) {
        return super.exportXls(request, storeProductInfo, StoreProductInfo.class, "店铺货物关联表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("storeProduct:tb_store_product_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StoreProductInfo.class);
    }

}

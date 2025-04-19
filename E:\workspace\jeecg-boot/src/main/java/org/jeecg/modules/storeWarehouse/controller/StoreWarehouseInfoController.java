package org.jeecg.modules.storeWarehouse.controller;

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
import org.jeecg.modules.storeWarehouse.entity.StoreWarehouseInfo;
import org.jeecg.modules.storeWarehouse.service.IStoreWarehouseInfoService;

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
 * @Description: 店铺仓库关联表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Api(tags="店铺仓库关联表")
@RestController
@RequestMapping("/storeWarehouse/storeWarehouseInfo")
@Slf4j
public class StoreWarehouseInfoController extends JeecgController<StoreWarehouseInfo, IStoreWarehouseInfoService> {
	@Autowired
	private IStoreWarehouseInfoService storeWarehouseInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param storeWarehouseInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "店铺仓库关联表-分页列表查询")
	@ApiOperation(value="店铺仓库关联表-分页列表查询", notes="店铺仓库关联表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StoreWarehouseInfo>> queryPageList(StoreWarehouseInfo storeWarehouseInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StoreWarehouseInfo> queryWrapper = QueryGenerator.initQueryWrapper(storeWarehouseInfo, req.getParameterMap());
		Page<StoreWarehouseInfo> page = new Page<StoreWarehouseInfo>(pageNo, pageSize);
		IPage<StoreWarehouseInfo> pageList = storeWarehouseInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param storeWarehouseInfo
	 * @return
	 */
	@AutoLog(value = "店铺仓库关联表-添加")
	@ApiOperation(value="店铺仓库关联表-添加", notes="店铺仓库关联表-添加")
	@RequiresPermissions("storeWarehouse:store_warehouse_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StoreWarehouseInfo storeWarehouseInfo) {
		storeWarehouseInfoService.save(storeWarehouseInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param storeWarehouseInfo
	 * @return
	 */
	@AutoLog(value = "店铺仓库关联表-编辑")
	@ApiOperation(value="店铺仓库关联表-编辑", notes="店铺仓库关联表-编辑")
	@RequiresPermissions("storeWarehouse:store_warehouse_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StoreWarehouseInfo storeWarehouseInfo) {
		storeWarehouseInfoService.updateById(storeWarehouseInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "店铺仓库关联表-通过id删除")
	@ApiOperation(value="店铺仓库关联表-通过id删除", notes="店铺仓库关联表-通过id删除")
	@RequiresPermissions("storeWarehouse:store_warehouse_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		storeWarehouseInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "店铺仓库关联表-批量删除")
	@ApiOperation(value="店铺仓库关联表-批量删除", notes="店铺仓库关联表-批量删除")
	@RequiresPermissions("storeWarehouse:store_warehouse_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.storeWarehouseInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "店铺仓库关联表-通过id查询")
	@ApiOperation(value="店铺仓库关联表-通过id查询", notes="店铺仓库关联表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StoreWarehouseInfo> queryById(@RequestParam(name="id",required=true) String id) {
		StoreWarehouseInfo storeWarehouseInfo = storeWarehouseInfoService.getById(id);
		if(storeWarehouseInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(storeWarehouseInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param storeWarehouseInfo
    */
    @RequiresPermissions("storeWarehouse:store_warehouse_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StoreWarehouseInfo storeWarehouseInfo) {
        return super.exportXls(request, storeWarehouseInfo, StoreWarehouseInfo.class, "店铺仓库关联表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("storeWarehouse:store_warehouse_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StoreWarehouseInfo.class);
    }

}

package org.jeecg.modules.store.controller;

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
import org.jeecg.modules.store.entity.StoreInfo;
import org.jeecg.modules.store.service.IStoreInfoService;

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
 * @Description: 店铺表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Api(tags="店铺表")
@RestController
@RequestMapping("/store/storeInfo")
@Slf4j
public class StoreInfoController extends JeecgController<StoreInfo, IStoreInfoService> {
	@Autowired
	private IStoreInfoService storeInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param storeInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "店铺表-分页列表查询")
	@ApiOperation(value="店铺表-分页列表查询", notes="店铺表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<StoreInfo>> queryPageList(StoreInfo storeInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<StoreInfo> queryWrapper = QueryGenerator.initQueryWrapper(storeInfo, req.getParameterMap());
		Page<StoreInfo> page = new Page<StoreInfo>(pageNo, pageSize);
		IPage<StoreInfo> pageList = storeInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param storeInfo
	 * @return
	 */
	@AutoLog(value = "店铺表-添加")
	@ApiOperation(value="店铺表-添加", notes="店铺表-添加")
	@RequiresPermissions("store:tb_store_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody StoreInfo storeInfo) {
		storeInfoService.save(storeInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param storeInfo
	 * @return
	 */
	@AutoLog(value = "店铺表-编辑")
	@ApiOperation(value="店铺表-编辑", notes="店铺表-编辑")
	@RequiresPermissions("store:tb_store_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody StoreInfo storeInfo) {
		storeInfoService.updateById(storeInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "店铺表-通过id删除")
	@ApiOperation(value="店铺表-通过id删除", notes="店铺表-通过id删除")
	@RequiresPermissions("store:tb_store_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		storeInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "店铺表-批量删除")
	@ApiOperation(value="店铺表-批量删除", notes="店铺表-批量删除")
	@RequiresPermissions("store:tb_store_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.storeInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "店铺表-通过id查询")
	@ApiOperation(value="店铺表-通过id查询", notes="店铺表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<StoreInfo> queryById(@RequestParam(name="id",required=true) String id) {
		StoreInfo storeInfo = storeInfoService.getById(id);
		if(storeInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(storeInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param storeInfo
    */
    @RequiresPermissions("store:tb_store_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, StoreInfo storeInfo) {
        return super.exportXls(request, storeInfo, StoreInfo.class, "店铺表");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("store:tb_store_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, StoreInfo.class);
    }

}

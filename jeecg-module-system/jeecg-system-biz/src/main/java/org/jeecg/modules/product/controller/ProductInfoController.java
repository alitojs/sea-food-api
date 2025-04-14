package org.jeecg.modules.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.product.entity.vo.ProductBatchEditVO;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.product.entity.vo.ProductInfoPage;
import org.jeecg.modules.product.service.IProductInfoService;
import org.jeecg.modules.product.service.IProductSpecificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 商品信息表
 * @Author: jeecg-boot
 * @Date:   2024-08-04
 * @Version: V1.0
 */
@Api(tags="商品信息表")
@RestController
@RequestMapping("/productInfo")
@Slf4j
public class ProductInfoController {
	@Autowired
	private IProductInfoService productInfoService;
	@Autowired
	private IProductSpecificationService productSpecificationService;
	
	/**
	 * 分页列表查询
	 *
	 * @param productInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "商品信息表-分页列表查询")
	@ApiOperation(value="商品信息表-分页列表查询", notes="商品信息表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ProductInfo>> queryPageList(ProductInfo productInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ProductInfo> queryWrapper = QueryGenerator.initQueryWrapper(productInfo, req.getParameterMap());
		Page<ProductInfo> page = new Page<ProductInfo>(pageNo, pageSize);
		IPage<ProductInfo> pageList = productInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param productInfoPage
	 * @return
	 */
	@AutoLog(value = "商品信息表-添加")
	@ApiOperation(value="商品信息表-添加", notes="商品信息表-添加")
    @RequiresPermissions("product:tb_product_info:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ProductInfoPage productInfoPage) {
		ProductInfo productInfo = new ProductInfo();
		BeanUtils.copyProperties(productInfoPage, productInfo);
		productInfoService.saveMain(productInfo, productInfoPage.getProductSpecificationList());
		return Result.OK("添加成功！");
	}

	 /**
	  *   批量修改商品规格的仓库
	  * @return
	  */
	 @AutoLog(value = "商品信息表-批量修改商品规格的仓库")
	 @ApiOperation(value = "商品信息表-批量修改商品规格的仓库", notes = "商品信息表-批量修改商品规格的仓库")
	 @PostMapping(value = "/batchEditWaveHouseByProductSpecification")
	 public Result<String> batchEditWaveHouseByProductSpecification(@RequestBody ProductBatchEditVO productBatchEditVO) {
		 return productInfoService.batchEditWaveHouseByProductSpecification(productBatchEditVO);
	 }
	
	/**
	 *  编辑
	 *
	 * @param productInfoPage
	 * @return
	 */
	@AutoLog(value = "商品信息表-编辑")
	@ApiOperation(value="商品信息表-编辑", notes="商品信息表-编辑")
    @RequiresPermissions("product:tb_product_info:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ProductInfoPage productInfoPage) {
		ProductInfo productInfo = new ProductInfo();
		BeanUtils.copyProperties(productInfoPage, productInfo);
		ProductInfo productInfoEntity = productInfoService.getById(productInfo.getId());
		if(productInfoEntity==null) {
			return Result.error("未找到对应数据");
		}
		productInfoService.updateMain(productInfo, productInfoPage.getProductSpecificationList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "商品信息表-通过id删除")
	@ApiOperation(value="商品信息表-通过id删除", notes="商品信息表-通过id删除")
    @RequiresPermissions("product:tb_product_info:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		productInfoService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "商品信息表-批量删除")
	@ApiOperation(value="商品信息表-批量删除", notes="商品信息表-批量删除")
    @RequiresPermissions("product:tb_product_info:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.productInfoService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品信息表-通过id查询")
	@ApiOperation(value="商品信息表-通过id查询", notes="商品信息表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ProductInfo> queryById(@RequestParam(name="id",required=true) String id) {
		ProductInfo productInfo = productInfoService.getById(id);
		if(productInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(productInfo);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "商品规格表通过主表ID查询")
	@ApiOperation(value="商品规格表主表ID查询", notes="商品规格表-通主表ID查询")
	@GetMapping(value = "/queryProductSpecificationByMainId")
	public Result<?> queryProductSpecificationListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ProductSpecification> productSpecificationList = productSpecificationService.selectByMainId(id);
		Page<ProductSpecification> page = new Page<ProductSpecification>(1, 1);
		//只有分页会翻译字典，这边封装成翻页对象
		page.setRecords(productSpecificationList);
		return Result.OK(page);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param productInfo
    */
    @RequiresPermissions("product:tb_product_info:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ProductInfo productInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ProductInfo> queryWrapper = QueryGenerator.initQueryWrapper(productInfo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<ProductInfo> productInfoList = productInfoService.list(queryWrapper);

      // Step.3 组装pageList
      List<ProductInfoPage> pageList = new ArrayList<ProductInfoPage>();
      for (ProductInfo main : productInfoList) {
          ProductInfoPage vo = new ProductInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<ProductSpecification> productSpecificationList = productSpecificationService.selectByMainId(main.getId());
          vo.setProductSpecificationList(productSpecificationList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "商品信息表列表");
      mv.addObject(NormalExcelConstants.CLASS, ProductInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品信息表数据", "导出人:"+sysUser.getRealname(), "商品信息表"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("product:tb_product_info:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<ProductInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ProductInfoPage.class, params);
              for (ProductInfoPage page : list) {
                  ProductInfo po = new ProductInfo();
                  BeanUtils.copyProperties(page, po);
                  productInfoService.saveMain(po, page.getProductSpecificationList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}

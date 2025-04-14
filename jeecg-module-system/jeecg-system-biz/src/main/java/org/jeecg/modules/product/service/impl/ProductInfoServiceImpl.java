package org.jeecg.modules.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.entity.vo.ProductBatchEditVO;
import org.jeecg.modules.product.mapper.ProductSpecificationMapper;
import org.jeecg.modules.product.mapper.ProductInfoMapper;
import org.jeecg.modules.product.service.IProductInfoService;
import org.jeecg.modules.product.service.IProductSpecificationService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @Description: 商品信息表
 * @Author: jeecg-boot
 * @Date:   2024-08-04
 * @Version: V1.0
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private IProductSpecificationService productSpecificationService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ProductInfo productInfo, List<ProductSpecification> productSpecificationList) {
		productInfoMapper.insert(productInfo);
		if(productSpecificationList!=null && productSpecificationList.size()>0) {
			for(ProductSpecification entity:productSpecificationList) {
				//外键设置
				entity.setProductId(productInfo.getId());
				productSpecificationService.save(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ProductInfo productInfo,List<ProductSpecification> productSpecificationList) {
		productInfoMapper.updateById(productInfo);
		
		//1.先删除子表数据
		productSpecificationService.deleteByMainId(productInfo.getId());
		
		//2.子表数据重新插入
		if(productSpecificationList!=null && productSpecificationList.size()>0) {
			for(ProductSpecification entity:productSpecificationList) {
				//外键设置
				entity.setProductId(productInfo.getId());
				productSpecificationService.save(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		productSpecificationService.deleteByMainId(id);
		productInfoMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			productSpecificationService.deleteByMainId(id.toString());
			productInfoMapper.deleteById(id);
		}
	}

	/**
	 * 批量修改商品规格的仓库
	 * @param productBatchEditVO
	 * @return
	 */
	@Override
	public Result<String> batchEditWaveHouseByProductSpecification(ProductBatchEditVO productBatchEditVO) {
		String productInfoIds = productBatchEditVO.getProductInfoIds();
		String waveHouseId = productBatchEditVO.getWaveHouseId();

		List<ProductInfo> productInfos = this.listByIds(Arrays.asList(productInfoIds.split(",")));
		// 解决非法输入的情况
		List<String> productionIds = productInfos.stream().map(item -> item.getId()).collect(Collectors.toList());
		List<ProductSpecification> productSpecifications = productSpecificationService.list(new LambdaQueryWrapper<ProductSpecification>().in(ProductSpecification::getProductId, productionIds));
		for (ProductSpecification productSpecification : productSpecifications) {
			productSpecification.setWarehouseId(waveHouseId);
		}
		productSpecificationService.updateBatchById(productSpecifications);
		return Result.ok("编辑成功");
	}
	
}

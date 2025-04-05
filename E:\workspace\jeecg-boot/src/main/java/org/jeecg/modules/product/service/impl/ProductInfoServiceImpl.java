package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.mapper.ProductSpecificationMapper;
import org.jeecg.modules.product.mapper.ProductInfoMapper;
import org.jeecg.modules.product.service.IProductInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 商品信息表
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@Service
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfo> implements IProductInfoService {

	@Autowired
	private ProductInfoMapper productInfoMapper;
	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ProductInfo productInfo, List<ProductSpecification> productSpecificationList) {
		productInfoMapper.insert(productInfo);
		if(productSpecificationList!=null && productSpecificationList.size()>0) {
			for(ProductSpecification entity:productSpecificationList) {
				//外键设置
				entity.setProductId(productInfo.getId());
				productSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ProductInfo productInfo,List<ProductSpecification> productSpecificationList) {
		productInfoMapper.updateById(productInfo);
		
		//1.先删除子表数据
		productSpecificationMapper.deleteByMainId(productInfo.getId());
		
		//2.子表数据重新插入
		if(productSpecificationList!=null && productSpecificationList.size()>0) {
			for(ProductSpecification entity:productSpecificationList) {
				//外键设置
				entity.setProductId(productInfo.getId());
				productSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		productSpecificationMapper.deleteByMainId(id);
		productInfoMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			productSpecificationMapper.deleteByMainId(id.toString());
			productInfoMapper.deleteById(id);
		}
	}
	
}

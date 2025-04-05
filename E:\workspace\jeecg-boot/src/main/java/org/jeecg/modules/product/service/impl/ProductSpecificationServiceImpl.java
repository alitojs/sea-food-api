package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.mapper.ProductSpecificationMapper;
import org.jeecg.modules.product.service.IProductSpecificationService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 商品规格表
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
@Service
public class ProductSpecificationServiceImpl extends ServiceImpl<ProductSpecificationMapper, ProductSpecification> implements IProductSpecificationService {
	
	@Autowired
	private ProductSpecificationMapper productSpecificationMapper;
	
	@Override
	public List<ProductSpecification> selectByMainId(String mainId) {
		return productSpecificationMapper.selectByMainId(mainId);
	}
}

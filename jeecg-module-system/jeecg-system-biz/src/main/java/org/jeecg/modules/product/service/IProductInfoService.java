package org.jeecg.modules.product.service;

import org.jeecg.modules.product.entity.ProductSpecification;
import org.jeecg.modules.product.entity.ProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 商品信息表
 * @Author: jeecg-boot
 * @Date:   2024-08-04
 * @Version: V1.0
 */
public interface IProductInfoService extends IService<ProductInfo> {

	/**
	 * 添加一对多
	 *
	 * @param productInfo
	 * @param productSpecificationList
	 */
	public void saveMain(ProductInfo productInfo,List<ProductSpecification> productSpecificationList) ;
	
	/**
	 * 修改一对多
	 *
   * @param productInfo
   * @param productSpecificationList
	 */
	public void updateMain(ProductInfo productInfo,List<ProductSpecification> productSpecificationList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}

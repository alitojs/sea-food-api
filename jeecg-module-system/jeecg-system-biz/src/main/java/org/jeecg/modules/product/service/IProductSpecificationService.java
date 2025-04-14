package org.jeecg.modules.product.service;

import org.jeecg.modules.product.entity.ProductSpecification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品规格表
 * @Author: jeecg-boot
 * @Date:   2024-08-04
 * @Version: V1.0
 */
public interface IProductSpecificationService extends IService<ProductSpecification> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ProductSpecification>
	 */
	public List<ProductSpecification> selectByMainId(String mainId);

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(String mainId);
}

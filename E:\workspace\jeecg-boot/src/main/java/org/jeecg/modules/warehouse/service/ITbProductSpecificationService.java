package org.jeecg.modules.warehouse.service;

import org.jeecg.modules.warehouse.entity.TbProductSpecification;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 商品规格表
 * @Author: jeecg-boot
 * @Date:   2025-04-07
 * @Version: V1.0
 */
public interface ITbProductSpecificationService extends IService<TbProductSpecification> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<TbProductSpecification>
	 */
	public List<TbProductSpecification> selectByMainId(String mainId);
}

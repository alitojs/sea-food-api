package org.jeecg.modules.product.mapper;

import java.util.List;
import org.jeecg.modules.product.entity.TbProductSpecification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商品规格表
 * @Author: jeecg-boot
 * @Date:   2025-04-02
 * @Version: V1.0
 */
public interface TbProductSpecificationMapper extends BaseMapper<TbProductSpecification> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<TbProductSpecification>
   */
	public List<TbProductSpecification> selectByMainId(@Param("mainId") String mainId);
}

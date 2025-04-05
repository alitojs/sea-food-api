package org.jeecg.modules.product.service.impl;

import org.jeecg.modules.product.entity.TbProductSpecification;
import org.jeecg.modules.product.mapper.TbProductSpecificationMapper;
import org.jeecg.modules.product.service.ITbProductSpecificationService;
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
public class TbProductSpecificationServiceImpl extends ServiceImpl<TbProductSpecificationMapper, TbProductSpecification> implements ITbProductSpecificationService {
	
	@Autowired
	private TbProductSpecificationMapper tbProductSpecificationMapper;
	
	@Override
	public List<TbProductSpecification> selectByMainId(String mainId) {
		return tbProductSpecificationMapper.selectByMainId(mainId);
	}
}

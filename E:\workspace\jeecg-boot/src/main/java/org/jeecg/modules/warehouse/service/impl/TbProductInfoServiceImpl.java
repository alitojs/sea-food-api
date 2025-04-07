package org.jeecg.modules.warehouse.service.impl;

import org.jeecg.modules.warehouse.entity.TbProductInfo;
import org.jeecg.modules.warehouse.entity.TbProductSpecification;
import org.jeecg.modules.warehouse.mapper.TbProductSpecificationMapper;
import org.jeecg.modules.warehouse.mapper.TbProductInfoMapper;
import org.jeecg.modules.warehouse.service.ITbProductInfoService;
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
 * @Date:   2025-04-07
 * @Version: V1.0
 */
@Service
public class TbProductInfoServiceImpl extends ServiceImpl<TbProductInfoMapper, TbProductInfo> implements ITbProductInfoService {

	@Autowired
	private TbProductInfoMapper tbProductInfoMapper;
	@Autowired
	private TbProductSpecificationMapper tbProductSpecificationMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(TbProductInfo tbProductInfo, List<TbProductSpecification> tbProductSpecificationList) {
		tbProductInfoMapper.insert(tbProductInfo);
		if(tbProductSpecificationList!=null && tbProductSpecificationList.size()>0) {
			for(TbProductSpecification entity:tbProductSpecificationList) {
				//外键设置
				entity.setProductId(tbProductInfo.getId());
				tbProductSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(TbProductInfo tbProductInfo,List<TbProductSpecification> tbProductSpecificationList) {
		tbProductInfoMapper.updateById(tbProductInfo);
		
		//1.先删除子表数据
		tbProductSpecificationMapper.deleteByMainId(tbProductInfo.getId());
		
		//2.子表数据重新插入
		if(tbProductSpecificationList!=null && tbProductSpecificationList.size()>0) {
			for(TbProductSpecification entity:tbProductSpecificationList) {
				//外键设置
				entity.setProductId(tbProductInfo.getId());
				tbProductSpecificationMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		tbProductSpecificationMapper.deleteByMainId(id);
		tbProductInfoMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			tbProductSpecificationMapper.deleteByMainId(id.toString());
			tbProductInfoMapper.deleteById(id);
		}
	}
	
}

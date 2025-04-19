package org.jeecg.modules.storeProduct.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.product.service.IProductInfoService;
import org.jeecg.modules.storeProduct.entity.StoreProductInfo;
import org.jeecg.modules.storeProduct.mapper.StoreProductInfoMapper;
import org.jeecg.modules.storeProduct.service.IStoreProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 店铺货物关联表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
@Service
public class StoreProductInfoServiceImpl extends ServiceImpl<StoreProductInfoMapper, StoreProductInfo> implements IStoreProductInfoService {

    @Autowired
    private IProductInfoService productInfoService;

    @Override
    public IPage<ProductInfo> queryProductListByStoreId(Page<ProductInfo> page, StoreProductInfo storeProductInfo, HttpServletRequest req) {
        LambdaQueryWrapper<StoreProductInfo> storeProductLambda = QueryGenerator.initQueryWrapper(storeProductInfo, req.getParameterMap()).lambda();
        List<StoreProductInfo> storeProductList = this.list(storeProductLambda);

        List<String> productIds = storeProductList.stream().map(item -> item.getProductId()).collect(Collectors.toList());

        IPage<ProductInfo> pageList = productInfoService.page(page, new LambdaQueryWrapper<ProductInfo>().in(ProductInfo:: getId, productIds).eq(ProductInfo::getIsLimited, 1));
        return pageList;
    }
}

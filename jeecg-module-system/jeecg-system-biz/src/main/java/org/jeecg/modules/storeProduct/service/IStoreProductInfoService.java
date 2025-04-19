package org.jeecg.modules.storeProduct.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.product.entity.ProductInfo;
import org.jeecg.modules.storeProduct.entity.StoreProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 店铺货物关联表
 * @Author: jeecg-boot
 * @Date:   2025-04-19
 * @Version: V1.0
 */
public interface IStoreProductInfoService extends IService<StoreProductInfo> {

    IPage<ProductInfo> queryProductListByStoreId(Page<ProductInfo> page, StoreProductInfo storeProductInfo, HttpServletRequest req);
}

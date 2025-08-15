package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.CommodityInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ICommodityInfoService extends IService<CommodityInfo> {

    /**
     * 分页获取商品信息
     *
     * @param page         分页对象
     * @param commodityInfo 商品信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCommodityPage(Page<CommodityInfo> page, CommodityInfo commodityInfo);

    /**
     * 根据ID获取商品信息
     *
     * @param commodityId 商品信息
     * @return 结果
     */
    LinkedHashMap<String, Object> selectCommodityById(Integer commodityId);
}

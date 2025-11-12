package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.CommodityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface CommodityInfoMapper extends BaseMapper<CommodityInfo> {

    /**
     * 分页获取商品信息
     *
     * @param page         分页对象
     * @param commodityInfo 商品信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectCommodityPage(Page<CommodityInfo> page, @Param("commodityInfo") CommodityInfo commodityInfo);

    /**
     * 根据ID获取商品信息
     *
     * @param commodityId 商品信息
     * @return 结果
     */
    LinkedHashMap<String, Object> selectCommodityById(@Param("commodityId") Integer commodityId);
}

package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.CommodityInfoMapper;
import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class CommodityInfoServiceImpl extends ServiceImpl<CommodityInfoMapper, CommodityInfo> implements ICommodityInfoService {

    /**
     * 分页获取商品信息
     *
     * @param page         分页对象
     * @param commodityInfo 商品信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectCommodityPage(Page<CommodityInfo> page, CommodityInfo commodityInfo) {
        return baseMapper.selectCommodityPage(page, commodityInfo);
    }

    /**
     * 根据ID获取商品信息
     *
     * @param commodityId 商品信息
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> selectCommodityById(Integer commodityId) {
        // 商品信息
        CommodityInfo commodityInfo = this.getById(commodityId);
        commodityInfo.setClickNum(commodityInfo.getClickNum() + 1);
        commodityInfo.setClickTime(DateUtil.formatDateTime(new Date()));
        this.updateById(commodityInfo);
        return baseMapper.selectCommodityById(commodityId);
    }
}

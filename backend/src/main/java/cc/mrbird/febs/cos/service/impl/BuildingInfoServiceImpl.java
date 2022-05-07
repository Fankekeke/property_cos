package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.dao.BuildingInfoMapper;
import cc.mrbird.febs.cos.service.IBuildingInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class BuildingInfoServiceImpl extends ServiceImpl<BuildingInfoMapper, BuildingInfo> implements IBuildingInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> buildingInfoByPage(Page page, BuildingInfo buildingInfo) {
        return baseMapper.buildingInfoByPage(page, buildingInfo);
    }
}

package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.dao.RepairInfoMapper;
import cc.mrbird.febs.cos.service.IRepairInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class RepairInfoServiceImpl extends ServiceImpl<RepairInfoMapper, RepairInfo> implements IRepairInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> repairInfoByPage(Page page, RepairInfo repairInfo) {
        return baseMapper.repairInfoByPage(page, repairInfo);
    }

    @Override
    public LinkedHashMap<String, Object> repairInfoById(Integer repairId) {
        return baseMapper.repairInfoById(repairId);
    }
}

package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.SafetyInspection;
import cc.mrbird.febs.cos.dao.SafetyInspectionMapper;
import cc.mrbird.febs.cos.service.ISafetyInspectionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class SafetyInspectionServiceImpl extends ServiceImpl<SafetyInspectionMapper, SafetyInspection> implements ISafetyInspectionService {

    /**
     * 分页获取安全巡检信息
     *
     * @param page         分页对象
     * @param safetyInspection 安全巡检信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectSafetyInspectionPage(Page<SafetyInspection> page, SafetyInspection safetyInspection) {
        return baseMapper.selectSafetyInspectionPage(page, safetyInspection);
    }
}

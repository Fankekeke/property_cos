package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.DailyManage;
import cc.mrbird.febs.cos.dao.DailyManageMapper;
import cc.mrbird.febs.cos.service.IDailyManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class DailyManageServiceImpl extends ServiceImpl<DailyManageMapper, DailyManage> implements IDailyManageService {

    @Override
    public IPage<LinkedHashMap<String, Object>> dailyInfoByPage(Page page, DailyManage dailyManage) {
        return baseMapper.dailyInfoByPage(page, dailyManage);
    }
}

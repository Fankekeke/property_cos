package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.VisitRecord;
import cc.mrbird.febs.cos.dao.VisitRecordMapper;
import cc.mrbird.febs.cos.service.IVisitRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class VisitRecordServiceImpl extends ServiceImpl<VisitRecordMapper, VisitRecord> implements IVisitRecordService {

    /**
     * 分页获取来访记录信息
     *
     * @param page        分页对象
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryVisitRecordPage(Page<VisitRecord> page, VisitRecord visitRecord) {
        return baseMapper.queryVisitRecordPage(page, visitRecord);
    }
}

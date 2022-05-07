package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.WorkerInfo;
import cc.mrbird.febs.cos.dao.WorkerInfoMapper;
import cc.mrbird.febs.cos.service.IWorkerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class WorkerInfoServiceImpl extends ServiceImpl<WorkerInfoMapper, WorkerInfo> implements IWorkerInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> workerInfoByPage(Page page, WorkerInfo workerInfo) {
        return baseMapper.workerInfoByPage(page, workerInfo);
    }
}

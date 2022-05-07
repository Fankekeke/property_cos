package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.dao.OwnerInfoMapper;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class OwnerInfoServiceImpl extends ServiceImpl<OwnerInfoMapper, OwnerInfo> implements IOwnerInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> ownerInfoByPage(Page page, OwnerInfo ownerInfo) {
        return baseMapper.ownerInfoByPage(page, ownerInfo);
    }
}

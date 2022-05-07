package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.dao.BulletinInfoMapper;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class BulletinInfoServiceImpl extends ServiceImpl<BulletinInfoMapper, BulletinInfo> implements IBulletinInfoService {

    @Override
    public IPage<LinkedHashMap<String, Object>> bulletinInfoByPage(Page page, BulletinInfo bulletinInfo) {
        return baseMapper.bulletinInfoByPage(page, bulletinInfo);
    }
}

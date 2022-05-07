package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.EpidemicRegister;
import cc.mrbird.febs.cos.dao.EpidemicRegisterMapper;
import cc.mrbird.febs.cos.service.IEpidemicRegisterService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class EpidemicRegisterServiceImpl extends ServiceImpl<EpidemicRegisterMapper, EpidemicRegister> implements IEpidemicRegisterService {

    @Override
    public IPage<LinkedHashMap<String, Object>> epidemicByPage(Page page, EpidemicRegister epidemicRegister) {
        return baseMapper.epidemicByPage(page, epidemicRegister);
    }
}

package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ChargeStation;
import cc.mrbird.febs.cos.dao.ChargeStationMapper;
import cc.mrbird.febs.cos.service.IChargeStationService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class ChargeStationServiceImpl extends ServiceImpl<ChargeStationMapper, ChargeStation> implements IChargeStationService {

    /**
     * 分页获取充电桩信息
     *
     * @param page          分页对象
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryChargeStationPage(Page<ChargeStation> page, ChargeStation chargeStation) {
        return baseMapper.queryChargeStationPage(page, chargeStation);
    }
}

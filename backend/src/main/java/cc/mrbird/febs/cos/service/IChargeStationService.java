package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ChargeStation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IChargeStationService extends IService<ChargeStation> {

    /**
     * 分页获取充电桩信息
     *
     * @param page          分页对象
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryChargeStationPage(Page<ChargeStation> page, ChargeStation chargeStation);
}

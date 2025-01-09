package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ChargeStation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ChargeStationMapper extends BaseMapper<ChargeStation> {

    /**
     * 分页获取充电桩信息
     *
     * @param page          分页对象
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryChargeStationPage(Page<ChargeStation> page, @Param("chargeStation") ChargeStation chargeStation);
}

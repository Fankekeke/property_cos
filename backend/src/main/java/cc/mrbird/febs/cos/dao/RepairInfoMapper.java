package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.HousesInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface RepairInfoMapper extends BaseMapper<RepairInfo> {

    // 分页查询维修信息
    IPage<LinkedHashMap<String, Object>> repairInfoByPage(Page page, @Param("repairInfo") RepairInfo repairInfo);

    // 查看维修详情
    LinkedHashMap<String, Object> repairInfoById(@Param("repairId") Integer repairId);

    /**
     * 分页查询房屋维修统计
     *
     * @param page       分页对象
     * @param repairInfo 房屋
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectHouseRepairPage(Page<HousesInfo> page, RepairInfo repairInfo);
}

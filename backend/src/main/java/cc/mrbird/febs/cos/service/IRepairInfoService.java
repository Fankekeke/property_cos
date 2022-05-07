package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RepairInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IRepairInfoService extends IService<RepairInfo> {

    // 分页查询维修信息
    IPage<LinkedHashMap<String, Object>> repairInfoByPage(Page page, RepairInfo repairInfo);

    // 查看维修详情
    LinkedHashMap<String, Object> repairInfoById(Integer repairId);

}

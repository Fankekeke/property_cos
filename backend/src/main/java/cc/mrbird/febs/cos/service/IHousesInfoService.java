package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.HousesInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IHousesInfoService extends IService<HousesInfo> {

    // 分页获取房屋信息
    IPage<LinkedHashMap<String, Object>> housesInfoByPage(Page page, HousesInfo housesInfo);

    // 根据业主ID获取房屋
    List<LinkedHashMap<String, Object>> housesInfoByOwnerId(Integer ownerId);

    // 获取房屋列表及上月水电费
    List<LinkedHashMap<String, Object>> housesConsumption(Integer housesId);

    // 查询房屋本月是否有缴费项
    List<LinkedHashMap<String, Object>> housesConsumptionMonth(Integer housesId);

    // 查询房屋是否有缴费项
    List<LinkedHashMap<String, Object>> housesConsumptionCheck(Integer housesId, String date);

    // 进入主页
    LinkedHashMap<String, Object> home(Integer type);

    // 根据用户获取房屋信息
    List<LinkedHashMap<String, Object>> housesListByOwnerId(Integer userId);
}

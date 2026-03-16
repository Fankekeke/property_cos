package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.HousesInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface HousesInfoMapper extends BaseMapper<HousesInfo> {

    // 分页获取房屋信息
    IPage<LinkedHashMap<String, Object>> housesInfoByPage(Page page, @Param("housesInfo") HousesInfo housesInfo);

    // 根据业主ID获取房屋
    List<LinkedHashMap<String, Object>> housesInfoByOwnerId(@Param("ownerId") Integer ownerId);

    // 获取房屋列表及上月水电费
    List<LinkedHashMap<String, Object>> housesConsumption(@Param("housesId") Integer housesId);

    // 查询房屋本月是否有缴费项
    List<LinkedHashMap<String, Object>> housesConsumptionMonth(@Param("housesId") Integer housesId);

    // 查询房屋是否有缴费项
    List<LinkedHashMap<String, Object>> housesConsumptionCheck(@Param("housesId") Integer housesId, @Param("date") String date);

    // 房屋类型占比
    List<LinkedHashMap<String, Object>> housesTypeRate();

    // 根据用户获取房屋信息
    List<LinkedHashMap<String, Object>> housesListByOwnerId(@Param("userId") Integer userId);
}

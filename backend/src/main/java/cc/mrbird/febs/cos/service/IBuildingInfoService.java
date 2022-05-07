package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.BuildingInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IBuildingInfoService extends IService<BuildingInfo> {

    // 分页获取楼宇信息
    IPage<LinkedHashMap<String, Object>> buildingInfoByPage(Page page, BuildingInfo buildingInfo);
}

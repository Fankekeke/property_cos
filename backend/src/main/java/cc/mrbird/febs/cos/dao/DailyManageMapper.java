package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.DailyManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface DailyManageMapper extends BaseMapper<DailyManage> {

    // 分页获取日常管理信息
    IPage<LinkedHashMap<String, Object>> dailyInfoByPage(Page page, @Param("dailyManage") DailyManage dailyManage);
}

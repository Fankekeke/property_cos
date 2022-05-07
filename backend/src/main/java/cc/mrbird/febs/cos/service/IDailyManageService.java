package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.DailyManage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IDailyManageService extends IService<DailyManage> {

    // 分页获取日常管理信息
    IPage<LinkedHashMap<String, Object>> dailyInfoByPage(Page page, DailyManage dailyManage);

}

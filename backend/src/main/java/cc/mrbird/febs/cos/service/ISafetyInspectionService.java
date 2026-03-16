package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.SafetyInspection;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ISafetyInspectionService extends IService<SafetyInspection> {

    /**
     * 分页获取安全巡检信息
     *
     * @param page         分页对象
     * @param safetyInspection 安全巡检信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectSafetyInspectionPage(Page<SafetyInspection> page, SafetyInspection safetyInspection);
}

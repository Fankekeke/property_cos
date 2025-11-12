package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ConferenceInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 活动记录 mapper层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface ConferenceInfoMapper extends BaseMapper<ConferenceInfo> {

    /**
     * 分页获取活动记录
     *
     * @param page           分页对象
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryConferencePage(Page<ConferenceInfo> page, @Param("conferenceInfo") ConferenceInfo conferenceInfo);
}

package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ConferenceInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * 活动记录 service层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
public interface IConferenceInfoService extends IService<ConferenceInfo> {

    /**
     * 分页获取活动记录
     *
     * @param page           分页对象
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryConferencePage(Page<ConferenceInfo> page, ConferenceInfo conferenceInfo);

    /**
     * 查询活动记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    LinkedHashMap<String, Object> queryDetail(Integer id);
}

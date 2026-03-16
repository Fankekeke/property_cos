package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.ConferenceInfo;
import cc.mrbird.febs.cos.dao.ConferenceInfoMapper;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IConferenceInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 活动记录 实现层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConferenceInfoServiceImpl extends ServiceImpl<ConferenceInfoMapper, ConferenceInfo> implements IConferenceInfoService {

    private final IOwnerInfoService ownerInfoService;

    /**
     * 分页获取活动记录
     *
     * @param page           分页对象
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> queryConferencePage(Page<ConferenceInfo> page, ConferenceInfo conferenceInfo) {
        return baseMapper.queryConferencePage(page,conferenceInfo);
    }

    /**
     * 查询活动记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> queryDetail(Integer id) {
        // 活动信息
        ConferenceInfo conferenceInfo = this.getById(id);
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("conferenceInfo", conferenceInfo);
                put("staffList", Collections.emptyList());
            }
        };
        // 获取参加活动员工
        if (StrUtil.isNotEmpty(conferenceInfo.getStaffIds())) {
            List<String> staffIds = Arrays.asList(StrUtil.split(conferenceInfo.getStaffIds(), ","));
            List<OwnerInfo> staffInfoList = new ArrayList<>(ownerInfoService.listByIds(staffIds));
            result.put("staffList", staffInfoList);
        }
        return result;
    }
}

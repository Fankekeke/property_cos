package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AuditInfo;
import cc.mrbird.febs.cos.entity.ConferenceInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IAuditInfoService;
import cc.mrbird.febs.cos.service.IConferenceInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 活动记录 控制层
 *
 * @author FanK fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/conference-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConferenceInfoController {

    private final IConferenceInfoService conferenceInfoService;

    private final IOwnerInfoService ownerInfoService;

    private final IAuditInfoService auditInfoService;

    /**
     * 分页获取活动记录
     *
     * @param page           分页对象
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ConferenceInfo> page, ConferenceInfo conferenceInfo) {
        return R.ok(conferenceInfoService.queryConferencePage(page, conferenceInfo));
    }

    /**
     * 根据条件查询员工
     *
     * @param conditionId 活动ID
     * @return 结果
     */
    @GetMapping("/queryStaffListByCondition")
    public R queryStaffListByCondition(Integer conditionId) {
        // 获取活动员工列表
        List<AuditInfo> auditInfoList = auditInfoService.list(Wrappers.<AuditInfo>lambdaQuery().eq(AuditInfo::getConferenceId, conditionId).eq(AuditInfo::getStatus, "1"));
        if (CollectionUtil.isNotEmpty(auditInfoList)) {
            List<Integer> ownerIds = auditInfoList.stream().map(AuditInfo::getOwnerId).collect(Collectors.toList());
            List<OwnerInfo> staffInfoList = ownerInfoService.list(Wrappers.<OwnerInfo>lambdaQuery().in(OwnerInfo::getId, ownerIds));
            return R.ok(staffInfoList);
        } else {
            return R.ok(Collections.emptyList());
        }
    }

    /**
     * 查询活动记录详情
     *
     * @param id 主键ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(conferenceInfoService.queryDetail(id));
    }

    /**
     * 查询活动记录列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(conferenceInfoService.list());
    }

    /**
     * 新增活动记录
     *
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    @PostMapping
    public R save(ConferenceInfo conferenceInfo) {
        conferenceInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(conferenceInfoService.save(conferenceInfo));
    }

    /**
     * 修改活动记录
     *
     * @param conferenceInfo 活动记录
     * @return 结果
     */
    @PutMapping
    public R edit(ConferenceInfo conferenceInfo) {
        return R.ok(conferenceInfoService.updateById(conferenceInfo));
    }

    /**
     * 删除活动记录
     *
     * @param ids ids
     * @return 活动记录
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(conferenceInfoService.removeByIds(ids));
    }
}

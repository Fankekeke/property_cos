package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.AuditInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IAuditInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/audit-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditInfoController {

    private final IAuditInfoService auditInfoService;

    private final IOwnerInfoService ownerInfoService;

    /**
     * 分页获取活动审核信息
     *
     * @param page      分页对象
     * @param auditInfo 活动审核信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<AuditInfo> page, AuditInfo auditInfo) {
        return R.ok(auditInfoService.selectAuditInfoPage(page, auditInfo));
    }

    /**
     * 获取活动审核信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(auditInfoService.list());
    }

    /**
     * 获取活动审核详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(auditInfoService.getById(id));
    }

    /**
     * 新增活动审核信息
     *
     * @param auditInfo 活动审核信息
     * @return 结果
     */
    @PostMapping
    public R save(AuditInfo auditInfo) throws FebsException {
        // 获取业主ID
        OwnerInfo ownerInfo = ownerInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, auditInfo.getOwnerId()));
        if (ownerInfo == null) {
            throw new FebsException("该用户不存在");
        }
        // 判断用户是否有在待审核的申请
        if (auditInfoService.count(Wrappers.<AuditInfo>lambdaQuery().eq(AuditInfo::getOwnerId, ownerInfo.getId()).eq(AuditInfo::getStatus, "0")) > 0) {
            throw new FebsException("该用户有未审核的申请，请勿重复申请");
        }
        auditInfo.setOwnerId(ownerInfo.getId());
        auditInfo.setStatus("0");
        auditInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(auditInfoService.save(auditInfo));
    }

    /**
     * 修改活动审核信息
     *
     * @param auditInfo 活动审核信息
     * @return 结果
     */
    @PutMapping
    public R edit(AuditInfo auditInfo) {
        return R.ok(auditInfoService.updateById(auditInfo));
    }

    /**
     * 删除活动审核信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(auditInfoService.removeByIds(ids));
    }
}

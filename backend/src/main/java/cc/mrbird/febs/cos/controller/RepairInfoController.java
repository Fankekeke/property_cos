package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HousesInfo;
import cc.mrbird.febs.cos.entity.RepairInfo;
import cc.mrbird.febs.cos.service.IRepairInfoService;
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
@RequestMapping("/cos/repair-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RepairInfoController {

    private final IRepairInfoService repairInfoService;

    /**
     * 查看维修详情
     *
     * @param repairId
     * @return
     */
    @GetMapping("/repairInfoById")
    public R repairInfoById(Integer repairId) {
        return R.ok(repairInfoService.repairInfoById(repairId));
    }

    /**
     * 分页查询房屋维修统计
     *
     * @param page       分页对象
     * @param repairInfo 房屋
     * @return 结果
     */
    @GetMapping("/repair/page")
    public R selectHouseRepairPage(Page<HousesInfo> page, RepairInfo repairInfo) {
        return R.ok(repairInfoService.selectHouseRepairPage(page, repairInfo));
    }

    /**
     * 修改维修状态
     *
     * @param repairId
     * @return
     */
    @GetMapping("/down")
    public R down(Integer repairId) {
        return R.ok(repairInfoService.update(Wrappers.<RepairInfo>lambdaUpdate().set(RepairInfo::getRepairStatus, 2).eq(RepairInfo::getId, repairId)));
    }

    /**
     * 分页查询维修信息
     *
     * @param page
     * @param repairInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, RepairInfo repairInfo) {
        return R.ok(repairInfoService.repairInfoByPage(page, repairInfo));
    }

    /**
     * 添加维修信息
     *
     * @param repairInfo
     * @return
     */
    @PostMapping
    public R save(RepairInfo repairInfo) {
        repairInfo.setCode("ORD-" + System.currentTimeMillis());
        repairInfo.setRepairStatus(0);
        repairInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(repairInfoService.save(repairInfo));
    }

    /**
     * 修改维修信息
     *
     * @param repairInfo
     * @return
     */
    @PutMapping
    public R edit(RepairInfo repairInfo) {
        repairInfo.setRepairStatus(1);
        // 分析此房屋维修情况
        RepairInfo repair = repairInfoService.getById(repairInfo.getId());
        repairInfoService.repairAnalyze(repair.getHousesId());
        return R.ok(repairInfoService.updateById(repairInfo));
    }

    /**
     * 删除维修信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(repairInfoService.removeByIds(ids));
    }

}

package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.SafetyInspection;
import cc.mrbird.febs.cos.service.ISafetyInspectionService;
import cn.hutool.core.date.DateUtil;
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
@RequestMapping("/cos/safety-inspection")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SafetyInspectionController {

    private final ISafetyInspectionService safetyInspectionService;

    /**
     * 分页获取安全巡检信息
     *
     * @param page         分页对象
     * @param safetyInspection 安全巡检信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<SafetyInspection> page, SafetyInspection safetyInspection) {
        return R.ok(safetyInspectionService.selectSafetyInspectionPage(page, safetyInspection));
    }

    /**
     * 获取安全巡检信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(safetyInspectionService.list());
    }

    /**
     * 获取安全巡检详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(safetyInspectionService.getById(id));
    }

    /**
     * 新增安全巡检信息
     *
     * @param safetyInspection 安全巡检信息
     * @return 结果
     */
    @PostMapping
    public R save(SafetyInspection safetyInspection) {
        safetyInspection.setCheckDate(DateUtil.formatDateTime(new Date()));
        return R.ok(safetyInspectionService.save(safetyInspection));
    }

    /**
     * 修改安全巡检信息
     *
     * @param safetyInspection 安全巡检信息
     * @return 结果
     */
    @PutMapping
    public R edit(SafetyInspection safetyInspection) {
        return R.ok(safetyInspectionService.updateById(safetyInspection));
    }

    /**
     * 删除安全巡检信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(safetyInspectionService.removeByIds(ids));
    }
}

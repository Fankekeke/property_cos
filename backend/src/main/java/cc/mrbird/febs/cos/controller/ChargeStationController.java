package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.ChargeStation;
import cc.mrbird.febs.cos.service.IChargeStationService;
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
@RequestMapping("/cos/charge-station")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChargeStationController {

    private final IChargeStationService chargeStationService;

    /**
     * 分页获取充电桩信息
     *
     * @param page          分页对象
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<ChargeStation> page, ChargeStation chargeStation) {
        return R.ok(chargeStationService.queryChargeStationPage(page, chargeStation));
    }

    /**
     * 获取充电桩信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(chargeStationService.list());
    }

    /**
     * 获取充电桩详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(chargeStationService.getById(id));
    }

    /**
     * 新增充电桩信息
     *
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    @PostMapping
    public R save(ChargeStation chargeStation) {
        chargeStation.setCode("CAG-" + System.currentTimeMillis());
        chargeStation.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(chargeStationService.save(chargeStation));
    }

    /**
     * 修改充电桩信息
     *
     * @param chargeStation 充电桩信息
     * @return 结果
     */
    @PutMapping
    public R edit(ChargeStation chargeStation) {
        return R.ok(chargeStationService.updateById(chargeStation));
    }

    /**
     * 删除充电桩信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(chargeStationService.removeByIds(ids));
    }
}

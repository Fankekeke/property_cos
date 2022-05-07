package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.EpidemicRegister;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IEpidemicRegisterService;
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
@RequestMapping("/cos/epidemic-register")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EpidemicRegisterController {

    private final IEpidemicRegisterService epidemicRegisterService;

    private final IOwnerInfoService ownerInfoService;

    /**
     * 分页获取防疫登记信息
     * @param page
     * @param epidemicRegister
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, EpidemicRegister epidemicRegister) {
        return R.ok(epidemicRegisterService.epidemicByPage(page, epidemicRegister));
    }

    /**
     * 添加防疫登记信息
     * @param epidemicRegister
     * @return
     */
    @PostMapping
    public R save(EpidemicRegister epidemicRegister) {
        epidemicRegister.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(epidemicRegisterService.save(epidemicRegister));
    }

    /**
     * 修改防疫登记信息
     * @param epidemicRegister
     * @return
     */
    @PutMapping
    public R edit(EpidemicRegister epidemicRegister) {
        return R.ok(epidemicRegisterService.updateById(epidemicRegister));
    }

    /**
     * 删除防疫登记信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(epidemicRegisterService.removeByIds(ids));
    }

}

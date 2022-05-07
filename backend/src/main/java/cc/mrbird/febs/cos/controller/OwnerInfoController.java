package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OwnerInfo;
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
@RequestMapping("/cos/owner-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerInfoController {

    private final IOwnerInfoService ownerInfoService;

    /**
     * 根据系统账户ID获取业主信息
     * @param userId
     * @return
     */
    @GetMapping("/OwnerInfoByUserId")
    public R OwnerInfoByUserId(Integer userId) {
        return R.ok(ownerInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, userId)));
    }

    /**
     *
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(ownerInfoService.list());
    }

    /**
     * 分页获取业主信息
     * @param page
     * @param ownerInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, OwnerInfo ownerInfo) {
        return R.ok(ownerInfoService.ownerInfoByPage(page, ownerInfo));
    }

    /**
     * 添加业主信息
     * @param ownerInfo
     * @return
     */
    @PostMapping
    public R save(OwnerInfo ownerInfo) {
        ownerInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(ownerInfoService.save(ownerInfo));
    }

    /**
     * 修改业主信息
     * @param ownerInfo
     * @return
     */
    @PutMapping
    public R edit(OwnerInfo ownerInfo) {
        return R.ok(ownerInfoService.updateById(ownerInfo));
    }

    /**
     * 删除业主信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(ownerInfoService.removeByIds(ids));
    }

}

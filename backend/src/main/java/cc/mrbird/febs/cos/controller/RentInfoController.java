package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.entity.RentInfo;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cc.mrbird.febs.cos.service.IRentInfoService;
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
@RequestMapping("/cos/rent-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentInfoController {

    private final IRentInfoService rentInfoService;

    private final IOwnerInfoService ownerInfoService;

    /**
     * 分页获取出租信息
     *
     * @param page     分页对象
     * @param rentInfo 出租信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<RentInfo> page, RentInfo rentInfo) {
        if (rentInfo.getOwnerId() != null) {
            OwnerInfo ownerInfo = ownerInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, rentInfo.getOwnerId()));
            rentInfo.setOwnerId(ownerInfo.getId());
        }
        return R.ok(rentInfoService.selectRentPage(page, rentInfo));
    }

    /**
     * 获取所有租房信息
     *
     * @return 结果
     */
    @GetMapping("/rent/list")
    public R selectRentList() {
        return R.ok(rentInfoService.selectRentList());
    }

    @GetMapping("/setStatus")
    public R setStatus(@RequestParam("rentId") Integer rentId, @RequestParam("status") Integer status) {
        return R.ok(rentInfoService.update(Wrappers.<RentInfo>lambdaUpdate().set(RentInfo::getDelFlag, status).eq(RentInfo::getId, rentId)));
    }

    /**
     * 租房信息上下架
     *
     * @param rentId 租房ID
     * @param status 状态
     * @return 结果
     */
    @GetMapping("/audit")
    public R audit(@RequestParam("rentId") Integer rentId, @RequestParam("status") Integer status) {
        return R.ok(rentInfoService.update(Wrappers.<RentInfo>lambdaUpdate().set(RentInfo::getDelFlag, status).eq(RentInfo::getId, rentId)));
    }

    /**
     * 添加出租信息
     *
     * @param rentInfo 出租信息
     * @return 结果
     */
    @PostMapping
    public R save(RentInfo rentInfo) {
        OwnerInfo ownerInfo = ownerInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, rentInfo.getOwnerId()));
        rentInfo.setOwnerId(ownerInfo.getId());
        rentInfo.setDelFlag(1);
        rentInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(rentInfoService.save(rentInfo));
    }

    /**
     * 修改出租信息
     *
     * @param rentInfo 出租信息
     * @return 结果
     */
    @PutMapping
    public R edit(RentInfo rentInfo) {
        return R.ok(rentInfoService.updateById(rentInfo));
    }

    /**
     * 删除出租信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(rentInfoService.removeByIds(ids));
    }
}

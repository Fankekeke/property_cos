package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.HousesInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IHousesInfoService;
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
@RequestMapping("/cos/houses-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HousesInfoController {

    private final IHousesInfoService housesInfoService;

    /**
     * 根据用户获取房屋信息
     * @param userId
     * @return
     */
    @GetMapping("/housesListByOwnerId")
    public R housesListByOwnerId(Integer userId) {
        return R.ok(housesInfoService.housesListByOwnerId(userId));
    }

    /**
     * 进入主页
     * @param type
     * @return
     */
    @GetMapping("/home")
    public R home(@RequestParam(value = "type", required = false) Integer type) {
        return R.ok(housesInfoService.home(type));
    }

    /**
     * 获取房屋列表
     * @return
     */
    @GetMapping("/list")
    public R housesInfoByList() {
        return R.ok(housesInfoService.list());
    }

    /**
     * 根据房屋ID获取上月水电费
     * @return
     */
    @GetMapping("/housesConsumption")
    public R housesConsumption(@RequestParam Integer housesId) {
        return R.ok(housesInfoService.housesConsumption(housesId));
    }

    /**
     * 查询房屋本月是否有缴费项
     * @param housesId
     * @return
     */
    @GetMapping("/housesConsumptionMonth")
    public R housesConsumptionMonth(@RequestParam Integer housesId) {
        return R.ok(housesInfoService.housesConsumptionMonth(housesId));
    }

    /**
     * 查询房屋是否有缴费项
     * @param housesId
     * @param date
     * @return
     */
    @GetMapping("/housesConsumptionCheck")
    public R housesConsumptionCheck(@RequestParam Integer housesId, @RequestParam String date) {
        return R.ok(housesInfoService.housesConsumptionCheck(housesId, date));
    }

    /**
     * 根据业主ID获取房屋
     * @param ownerId
     * @return
     */
    @GetMapping("/housesInfoByOwnerId")
    public R housesInfoByOwnerId(@RequestParam Integer ownerId) {
        return R.ok(housesInfoService.housesInfoByOwnerId(ownerId));
    }

    /**
     * 分页获取房屋信息
     * @param page
     * @param housesInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, HousesInfo housesInfo) {
        return R.ok(housesInfoService.housesInfoByPage(page, housesInfo));
    }

    /**
     * 添加房屋信息
     * @param housesInfo
     * @return
     */
    @PostMapping
    public R save(HousesInfo housesInfo) {
        housesInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(housesInfoService.save(housesInfo));
    }

    /**
     * 修改房屋信息
     * @param housesInfo
     * @return
     */
    @PutMapping
    public R edit(HousesInfo housesInfo) {
        return R.ok(housesInfoService.updateById(housesInfo));
    }

    /**
     * 删除房屋信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(housesInfoService.removeByIds(ids));
    }

}

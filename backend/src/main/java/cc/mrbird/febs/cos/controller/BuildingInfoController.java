package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.BuildingInfo;
import cc.mrbird.febs.cos.service.IBuildingInfoService;
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
@RequestMapping("/cos/building-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildingInfoController {

    private final IBuildingInfoService buildingInfoService;

    /**
     * 查询所有楼宇
     * @return
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(buildingInfoService.list());
    }

    /**
     * 分页获取楼宇信息
     * @param page
     * @param buildingInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, BuildingInfo buildingInfo) {
        return R.ok(buildingInfoService.buildingInfoByPage(page, buildingInfo));
    }

    /**
     * 添加楼宇信息
     * @param buildingInfo
     * @return
     */
    @PostMapping
    public R save(BuildingInfo buildingInfo) {
        buildingInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(buildingInfoService.save(buildingInfo));
    }

    /**
     * 修改楼宇信息
     * @param buildingInfo
     * @return
     */
    @PutMapping
    public R edit(BuildingInfo buildingInfo) {
        return R.ok(buildingInfoService.updateById(buildingInfo));
    }

    /**
     * 删除楼宇信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(buildingInfoService.removeByIds(ids));
    }

}

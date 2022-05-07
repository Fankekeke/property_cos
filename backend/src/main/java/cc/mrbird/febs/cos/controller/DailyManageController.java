package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.DailyManage;
import cc.mrbird.febs.cos.service.IDailyManageService;
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
@RequestMapping("/cos/daily-manage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DailyManageController {

    private final IDailyManageService dailyManageService;

    /**
     * 分页获取日常管理信息
     * @param page
     * @param dailyManage
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, DailyManage dailyManage) {
        return R.ok(dailyManageService.dailyInfoByPage(page, dailyManage));
    }

    /**
     * 添加日常管理信息
     * @param dailyManage
     * @return
     */
    @PostMapping
    public R save(DailyManage dailyManage) {
        dailyManage.setCreateDate(DateUtil.formatDateTime(new Date()));
        dailyManage.setReadStatus(0);
        return R.ok(dailyManageService.save(dailyManage));
    }

    /**
     * 修改日常管理信息
     * @param dailyManage
     * @return
     */
    @PutMapping
    public R edit(DailyManage dailyManage) {
        return R.ok(dailyManageService.updateById(dailyManage));
    }

    /**
     * 删除日常管理信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(dailyManageService.removeByIds(ids));
    }

}

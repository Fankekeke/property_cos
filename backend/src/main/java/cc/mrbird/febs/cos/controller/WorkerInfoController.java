package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.WorkerInfo;
import cc.mrbird.febs.cos.service.IWorkerInfoService;
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
@RequestMapping("/cos/worker-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WorkerInfoController {

    private final IWorkerInfoService workerInfoService;

    /**
     * 根据类型获取工作人员
     * @param type
     * @return
     */
    @GetMapping("/list")
    public R listByType(@RequestParam Integer type) {
        return R.ok(workerInfoService.list(Wrappers.<WorkerInfo>lambdaQuery().eq(WorkerInfo::getType, type)));
    }

    /**
     * 分页查询工作人员信息
     * @param page
     * @param workerInfo
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, WorkerInfo workerInfo) {
        return R.ok(workerInfoService.workerInfoByPage(page, workerInfo));
    }

    /**
     * 添加工作人员信息
     * @param workerInfo
     * @return
     */
    @PostMapping
    public R save(WorkerInfo workerInfo) {
        workerInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(workerInfoService.save(workerInfo));
    }

    /**
     * 修改工作人员信息
     * @param workerInfo
     * @return
     */
    @PutMapping
    public R edit(WorkerInfo workerInfo) {
        return R.ok(workerInfoService.updateById(workerInfo));
    }

    /**
     * 删除工作人员信息
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(workerInfoService.removeByIds(ids));
    }

}

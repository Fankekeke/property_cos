package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.VisitRecord;
import cc.mrbird.febs.cos.service.IVisitRecordService;
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
@RequestMapping("/cos/visit-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VisitRecordController {

    private final IVisitRecordService visitRecordService;

    /**
     * 分页获取来访记录信息
     *
     * @param page        分页对象
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<VisitRecord> page, VisitRecord visitRecord) {
        return R.ok(visitRecordService.queryVisitRecordPage(page, visitRecord));
    }

    /**
     * 获取来访记录信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(visitRecordService.list());
    }

    /**
     * 获取来访记录详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(visitRecordService.getById(id));
    }

    /**
     * 新增来访记录信息
     *
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    @PostMapping
    public R save(VisitRecord visitRecord) {
        visitRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(visitRecordService.save(visitRecord));
    }

    /**
     * 修改来访记录信息
     *
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    @PutMapping
    public R edit(VisitRecord visitRecord) {
        return R.ok(visitRecordService.updateById(visitRecord));
    }

    /**
     * 删除来访记录信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(visitRecordService.removeByIds(ids));
    }
}

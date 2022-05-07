package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PropertyItem;
import cc.mrbird.febs.cos.service.IPropertyItemService;
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
@RequestMapping("/cos/property-item")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PropertyItemController {

    private final IPropertyItemService propertyItemService;

    /**
     * 获取所有物业项
     * @return
     */
    @GetMapping("/list")
    private R list() {
        return R.ok(propertyItemService.list());
    }

    /**
     * 分页查询物业管理项
     * @param page
     * @param propertyItem
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, PropertyItem propertyItem) {
        return R.ok(propertyItemService.propertyItemByPage(page, propertyItem));
    }

    /**
     * 添加物业管理项
     * @param propertyItem
     * @return
     */
    @PostMapping
    public R save(PropertyItem propertyItem) {
        propertyItem.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(propertyItemService.save(propertyItem));
    }

    /**
     * 修改物业管理项
     * @param propertyItem
     * @return
     */
    @PutMapping
    public R edit(PropertyItem propertyItem) {
        return R.ok(propertyItemService.updateById(propertyItem));
    }

    /**
     * 删除物业管理项
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(propertyItemService.removeByIds(ids));
    }

}

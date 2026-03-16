package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CommodityType;
import cc.mrbird.febs.cos.service.ICommodityTypeService;
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
@RequestMapping("/cos/commodity-type")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommodityTypeController {

    private final ICommodityTypeService commodityTypeService;

    /**
     * 分页获取商品类型信息
     *
     * @param page         分页对象
     * @param commodityType 商品类型信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<CommodityType> page, CommodityType commodityType) {
        return R.ok(commodityTypeService.selectCommodityPage(page, commodityType));
    }

    /**
     * 获取商品类型信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(commodityTypeService.list());
    }

    /**
     * 获取商品类型详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(commodityTypeService.getById(id));
    }

    /**
     * 新增商品类型信息
     *
     * @param commodityType 商品类型信息
     * @return 结果
     */
    @PostMapping
    public R save(CommodityType commodityType) {
        commodityType.setCode("COT-" +System.currentTimeMillis());
        commodityType.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(commodityTypeService.save(commodityType));
    }

    /**
     * 修改商品类型信息
     *
     * @param commodityType 商品类型信息
     * @return 结果
     */
    @PutMapping
    public R edit(CommodityType commodityType) {
        return R.ok(commodityTypeService.updateById(commodityType));
    }

    /**
     * 删除商品类型信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(commodityTypeService.removeByIds(ids));
    }

}

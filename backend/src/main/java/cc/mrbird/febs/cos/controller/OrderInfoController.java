package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.entity.LogisticsInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/order-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    private final IOwnerInfoService userInfoService;

    private final ICommodityInfoService commodityInfoService;

    /**
     * 分页获取订单订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<OrderInfo> page, OrderInfo orderInfo) {
        return R.ok(orderInfoService.selectOrderPage(page, orderInfo));
    }

    /**
     * 获取订单订单信息
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(orderInfoService.list());
    }

    /**
     * 订单支付后回调-更新订单状态
     *
     * @param orderCode 订单编号
     * @return 结果
     */
    @GetMapping("/payment")
    public R payment(@RequestParam("orderCode") String orderCode) {
        return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getPayStatus, 1).eq(OrderInfo::getCode, orderCode)));
    }

    /**
     * 修改订单状态
     *
     * @param orderId 订单ID
     * @param status  状态
     * @return 结果
     */
    @GetMapping("/edit/status")
    public R orderReceive(@RequestParam("orderId") Integer orderId, @RequestParam("status") Integer status) {
        return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getPayStatus, status).eq(OrderInfo::getId, orderId)));
    }

    /**
     * 获取订单订单详细信息
     *
     * @param id ID
     * @return 结果
     */
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(orderInfoService.detail(id));
    }

    /**
     * 新增订单订单信息
     *
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    @PostMapping
    public R save(OrderInfo orderInfo) {
        // 获取用户信息
        OwnerInfo user = userInfoService.getOne(Wrappers.<OwnerInfo>lambdaQuery().eq(OwnerInfo::getUserId, orderInfo.getBuyUserId()));
        if (user != null) {
            orderInfo.setBuyUserId(user.getId());
        }
        // 更新商品状态
        CommodityInfo commodity = commodityInfoService.getById(orderInfo.getCommodityId());
        commodity.setStatus("2");
        orderInfo.setSellUserId(commodity.getUserId());
        orderInfo.setTotalPrice(commodity.getPrice());
        orderInfo.setTotalAfterPrice(commodity.getPrice());
        commodityInfoService.updateById(commodity);

        orderInfo.setCode("OR-" + System.currentTimeMillis());
        orderInfo.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(orderInfoService.save(orderInfo));
    }

    /**
     * 根据订单查询物流
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @GetMapping("/logistics/{orderId}")
    public R selectLogistics(@PathVariable("orderId") Integer orderId) {
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if (StrUtil.isNotEmpty(orderInfo.getLogistics())) {
            List<LogisticsInfo> logisticsList = JSONUtil.toList(orderInfo.getLogistics(), LogisticsInfo.class);
            return R.ok(logisticsList);
        } else {
            return R.ok(Collections.emptyList());
        }
    }

    /**
     * 获取主页数据
     *
     * @return 结果
     */
    @GetMapping("/homeData")
    public R homeData() {
        return R.ok(orderInfoService.homeData());
    }


    /**
     * 修改订单订单信息
     *
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    @PutMapping
    public R edit(OrderInfo orderInfo) {
        return R.ok(orderInfoService.updateById(orderInfo));
    }

    /**
     * 删除订单订单信息
     *
     * @param ids 主键IDS
     * @return 结果
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(orderInfoService.removeByIds(ids));
    }
}

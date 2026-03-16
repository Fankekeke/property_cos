package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.dao.OrderInfoMapper;
import cc.mrbird.febs.cos.entity.BulletinInfo;
import cc.mrbird.febs.cos.entity.CommodityInfo;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.OwnerInfo;
import cc.mrbird.febs.cos.service.IBulletinInfoService;
import cc.mrbird.febs.cos.service.ICommodityInfoService;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IOwnerInfoService;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private final IOwnerInfoService userService;

    private final ICommodityInfoService commodityInfoService;

    private final IBulletinInfoService bulletinInfoService;


    /**
     * 分页获取订单订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    @Override
    public IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo) {
        return baseMapper.selectOrderPage(page, orderInfo);
    }

    /**
     * 获取订单订单详细信息
     *
     * @param orderId ID
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> detail(Integer orderId) {
        // 订单信息
        OrderInfo order = this.getById(orderId);
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>() {
            {
                put("buyer", null);
                put("sale", null);
                put("goods", null);
                put("evaluate", null);
            }
        };

        OwnerInfo buyer = userService.getById(order.getBuyUserId());
        OwnerInfo sale = userService.getById(order.getSellUserId());

        // 商品信息
        CommodityInfo commodity = commodityInfoService.getById(order.getCommodityId());

        result.put("buyer", buyer);
        result.put("sale", sale);
        result.put("goods", commodity);
        return result;
    }

    /**
     * 获取主页数据
     *
     * @return 结果
     */
    @Override
    public LinkedHashMap<String, Object> homeData() {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();

        List<OrderInfo> orderList = this.list(Wrappers.<OrderInfo>lambdaQuery().gt(OrderInfo::getPayStatus, 0));
        // 总订单数量
        result.put("orderCode", orderList.size());
        // 总销售额
        result.put("orderPrice", orderList.stream().map(OrderInfo::getTotalAfterPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        // 商品数量
        result.put("pharmacyNum", commodityInfoService.count());
        // 用户数量
        result.put("staffNum", userService.count());

        // 本月订单数量
        List<OrderInfo> orderMonthList = baseMapper.selectOrderByMonth();
        result.put("monthOrderNum", CollectionUtil.isEmpty(orderMonthList) ? 0 : orderMonthList.size());
        BigDecimal orderPrice = orderMonthList.stream().map(OrderInfo::getTotalAfterPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        // 获取本月收益
        result.put("monthOrderPrice", orderPrice);

        // 本年订单数量
        List<OrderInfo> orderYearList = baseMapper.selectOrderByYear();
        result.put("yearOrderNum", CollectionUtil.isEmpty(orderYearList) ? 0 : orderYearList.size());
        // 本年总收益
        BigDecimal orderYearPrice = orderYearList.stream().map(OrderInfo::getTotalAfterPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("yearOrderPrice", orderYearPrice);

        // 公告信息
        result.put("bulletin", bulletinInfoService.list(Wrappers.<BulletinInfo>lambdaQuery()));

        // 近十天内订单统计
        result.put("orderNumWithinDays", baseMapper.selectOrderNumWithinDays());
        // 近十天内收益统计
        result.put("orderPriceWithinDays", baseMapper.selectOrderPriceWithinDays());
        // 订单销售类别统计
        result.put("orderDrugType", baseMapper.selectOrderType());
        return result;
    }
}

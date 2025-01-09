package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.OrderInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 分页获取订单订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, OrderInfo orderInfo);

    /**
     * 获取订单订单详细信息
     *
     * @param orderId ID
     * @return 结果
     */
    LinkedHashMap<String, Object> detail(Integer orderId);

    /**
     * 获取主页数据
     *
     * @return 结果
     */
    LinkedHashMap<String, Object> homeData();
}

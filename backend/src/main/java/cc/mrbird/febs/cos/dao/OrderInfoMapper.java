package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 分页获取订单订单信息
     *
     * @param page      分页对象
     * @param orderInfo 订单订单信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectOrderPage(Page<OrderInfo> page, @Param("orderInfo") OrderInfo orderInfo);

    /**
     * 近十天内订单统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumWithinDays();

    /**
     * 近十天内收益统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderPriceWithinDays();

    /**
     * 查询本月订单
     *
     * @return 结果
     */
    List<OrderInfo> selectOrderByMonth();

    /**
     * 查询本年订单
     *
     * @return 结果
     */
    List<OrderInfo> selectOrderByYear();

    /**
     * 订单销售类别统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderType();
}

package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.PaymentManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface PaymentManageMapper extends BaseMapper<PaymentManage> {

    // 分页查询缴纳费用管理
    IPage<LinkedHashMap<String, Object>> paymentManageByPage(Page page, @Param("paymentManage") PaymentManage paymentManage);

    // 预收金额及已收金额
    LinkedHashMap<String, Object> collectionInfo();

    // 本年预收情况统计
    List<LinkedHashMap<String, Object>> propertyItemYear();

    // 需缴费及总水电消耗
    LinkedHashMap<String, Object> payableRate(@Param("userId") Integer userId);

    List<LinkedHashMap<String, Object>> epidemicRate();

    /**
     * 近十天缴费统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderNumWithinDays();

    /**
     * 近十天报修统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderPriceWithinDays();

    /**
     * 订单销售商品类别统计
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectOrderDishesType(@Param("year") Integer year, @Param("month") Integer month);
}

package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.PaymentManage;
import cc.mrbird.febs.cos.dao.PaymentManageMapper;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.service.IPaymentManageService;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FanK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentManageServiceImpl extends ServiceImpl<PaymentManageMapper, PaymentManage> implements IPaymentManageService {

    private final IPaymentRecordService paymentRecordService;

    @Override
    public IPage<LinkedHashMap<String, Object>> paymentManageByPage(Page page, PaymentManage paymentManage) {
        return baseMapper.paymentManageByPage(page, paymentManage);
    }

    @Override
    public LinkedHashMap<String, Object> selectHomeDataByAdmin() {
        // 返回数据
        LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();

        // 本月缴费数量
        Integer year = DateUtil.year(new Date());
        Integer month = DateUtil.month(new Date()) + 1;

        List<PaymentManage> paymentManageMonth = this.list(Wrappers.<PaymentManage>lambdaQuery().eq(PaymentManage::getYear, year).eq(PaymentManage::getMonth, month));
        if (CollectionUtil.isEmpty(paymentManageMonth)) {
            result.put("monthPaymentNum", 0);
            result.put("monthPayable", 0);
            result.put("monthPaid", 0);
            result.put("monthUnpaid", 0);
        } else {
            Map<Integer, BigDecimal> map = paymentManageMonth.stream().collect(Collectors.toMap(PaymentManage::getId, PaymentManage::getPrice));
            result.put("monthPaymentNum", paymentManageMonth.size());
            // 本月应缴费用
            BigDecimal monthPayable = paymentManageMonth.stream().map(PaymentManage::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            result.put("monthPayable", monthPayable);
            // 获取已交费用
            List<Integer> paymentIds = paymentManageMonth.stream().map(PaymentManage::getId).collect(Collectors.toList());
            List<PaymentRecord> paymentRecordList = paymentRecordService.list(Wrappers.<PaymentRecord>lambdaQuery().in(PaymentRecord::getPaymentId, paymentIds));
            // 本月已缴费用
            if (CollectionUtil.isEmpty(paymentRecordList)) {
                result.put("monthPaid", 0);
                result.put("monthUnpaid", monthPayable);
            } else {
                BigDecimal paid = BigDecimal.ZERO;
                for (PaymentRecord paymentRecord : paymentRecordList) {
                    paid = paid.add(map.get(paymentRecord.getPaymentId()));
                }
                result.put("monthPaid", paid);
                result.put("monthUnpaid", monthPayable.subtract(paid));
            }
        }

        // 近十天缴费统计
        result.put("orderNumDayList", baseMapper.selectOrderNumWithinDays());
        // 近十天报修统计
        result.put("priceDayList", baseMapper.selectOrderPriceWithinDays());
        // 缴费类型统计
        result.put("orderType", baseMapper.selectOrderDishesType(year, month));
        return result;
    }
}

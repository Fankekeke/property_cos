package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.OrderInfo;
import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.service.IOrderInfoService;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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
@RequestMapping("/cos/payment-record")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentRecordController {

    private final IPaymentRecordService paymentRecordService;
    private final IOrderInfoService orderInfoService;

    /**
     * 分页查询缴纳记录
     * @param page
     * @param paymentRecord
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.paymentRecordByPage(page, paymentRecord));
    }

    /**
     * 添加缴纳记录
     * @param paymentRecord
     * @return
     */
    @PostMapping
    public R save(PaymentRecord paymentRecord) {
        if (StrUtil.isNotEmpty(paymentRecord.getOrderCode())) {
            return R.ok(orderInfoService.update(Wrappers.<OrderInfo>lambdaUpdate().set(OrderInfo::getPayStatus, 1).eq(OrderInfo::getCode, paymentRecord.getOrderCode())));
        }
        paymentRecord.setCreateDate(DateUtil.formatDateTime(new Date()));
        return R.ok(paymentRecordService.save(paymentRecord));
    }

    /**
     * 修改缴纳记录
     * @param paymentRecord
     * @return
     */
    @PutMapping
    public R edit(PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.updateById(paymentRecord));
    }

    /**
     * 删除缴纳记录
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(paymentRecordService.removeByIds(ids));
    }

}

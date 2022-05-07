package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PaymentManage;
import cc.mrbird.febs.cos.entity.dto.PaymentManageList;
import cc.mrbird.febs.cos.service.IPaymentManageService;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author FanK
 */
@RestController
@RequestMapping("/cos/payment-manage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentManageController {

    private final IPaymentManageService paymentManageService;

    @PostMapping("/ali/payOverBack")
    public void payOverBack(String message) {
        System.out.println(message);
    }

    /**
     * 添加房屋缴费管理
     * @param paymentManageStr
     * @return
     */
    @PostMapping("/addPaymentManage")
    public R addPaymentManage(String paymentManageStr) {
        JSONArray array = JSONUtil.parseArray(paymentManageStr);
        List<PaymentManage> paymentManageList = JSONUtil.toList(array, PaymentManage.class);
        return R.ok(paymentManageService.saveBatch(paymentManageList));
    }

    /**
     * 分页查询缴纳费用管理
     * @param page
     * @param paymentManage
     * @return
     */
    @GetMapping("/page")
    public R page(Page page, PaymentManage paymentManage) {
        return R.ok(paymentManageService.paymentManageByPage(page, paymentManage));
    }

    /**
     * 添加缴纳费用管理
     * @param paymentManage
     * @return
     */
    @PostMapping
    public R save(PaymentManage paymentManage) {
        return R.ok(paymentManageService.save(paymentManage));
    }

    /**
     * 修改缴纳费用管理
     * @param paymentManage
     * @return
     */
    @PutMapping
    public R edit(PaymentManage paymentManage) {
        return R.ok(paymentManageService.updateById(paymentManage));
    }

    /**
     * 删除缴纳费用管理
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(paymentManageService.removeByIds(ids));
    }

}

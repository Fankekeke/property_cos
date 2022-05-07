package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PaymentManage;
import cc.mrbird.febs.cos.dao.PaymentManageMapper;
import cc.mrbird.febs.cos.service.IPaymentManageService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PaymentManageServiceImpl extends ServiceImpl<PaymentManageMapper, PaymentManage> implements IPaymentManageService {

    @Override
    public IPage<LinkedHashMap<String, Object>> paymentManageByPage(Page page, PaymentManage paymentManage) {
        return baseMapper.paymentManageByPage(page, paymentManage);
    }
}

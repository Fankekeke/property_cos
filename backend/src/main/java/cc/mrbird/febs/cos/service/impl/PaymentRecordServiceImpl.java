package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import cc.mrbird.febs.cos.dao.PaymentRecordMapper;
import cc.mrbird.febs.cos.service.IPaymentRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements IPaymentRecordService {

    @Override
    public IPage<LinkedHashMap<String, Object>> paymentRecordByPage(Page page, PaymentRecord paymentRecord) {
        return baseMapper.paymentRecordByPage(page, paymentRecord);
    }
}

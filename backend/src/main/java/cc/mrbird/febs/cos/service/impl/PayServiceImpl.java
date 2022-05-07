package cc.mrbird.febs.cos.service.impl;

import cc.mrbird.febs.common.utils.Alipay;
import cc.mrbird.febs.cos.entity.AlipayBean;
import cc.mrbird.febs.cos.service.PayService;
import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private Alipay alipay;

    @Override
    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}
package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.AlipayBean;
import com.alipay.api.AlipayApiException;

public interface PayService {

    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
    String aliPay(AlipayBean alipayBean) throws AlipayApiException;
}

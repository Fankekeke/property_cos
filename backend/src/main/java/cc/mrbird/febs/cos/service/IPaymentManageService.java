package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PaymentManage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IPaymentManageService extends IService<PaymentManage> {

    // 分页查询缴纳费用管理
    IPage<LinkedHashMap<String, Object>> paymentManageByPage(Page page, PaymentManage paymentManage);

}

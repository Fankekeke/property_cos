package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IPaymentRecordService extends IService<PaymentRecord> {

    // 分页查询缴纳记录
    IPage<LinkedHashMap<String, Object>> paymentRecordByPage(Page page, PaymentRecord paymentRecord);

}

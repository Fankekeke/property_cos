package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.PaymentRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

    // 分页查询缴纳记录
    IPage<LinkedHashMap<String, Object>> paymentRecordByPage(Page page, @Param("paymentRecord") PaymentRecord paymentRecord);
}

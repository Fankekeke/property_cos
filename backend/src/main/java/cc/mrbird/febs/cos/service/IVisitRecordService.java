package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.VisitRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IVisitRecordService extends IService<VisitRecord> {

    /**
     * 分页获取来访记录信息
     *
     * @param page        分页对象
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryVisitRecordPage(Page<VisitRecord> page, VisitRecord visitRecord);
}

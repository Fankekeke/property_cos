package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.VisitRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface VisitRecordMapper extends BaseMapper<VisitRecord> {

    /**
     * 分页获取来访记录信息
     *
     * @param page        分页对象
     * @param visitRecord 来访记录信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> queryVisitRecordPage(Page<VisitRecord> page, @Param("visitRecord") VisitRecord visitRecord);
}

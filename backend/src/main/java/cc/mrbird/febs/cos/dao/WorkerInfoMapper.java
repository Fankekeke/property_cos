package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.WorkerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface WorkerInfoMapper extends BaseMapper<WorkerInfo> {

    // 分页查询工作人员信息
    IPage<LinkedHashMap<String, Object>> workerInfoByPage(Page page, @Param("workerInfo") WorkerInfo workerInfo);
}

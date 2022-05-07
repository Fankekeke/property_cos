package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.WorkerInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IWorkerInfoService extends IService<WorkerInfo> {

    // 分页查询工作人员信息
    IPage<LinkedHashMap<String, Object>> workerInfoByPage(Page page, WorkerInfo workerInfo);

}

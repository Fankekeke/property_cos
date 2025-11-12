package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.ParkInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IParkInfoService extends IService<ParkInfo> {

    /**
     * 分页获取停车位信息
     * @param page
     * @param parkInfo
     * @return
     */
    IPage<LinkedHashMap<String, Object>> selectParkInfoPage(Page page, ParkInfo parkInfo);
}

package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.ParkInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface ParkInfoMapper extends BaseMapper<ParkInfo> {

    /**
     * 分页获取停车位信息
     * @param page
     * @param parkInfo
     * @return
     */
    IPage<LinkedHashMap<String, Object>> selectParkInfoPage(Page page, @Param("parkInfo") ParkInfo parkInfo);
}

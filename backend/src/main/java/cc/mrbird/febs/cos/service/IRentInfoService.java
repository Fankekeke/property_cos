package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.RentInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author FanK
 */
public interface IRentInfoService extends IService<RentInfo> {

    /**
     * 分页获取出租信息
     *
     * @param page     分页对象
     * @param rentInfo 出租信息
     * @return 结果
     */
    IPage<LinkedHashMap<String, Object>> selectRentPage(Page<RentInfo> page, RentInfo rentInfo);

    /**
     * 获取所有租房信息
     *
     * @return 结果
     */
    List<LinkedHashMap<String, Object>> selectRentList();
}

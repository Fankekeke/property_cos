package cc.mrbird.febs.cos.service;

import cc.mrbird.febs.cos.entity.OwnerInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface IOwnerInfoService extends IService<OwnerInfo> {

    // 分页获取业主信息
    IPage<LinkedHashMap<String, Object>> ownerInfoByPage(Page page,OwnerInfo ownerInfo);

}

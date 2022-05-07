package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.OwnerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface OwnerInfoMapper extends BaseMapper<OwnerInfo> {

    // 分页获取业主信息
    IPage<LinkedHashMap<String, Object>> ownerInfoByPage(Page page, @Param("ownerInfo") OwnerInfo ownerInfo);

}

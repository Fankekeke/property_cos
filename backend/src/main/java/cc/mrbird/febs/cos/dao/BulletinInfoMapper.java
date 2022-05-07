package cc.mrbird.febs.cos.dao;

import cc.mrbird.febs.cos.entity.BulletinInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;

/**
 * @author FanK
 */
public interface BulletinInfoMapper extends BaseMapper<BulletinInfo> {

    // 分页获取公告信息
    IPage<LinkedHashMap<String, Object>> bulletinInfoByPage(Page page, @Param("bulletinInfo") BulletinInfo bulletinInfo);
}
